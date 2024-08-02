package com.dreamwork.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.dreamwork.entities.Ammo;
import com.dreamwork.entities.EnemySlime;
import com.dreamwork.entities.Entity;
import com.dreamwork.entities.Gun;
import com.dreamwork.entities.HeartLife;
import com.dreamwork.entities.Player;
import com.dreamwork.game.Game;
import com.dreamwork.graphic.Spritesheet;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int Tile_Size = 16;
	
	public World(String path)
	{
		try 
		{
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[WIDTH * HEIGHT];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++)
			{
				for(int yy = 0; yy < map.getHeight(); yy++)
				{
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.Tile_Grass1);
					if(pixelAtual == 0xFF00FF21)
					{
						//Grama 1
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.Tile_Grass1);
					}
					else if(pixelAtual == 0xFF00E51A)
					{
						//Grama 2
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.Tile_Grass2);
					}
					else if(pixelAtual == 0xFF404040)
					{
						//Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.Tile_Wall);
					}
					else if(pixelAtual == 0xFFFF7F7F)
					{
						//Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
					}
					else if(pixelAtual == 0xFF7F0000)
					{
						//EnemeySlime
						EnemySlime en = new EnemySlime(xx*16,yy*16,16,16,Entity.EnemySlime_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}
					else if(pixelAtual == 0xFFFF0000)
					{
						//Heart
						Game.entities.add(new HeartLife(xx*16,yy*16,16,16,Entity.LifeHeart_EN));
					}
					else if(pixelAtual == 0xFFFFD800)
					{
						//Ammo
						Game.entities.add(new Ammo(xx*16,yy*16,16,16,Entity.Ammo_EN));
					}
					else if(pixelAtual == 0xFF000000)
					{
						//Gun
						Game.entities.add(new Gun(xx*16,yy*16,16,16,Entity.Weapon_EN));
					}
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void restartGame(String level)
	{
		 Game.entities.clear();
		 Game.enemies.clear();
		 Game.entities = new ArrayList<Entity>();
		 Game.enemies = new ArrayList<EnemySlime>();
		 Game.spritesheet = new Spritesheet("/spritesheet.png");
		 Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(35, 1, 16, 16));
		 Game.entities.add(Game.player);
		 Game.world = new World("/"+level);
		 return;
	}
	
	public static boolean isFree(int xNext, int yNext)
	{
		int x1 = xNext/Tile_Size;
		int y1 = yNext/Tile_Size;
		
		int x2 = (xNext+Tile_Size - 1) /Tile_Size;
		int y2 = yNext/Tile_Size;
		
		int x3 = xNext/Tile_Size;
		int y3 = (yNext+Tile_Size - 1) /Tile_Size;
		
		int x4 = (xNext+Tile_Size - 1) /Tile_Size;
		int y4 = (yNext+Tile_Size - 1) /Tile_Size;
		
		/*if(!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) || 
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) || 
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile) ))
		{
			return true;
		}
		if(Game.player.z > 0)
		{
			return true;
		}
		return false;
		*/
		
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) || 
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) || 
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile) );
		
	}
	
	public void render(Graphics g) 
	{
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		
		for(int xx = xstart; xx <= xfinal; xx++)
		{
			for(int yy = ystart; yy <= yfinal; yy++)
			{
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
				{
					continue;
				}
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
