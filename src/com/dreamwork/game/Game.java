package com.dreamwork.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.dreamwork.entities.Entity;
import com.dreamwork.entities.Player;
import com.dreamwork.graphic.Spritesheet;
import com.dreamwork.world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//-- Tamanho da Tela -- //
	public static int WIDTH = 240, HEIGHT = 160;
	public static int SCALE = 3;
	//variaveis
	private BufferedImage image;
	public static  List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	
	
	
	// -- INICIALIZADOR TICK(FPS - GAME )-- //
	boolean isRunning = true;
	

	 //Estancia da classe Game
	 public Game()
	 {
	 // -- Tamnho da tela -- //
	 this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
	 initFrame();
	 // -- Inicializado teclado -- //
	 this.addKeyListener(this);
	 // INICIALIZANDO OBJETOS.
	 image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	 entities = new ArrayList<Entity>();
	 spritesheet = new Spritesheet("/spritesheet.png");
	 player = new Player(0,0,16,16,spritesheet.getSprite(35, 1, 16, 16));
	 entities.add(player);
	 world = new World("/map.png");
	 }
	 
	 public void initFrame()
	 {
		 JFrame frame = new JFrame();
			frame.add(this);
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
	 }
	 
	 public void tick()
	 {
		 for(int i = 0; i < entities.size(); i++)
		 {
			 Entity e = entities.get(i);
			 e.tick();
		 }
	 }
	 
	 public void render()
	 {
	 //Renderizando graficos
		 BufferStrategy bs = this.getBufferStrategy();
			if(bs == null)
			{
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			
			g.setColor(new Color(90,90,90));
			g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			
			//--- World ------//
			world.render(g);
			
			//--- Entities ---//
			 for(int i = 0; i < entities.size(); i++)
			 {
				 Entity e = entities.get(i);
				 e.render(g);
			 }
			//---------------//
			
			g.dispose();
			g = bs.getDrawGraphics();
		    g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null );
			
			
			bs.show();
	 }
	 //Função principal
	 public static void main(String[] args)
	 {
		Game game = new Game();
		//Configuração da tela.
		
		
		new Thread(game).start();
	 }
	 
	@Override
	public void run() 
	{
		// Tick(FPS)
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning)
		{
		 long now = System.nanoTime();
		 delta += (now - lastTime) / ns;
		 lastTime = now;
		 if(delta >= 1)
		 {
			 tick();
			 render();
			 frames++;
			 delta--;
		 }
		 
		 if(System.currentTimeMillis() - timer >= 1000)
		 {
			 System.out.println("FPS" + frames);
			 frames = 0;
			 timer += 1000;
			 
		 }
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.down = false;
		}
	}
	
	
	
	
	
	
	
}
