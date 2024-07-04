package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.dreamwork.game.Game;
import com.dreamwork.world.Camera;
import com.dreamwork.world.World;

public class EnemySlime extends Entity {
	
	private double speed = 0.5;
	
	private int maskx = 8,  masky = 8,  maskw = 8,  maskh = 8;
	private BufferedImage[] spriteSlime;
	private boolean moves;
	public int curAnimation = 0;
	public int curFrames = 0, targetFrames = 11;

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
		moves= false;
		
		if(isCollidingWithPlayer() ==  false)
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
			
			Game.player.life -= 5;
			}
			System.out.println(Game.player.life);
			
			
		}
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
	}
	
	public boolean isCollidingWithPlayer()
	{
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.player.getHeight());
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xnext, int ynext)
	{
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for(int i = 0; i < Game.enemies.size(); i++)
		{
			EnemySlime e = Game.enemies.get(i);
			if(e == this)
			{
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy))
			{
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g)
	{
		//super herda tudo do metodo pai 
		//super.render(g);
		if(moves)
		{
		g.drawImage(spriteSlime[curAnimation], this.getX() - Camera.x,this.getY() - Camera.y, null);
		}else 
		{
		g.drawImage(spriteSlime[0], this.getX() - Camera.x,this.getY() - Camera.y, null);
		}
		
		//mask
		//g.setColor(Color.BLUE);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
	}
