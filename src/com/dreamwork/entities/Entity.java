package com.dreamwork.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entity {

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
		g.drawImage(sprite, this.getX(),this.getY(), null);
	}
	
}
