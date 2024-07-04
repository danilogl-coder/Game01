package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.dreamwork.game.Game;
import com.dreamwork.graphic.Spritesheet;
import com.dreamwork.world.Camera;
import com.dreamwork.world.World;



public class Player extends Entity
{
	public boolean right,left,up,down;
	public double speed  = 1.0;
	public int curAnimation = 0, curAnimationHurt = 0;;
	public int curFrames = 0, curFramesHurt =0, targetFrames = 10, targetFramesHurt = 10;
	private boolean moves;
	public int ammo = 0;
	public boolean hurt;
	private boolean hasGun = false;
	private int wait_gun;
	private BufferedImage[] right_player;
	private BufferedImage[] left_player;
	private BufferedImage[] up_player;
	private BufferedImage[] down_player;
	private BufferedImage[] wait_player;
	private BufferedImage[] player_damaged;

	
	public double life = 100 , maxLife = 100;
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		right_player = new BufferedImage[4];
		left_player = new BufferedImage[4];
		up_player = new BufferedImage[4];
		down_player = new BufferedImage[4];
		wait_player = new BufferedImage[1];
		player_damaged = new BufferedImage[2];
		
		//right
		for(int i = 0; i < 4; i++)
		{
			right_player[i] = Game.spritesheet.getSprite(35 + (i*16+i),35,16,16);
			
		}
		//left
		for(int i = 0; i < 4; i++)
		{
			left_player[i] = Game.spritesheet.getSprite(35 + (i*16+i),52,16,16);
		}
		//up
		for(int i = 0; i < 4; i++)
		{
			up_player[i] = Game.spritesheet.getSprite(35 + (i*16+i),18,16,16);
		}
		//down
		for(int i = 0; i < 4; i++)
		{
			down_player[i] = Game.spritesheet.getSprite(35 + (i*16+i),1,16,16);
		}
		//Hurt
		for(int i = 0; i < 2; i++)
		{
			player_damaged[i] = Game.spritesheet.getSprite(120 + (i*16+i),52,16,16);
		}
	
		
	}
	
	public void tick()
	{
	 moves = false;
	 if(right && World.isFree( (int)(x+speed), this.getY()))
	 {
		this.setX(x+=speed);
		wait_player[0] = Game.spritesheet.getSprite(35,35,16,16);
		wait_gun = 0;
		moves = true;
	 } else if(left && World.isFree( (int)(x-speed), this.getY()))
	 	{
		 this.setX(x-=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,52,16,16);
		 wait_gun = 1;
		 moves = true;
	 	}
	 if(up && World.isFree(this.getX(), (int)(y-speed)))
	 {
		 this.setY(y-=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,18,16,16);
		 wait_gun = 2;
		 moves = true;
	 } else if(down && World.isFree(this.getX(), (int)(y + speed)))
	 	{
		 this.setY(y+=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,1,16,16);
		 wait_gun = 3;
		 moves = true;
	 	}
	 
	 if(moves)
	 {
		 curFrames++;
		 if(curFrames == targetFrames)
		 {
			 curFrames = 0;
			 curAnimation++;
			 if(curAnimation == right_player.length)
			 {
				 curAnimation = 0;
			 }
		 }
	 }
	 
	 if(hurt)
	 {
		 curFramesHurt++;
		 if(curFramesHurt == targetFramesHurt)
		 {
			 curFramesHurt = 0;
			 curAnimationHurt++;
			 if(curAnimationHurt == player_damaged.length)
			 {
				 curAnimationHurt = 0;
				 this.hurt = false;
			 }
		 }
	 }
	 
	 this.checkCollisionLifeHeart();
	 this.checkCollisionAmmo();
	 this.checkCollisionGun();
	 
	 if(life <= 0)
		{
		 Game.entities.clear();
		 Game.enemies.clear();
		 Game.entities = new ArrayList<Entity>();
		 Game.enemies = new ArrayList<EnemySlime>();
		 Game.spritesheet = new Spritesheet("/spritesheet.png");
		 Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(35, 1, 16, 16));
		 Game.entities.add(Game.player);
		 Game.world = new World("/map.png");
		 return;
		}
	 
	 //Camera do jogador
	 Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2) + (this.width / 2), 0, World.WIDTH*16 - Game.WIDTH);
	 Camera.y = Camera.clamp(this.getY() -  (Game.HEIGHT/2) + (this.height / 2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionLifeHeart()
	 {
		 for(int i = 0; i < Game.entities.size(); i++)
		 {
			 Entity atual = Game.entities.get(i);
			 if(atual instanceof HeartLife)
			 {
				if(Entity.isColliding(this, atual))
				{
					life += 10;
					if (life > 100) life = 100;
					
					Game.entities.remove(atual);
				}
			 }
		 }
	 }
	
	public void checkCollisionGun()
	{
		 for(int i = 0; i < Game.entities.size(); i++)
		 {
			 Entity atual = Game.entities.get(i);
			 if(atual instanceof Gun)
			 {
				if(Entity.isColliding(this, atual))
				{
					hasGun = true;
					Game.entities.remove(atual);
				}
			 }
		 }
	}
	
	public void checkCollisionAmmo()
	{
		 for(int i = 0; i < Game.entities.size(); i++)
		 {
			 Entity atual = Game.entities.get(i);
			 if(atual instanceof Ammo)
			 {
				if(Entity.isColliding(this, atual))
				{
					ammo += 10;
					Game.entities.remove(atual);
				}
			 }
		 }
	}
	
	public void render(Graphics g)
	{
		if(!hurt)
		{
			if(right)
			{
				g.drawImage(right_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
				if(hasGun)
				{
					//Desenhar arma
					g.drawImage(Entity.Gun_Right,this.getX() + 8 - Camera.x,this.getY() - Camera.y, null);
					
				}
			}
			else if(left)
			{
				g.drawImage(left_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
				if(hasGun)
				{
					//Desenhar arma
					g.drawImage(Entity.Gun_Left,this.getX() -2  - Camera.x,this.getY() - Camera.y, null);
				}
			} else if(up)
			{
				if(hasGun)
				{
					//Desenhar arma
					g.drawImage(Entity.Gun_Up,this.getX() + 4 - Camera.x,this.getY() - Camera.y, null);
				}
				g.drawImage(up_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
			}  else if(down)
			{
				g.drawImage(down_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
				if(hasGun)
				{
					//Desenhar arma
					g.drawImage(Entity.Gun_Down,this.getX() + 3 - Camera.x,this.getY() - Camera.y, null);
				}
			} else 
			{
				if(hasGun)
				{
					if(wait_gun == 2)
					{
						g.drawImage(Entity.Gun_Up,this.getX() + 4 - Camera.x,this.getY() - Camera.y, null);
					}
				}
				g.drawImage(wait_player[0] == null? wait_player[0] = Game.spritesheet.getSprite(35,1,16,16) : wait_player[0] ,this.getX() - Camera.x ,this.getY() - Camera.y, null);
				if(hasGun)
				{
					if(wait_gun == 0)
					{
						g.drawImage(Entity.Gun_Right,this.getX() + 8 - Camera.x,this.getY() - Camera.y, null);
						
					}
					else if(wait_gun == 1)
					{
						g.drawImage(Entity.Gun_Left,this.getX() -2  - Camera.x,this.getY() - Camera.y, null);
					}
				
					else if(wait_gun == 3)
					{
						g.drawImage(Entity.Gun_Down,this.getX() + 3 - Camera.x,this.getY() - Camera.y, null);
					}
				}
			}
		} else 
		{
			g.drawImage(player_damaged[curAnimationHurt], this.getX() - Camera.x, this.getY() - Camera.y , null);
		}
		
		
	}

	

}
