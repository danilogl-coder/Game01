package com.dreamwork.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dreamwork.game.Game;
import com.dreamwork.world.Camera;

public class Bullet extends Entity{
	


	private double dx;
	private double dy;
	private double spd = 3;
	private int timer = 30, curTimer =0;
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) 
	{
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.dx = dx;
		this.dy = dy;
	}


	

 public void tick()
 {
	 x +=dx*spd;
	 y +=dy*spd;
	 curTimer++;
	 if(curTimer == timer)
	 {
		 curTimer = 0;
		 Game.bullet.remove(this);
	 }
	 
 }
 
 public void render(Graphics g)
 {
	 g.setColor(Color.yellow);
	 if(Game.player.up || Game.player.wait_gun == 2)
	 {
		 g.fillOval(this.getX() - Camera.x +9, this.getY() - Camera.y, width, height);
	 } else if(Game.player.down || Game.player.wait_gun == 3)
	 {
		 g.fillOval(this.getX() - Camera.x +9, this.getY() - Camera.y + 10, width, height);
	 }else if(Game.player.right || Game.player.wait_gun == 0)
	 {
		 g.fillOval(this.getX() - Camera.x +20, this.getY() - Camera.y + 6, width, height);
	 }else if(Game.player.left || Game.player.wait_gun == 1)
	 {
		 g.fillOval(this.getX() - Camera.x -3, this.getY() - Camera.y + 6, width, height);
	 }
	 
 }
}
