package com.dreamwork.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu 
{
	public String[] options = {"novo jogo", "carregar jogo", "sair"};
	public int currentOption = 0;
	public int maxOption = options.length -1;
	public boolean up, down;
	public void tick()
	{
		if(up)
		{
			up = false;
			currentOption--;
			if(currentOption < 0)
			{
				currentOption = maxOption;
			}
		}
		if(down)
		{
			down = false;
			currentOption++;
			if(currentOption > maxOption)
			{
				currentOption = 0;
			}
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,30));
		g.drawString("Legend Ninja", (Game.WIDTH*Game.SCALE) / 2 - 110, (Game.HEIGHT*Game.SCALE) / 2 - 160);
		
		//Opções de menu
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,24));
		g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE) / 2 - 70, 160);
		g.drawString("Carregar jogo", (Game.WIDTH*Game.SCALE) / 2 - 70, 190);
		g.drawString("Sair do jogo", (Game.WIDTH*Game.SCALE) / 2 - 70, 220);
		
		if(options[currentOption] == "novo jogo")
		{
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 90, 160);
		}else if(options[currentOption] == "carregar jogo")
		{
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 90, 190);
		}else if(options[currentOption] == "sair")
		{
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 90, 220);
		}
	}
}
