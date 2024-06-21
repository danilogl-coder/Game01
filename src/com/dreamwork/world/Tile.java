package com.dreamwork.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dreamwork.game.Game;

public class Tile {

	public static BufferedImage Tile_Grass1 = Game.spritesheet.getSprite(1, 1, 16, 16);
	public static BufferedImage Tile_Grass2 = Game.spritesheet.getSprite(1, 18, 16, 16);
	public static BufferedImage Tile_Wall = Game.spritesheet.getSprite(18, 1, 16, 16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite)
	{
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(sprite, x, y, null);
	}
}
