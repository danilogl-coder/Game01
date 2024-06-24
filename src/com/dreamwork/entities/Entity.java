package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dreamwork.game.Game;
import com.dreamwork.world.Camera;

public class Entity {

	public static BufferedImage LifeHeart_EN = Game.spritesheet.getSprite(120, 1, 16, 16);
	public static BufferedImage Weapon_EN = Game.spritesheet.getSprite(120, 18, 16, 16);
	public static BufferedImage Ammo_EN = Game.spritesheet.getSprite(120, 35, 16, 16);
	public static BufferedImage EnemySlime_EN = Game.spritesheet.getSprite(35, 69, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y, null);
	}
	
}
