package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dreamwork.game.Game;
import com.dreamwork.world.Camera;
import com.dreamwork.world.World;



public class Player extends Entity
{
	public boolean right,left,up,down;
	public double speed  = 1.0;
	public int curAnimation = 0;
	public int curFrames = 0, targetFrames = 10;
	private boolean moves;
	private BufferedImage[] right_player;
	private BufferedImage[] left_player;
	private BufferedImage[] up_player;
	private BufferedImage[] down_player;
	private BufferedImage[] wait_player;
	
	public static double life = 100 , maxLife = 100;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		right_player = new BufferedImage[4];
		left_player = new BufferedImage[4];
		up_player = new BufferedImage[4];
		down_player = new BufferedImage[4];
		wait_player = new BufferedImage[1];
		
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
	
		
	}
	
	public void tick()
	{
	 moves = false;
	 if(right && World.isFree( (int)(x+speed), this.getY()))
	 {
		this.setX(x+=speed);
		wait_player[0] = Game.spritesheet.getSprite(35,35,16,16);
		moves = true;
	 } else if(left && World.isFree( (int)(x-speed), this.getY()))
	 	{
		 this.setX(x-=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,52,16,16);
		 moves = true;
	 	}
	 if(up && World.isFree(this.getX(), (int)(y-speed)))
	 {
		 this.setY(y-=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,18,16,16);
		 moves = true;
	 } else if(down && World.isFree(this.getX(), (int)(y + speed)))
	 	{
		 this.setY(y+=speed);
		 wait_player[0] = Game.spritesheet.getSprite(35,1,16,16);
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
	 
	 //Camera do jogador
	 Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2) + (this.width / 2), 0, World.WIDTH*16 - Game.WIDTH);
	 Camera.y = Camera.clamp(this.getY() -  (Game.HEIGHT/2) + (this.height / 2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void render(Graphics g)
	{
		if(right)
		{
			g.drawImage(right_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
		else if(left)
		{
			g.drawImage(left_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
		} else if(up)
		{
			g.drawImage(up_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
		}  else if(down)
		{
			g.drawImage(down_player[curAnimation],this.getX() - Camera.x,this.getY() - Camera.y, null);	
		} else 
		{
			g.drawImage(wait_player[0] == null? wait_player[0] = Game.spritesheet.getSprite(35,1,16,16) : wait_player[0] ,this.getX() - Camera.x ,this.getY() - Camera.y, null);	
		}
		
	}

	

}
