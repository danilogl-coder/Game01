package com.dreamwork.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dreamwork.entities.Player;
import com.dreamwork.game.Game;

public class Ui {
	
	private BufferedImage lifeBar = Game.spritesheet.getSprite(1, 141, 50, 8);

	public void render(Graphics g)
	{
		
		
		g.setColor(Color.red);
		g.fillRect(20, 4, 50, 8);
		g.setColor(Color.GREEN);
		g.fillRect(20, 4, (int)((Player.life / Player.maxLife) * 50), 8);
		g.drawImage(lifeBar, 20, 4, 50, 8, null);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Player.life+"/"+(int)Player.maxLife, 30, 11);
	}
}
