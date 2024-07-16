package com.dreamwork.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.dreamwork.entities.Bullet;
import com.dreamwork.entities.EnemySlime;
import com.dreamwork.entities.Entity;
import com.dreamwork.entities.Player;
import com.dreamwork.graphic.Spritesheet;
import com.dreamwork.graphic.Ui;
import com.dreamwork.world.Camera;
import com.dreamwork.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String gameState = "menu";
	//-- Tamanho da Tela -- //
	public static int WIDTH = 240, HEIGHT = 160;
	public static int SCALE = 3;
	//variaveis
	private BufferedImage image;
	private int Cur_Level = 1, Max_Level = 2;
	public static  List<Entity> entities;
	public static List<EnemySlime> enemies;
	public static List<Bullet> bullet;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	public static Random rand;
	private boolean showMessageGameOver = true;
	private boolean restartGame = false;
	private int framesGameOver = 0;
	public Ui ui;
	public Menu menu;
	
	
	// -- INICIALIZADOR TICK(FPS - GAME )-- //
	boolean isRunning = true;
	

	 //Estancia da classe Game
	 public Game()
	 {
	 //Estancia do Random
	 rand = new Random();
	 // -- Tamanho da tela -- //
	 this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
	 initFrame();
	 // -- Inicializado teclado e mouse-- //
	 this.addKeyListener(this);
	 this.addMouseListener(this);
	 // INICIALIZANDO OBJETOS.
	 image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	 entities = new ArrayList<Entity>();
	 enemies = new ArrayList<EnemySlime>();
	 bullet = new ArrayList<Bullet>();
	 spritesheet = new Spritesheet("/spritesheet.png");
	 menu = new Menu();
	 // Inicializando UI(Vida)
	 ui = new Ui();
	 // --------------//
	 player = new Player(0,0,16,16,spritesheet.getSprite(35, 1, 16, 16));
	 entities.add(player);
	 world = new World("/level1.png");
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
		 if(gameState == "normal")
		 {
		 this.restartGame = false;
		 for(int i = 0; i < entities.size(); i++)
		 {
			 Entity e = entities.get(i);
			 e.tick();
		 }
		 
		 for(int i = 0; i < bullet.size(); i++)
		 {
			 Bullet t = bullet.get(i);
			 t.tick();
		 }
		 
		 if(enemies.size() == 0)
		 {
			 Cur_Level++;
			 if(Cur_Level > Max_Level)
			 {
				 Cur_Level = 1;
			 }
			 String newWorld = "level"+Cur_Level+".png";
			 World.restartGame(newWorld);
		 } 
		 } else if(gameState == "gameOver")
		 {
			 this.framesGameOver ++;
			 if(this.framesGameOver == 25)
			 {
				 this.framesGameOver = 0;
				 if(this.showMessageGameOver) 
				
					 this.showMessageGameOver = false;
					 else
					 this.showMessageGameOver = true;
				 
			}
		 } else if(gameState == "menu")
		 {
			 menu.tick();
		 }
		 
		 if(restartGame)
		 {
			 Game.gameState = "normal";
			 this.restartGame = false;
			 Cur_Level = 1;
			 String newWorld = "level"+Cur_Level+".png";
			 World.restartGame(newWorld); 
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
			 
			 //-- Bullet ---//
			 for(int i = 0; i < bullet.size(); i++)
			 {
				 Bullet r = bullet.get(i);
				 r.render(g);
			 }
			
			//Render Ui //
			ui.render(g);
			 
			
			g.dispose();
			g = bs.getDrawGraphics();
		    g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null );
		    g.setFont(new Font("arial",Font.BOLD,20));
		    g.setColor(Color.white);
		    g.drawString("Munição: " + player.ammo, 600,18);
		    
		    if(gameState == "gameOver")
		    {
		    	Graphics2D g2 = (Graphics2D)g;
		    	g2.setColor(new Color(0,0,0,100));
		    	g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		    	
		    	  g.setFont(new Font("arial",Font.BOLD,32));
				  g.setColor(Color.white);
				  g.drawString("Game Over", (WIDTH*SCALE) / 2 - 50, (HEIGHT*SCALE) / 2 - 20);
				  g.setFont(new Font("arial",Font.BOLD,20));
				  if(showMessageGameOver)
				  {
					  
					  g.drawString("> Pressione enter para iniciar <", (WIDTH*SCALE) / 2 - 130, (HEIGHT*SCALE) / 2 + 10);
				  }
				
		    } else if(gameState == "menu")
		    {
		    	menu.render(g);
		    }
			
			
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
		//Pedir foco na tela
		requestFocus();
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
			if(gameState == "menu")
			{
				menu.up = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.down = true;
			if(gameState == "menu")
			{
				menu.down = true;
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			this.restartGame = true;
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
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseShoot = true;
		player.mx = e.getX() / 3;
		player.my = e.getY() / 3; 
;	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
}
