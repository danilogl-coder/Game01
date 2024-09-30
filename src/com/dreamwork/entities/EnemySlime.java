package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.dreamwork.game.Game;
import com.dreamwork.game.Sound;
import com.dreamwork.world.AStar;
import com.dreamwork.world.Camera;
import com.dreamwork.world.Vector2i;
import com.dreamwork.world.World;

public class EnemySlime extends Entity {
	
	private double speed = 0.5;
	
	
	private BufferedImage[] spriteSlime;
	private boolean moves;
	public int curAnimation = 0;
	public int curFrames = 0, targetFrames = 11;
	private int life = 5;
	private boolean isDamaged = false;
	int DamagedFrames = 0;
	int DamageTargetFrames = 10;

	public EnemySlime(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub

	
		spriteSlime = new BufferedImage[4];
		
		for(int i = 0; i < spriteSlime.length; i++)
		{
			spriteSlime[i] = Game.spritesheet.getSprite(35 + (i*16+i),69,16,16);
		}
	}
	
	public void tick()
	{
		depth = 0;
		moves= false;
		/*
		if(this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 100)
		{
		if(isCollidingWithPlayer() ==  false || Game.player.z > 0)
		{
		
		if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isColliding((int)(x+speed), this.getY()))
		{
			moves = true;
			x += speed;
		} else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) && !isColliding((int)(x-speed), this.getY()))
		{
			moves = true;
			x -= speed;
		}
		if((int)y < Game.player.getY() && World.isFree( this.getX(), (int)(y+speed)) && !isColliding(this.getX(), (int)(y+speed)))
		{
			moves = true;
			y += speed;
		} else if((int)y > Game.player.getY() && World.isFree( this.getX(), (int)(y-speed)) && !isColliding(this.getX(), (int)(y-speed)))
		{
			moves = true;
			y -= speed;
		}
		
		} else 
		{
			Game.player.hurt = true;
			//Estamos colidindo
			
			if(Game.rand.nextInt(100) < 10)
			{
			Sound.Hurt.play();
			Game.player.life -= 5;
			
			}
			System.out.println(Game.player.life);
			
			
		}
		
		}
		*/
		if(!isCollidingWithPlayer())
		{
			if(path == null || path.size() == 0)
			{
				Vector2i start = new Vector2i((int)(x / 16),(int)(y/16));
				Vector2i end = new Vector2i((int)(Game.player.x / 16),(int)(Game.player.y/16));
				path = AStar.findPath(Game.world,start,end);
			}
		} else
		{
			if(new Random().nextInt(100) < 5)
			{
				Sound.Hurt.play();
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.hurt = true;
			}
		}
		
		if(new Random().nextInt(100) < 70)
		followPath(path);
		if(new Random().nextInt(100) < 5)
		 if(moves)
		 {
			 curFrames++;
			 if(curFrames == targetFrames)
			 {
				 curFrames = 0;
				 curAnimation++;
				 if(curAnimation == spriteSlime.length)
				 {
					 curAnimation = 0;
				 }
			 }
		 }
		 
		 collidingBullet();
		 
		 if(life == 0)
		 {
			 destroySelf();
		 }
		 
		 if(isDamaged)
		 {
		
			DamagedFrames++;
			if(DamagedFrames == DamageTargetFrames)
			{
				DamagedFrames = 0;
				isDamaged = false;
			}
		 }
	}
	
	public void destroySelf()
	{
		Game.enemies.remove(this);
		Game.entities.remove(this);
		return;
	}
	public void collidingBullet()
	{
		for(int i = 0; i < Game.bullet.size(); i++)
		{
			Entity e = Game.bullet.get(i);
			if(e instanceof Bullet)
			{
				if(Entity.isColliding(this, e))
				{
					life --;
					isDamaged = true;
					Game.bullet.remove(e);
					return;
				}
			}
		}
	
	}
	
	public boolean isCollidingWithPlayer()
	{
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.player.getHeight());
		return enemyCurrent.intersects(player);
	}
	
	
	
	public void render(Graphics g)
	{
		//super herda tudo do metodo pai 
		//super.render(g);
		if(isDamaged)
		{
			g.drawImage(Entity.EnemySlime_FEDBACK, this.getX() - Camera.x,this.getY() - Camera.y, null);
		
		}else {
		if(moves)
		{
		g.drawImage(spriteSlime[curAnimation], this.getX() - Camera.x,this.getY() - Camera.y, null);
		}else 
		{
		g.drawImage(spriteSlime[0], this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
		}
		
		//mask
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
	}
