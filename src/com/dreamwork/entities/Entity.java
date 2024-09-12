package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.dreamwork.game.Game;
import com.dreamwork.world.Camera;
import com.dreamwork.world.Node;
import com.dreamwork.world.Vector2i;

public class Entity {

	public static BufferedImage LifeHeart_EN = Game.spritesheet.getSprite(120, 1, 16, 16);
	public static BufferedImage Weapon_EN = Game.spritesheet.getSprite(120, 18, 16, 16);
	public static BufferedImage Ammo_EN = Game.spritesheet.getSprite(120, 35, 16, 16);
	public static BufferedImage EnemySlime_EN = Game.spritesheet.getSprite(35, 69, 16, 16);
	public static BufferedImage EnemySlime_FEDBACK = Game.spritesheet.getSprite(35, 86, 16, 16);
	public static BufferedImage Gun_Left = Game.spritesheet.getSprite(137, 18, 16, 16);
	public static BufferedImage Gun_Right = Game.spritesheet.getSprite(120, 18, 16, 16);
	public static BufferedImage Gun_Up = Game.spritesheet.getSprite(137, 1, 16, 16);
	public static BufferedImage Gun_Down = Game.spritesheet.getSprite(137, 35, 16, 16);
	
	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	protected int maskx,  masky,  mwidth,  mheight;
	protected List<Node> path;
	
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public boolean isColliding(int xnext, int ynext)
	{
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, mwidth, mheight);
		for(int i = 0; i < Game.enemies.size(); i++)
		{
			EnemySlime e = Game.enemies.get(i);
			if(e == this)
			{
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, mwidth, mheight);
			if(enemyCurrent.intersects(targetEnemy))
			{
				return true;
			}
		}
		return false;
	}
	
	public void followPath(List<Node> path)
	{
		if(path != null)
		{
			if(path.size() > 0)
			{
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16 &&  !isColliding(this.getX()+1, this.getY()))
				{
					x++;
				}else if(x > target.x * 16 &&  !isColliding(this.getX()-1, this.getY()))
				{
					x--;
				}
				
				if(y < target.y * 16 &&  !isColliding(this.getX(), this.getY() +1))
				{
					y++;
				}else if(y > target.y * 16 &&  !isColliding(this.getX(), this.getY() - 1))
				{
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16)
				{
					path.remove(path.size() - 1);
				}
			}
		}
	}
	public void setMask(int maskx, int masky, int mwidth, int mheight)
	{
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public static boolean isColliding(Entity e1, Entity e2)
	{
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z)
		{
			return true;
		}
		
		return false;
	}
	
	// --- GET ----- //
	public int getX()
	{
	return (int)this.x;
	}
	
	public int getY()
	{
	return (int)this.y;
	}
	
	public int getWidth()
	{
	return this.width;
	}
	
	public int getHeight()
	{
	return this.height;
	}
	
	// --- SET ---- //
	public double setX(double newX)
	{
	return this.x = newX;
	}
	public double setY(double newY)
	{
	return this.y = newY;
	}
	public int setWidth(int newWidth)
	{
	return this.width = newWidth;
	}
	public int setHeight(int newHeight)
	{
	return this.height = newHeight;
	}
	
	public void tick()
	{
		
	}
	
	public double calculateDistance(int x1, int y1, int x2,int y2)
	{
		return Math.sqrt((x1 - x2) * (x1-x2) + (y1 - y2) * (y1 - y2));
	}
	
	
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y, null);
	}
	
}
