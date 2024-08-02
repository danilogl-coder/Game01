package com.dreamwork.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dreamwork.world.World;

public class Menu 
{
	public String[] options = {"novo jogo", "carregar jogo", "sair", "continuar"};
	public int currentOption = 0;
	public int maxOption = options.length -1;
	public boolean up, down, enter;
	public static boolean pause = false;
	public static boolean savesExist = false;
	public static boolean saveGame = false;
	
	public void tick()
	{
		File file = new File("save.txt");
		if(file.exists())
		{
			savesExist = true;
		}else
		{
			savesExist = false;
		}
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
		if(enter)
		{
			enter = false;
			if(options[currentOption] == "novo jogo" || options[currentOption] == "continuar")
			{
				
				Game.gameState = "normal";
				pause = false;
			    file = new File("save.txt");
				file.delete();
				
			}
			else if(options[currentOption] == "carregar jogo")
			{
				file = new File("save.txt");
				if(file.exists())
				{
					String saver = loadGame(10);
					applySave(saver);
				}
			}
			else if(options[currentOption] == "sair" )
			{
				System.exit(3);
			}
		}
	}
	
	public static void applySave(String str)
	{
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++)
		{
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) 
			{
			case "level": 
			
				World.restartGame("level"+spl2[1]+".png");
				Game.gameState = "normal";
				pause = false;
				break;
			
			case "vida":
			
				Game.player.life = Integer.parseInt(spl2[1]);
				break;
			
			
			
			}
		}
	}
	
	public static String loadGame(int encode)
	{
		String line = "";
		File file = new File("save.txt");
		if(file.exists())
		{
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null)
					{
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++)
						{
							val[i]-=encode;
							trans[1]+=val[i];
						}
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				} catch (IOException e) {
					// TODO: handle exception
				}
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, int[]val2, int encode)
	{
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++)
		{
			String current = val1[i];
			current+=":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++)
			{
				value[n]+=encode;
				current+=value[n];
			}
			try {
				writer.write(current);
				if(i < val1.length - 1)
					writer.newLine();
			} catch (IOException e) {
				// TODO: handle exception
			}
			
		}
		
		try {
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO: handle exception
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
		if(pause == false)
		{
			g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE) / 2 - 70, 160);
		} else 
		{
			g.drawString("Continuar", (Game.WIDTH*Game.SCALE) / 2 - 70, 160);
		}
		
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
