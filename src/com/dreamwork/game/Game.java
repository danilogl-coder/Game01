package com.dreamwork.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.dreamwork.entities.Bullet;
import com.dreamwork.entities.EnemySlime;
import com.dreamwork.entities.Entity;
import com.dreamwork.entities.Player;
import com.dreamwork.graphic.Spritesheet;
import com.dreamwork.graphic.Ui;
import com.dreamwork.world.Camera;
import com.dreamwork.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
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
	public boolean saveGame = false;
	private int framesGameOver = 0;
	public int mx, my;
	//public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelart.ttf");
	//public Font newFont;
	public Ui ui;
	public Menu menu;
	
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	
	// -- INICIALIZADOR TICK(FPS - GAME )-- //
	boolean isRunning = true;
	

	 //Estancia da classe Game
	 public Game()
	 {
	 //Musica
	 //Sound.MusicBackground.loop();
		 
	 //Estancia do Random
	 rand = new Random();
	 // -- Tamanho da tela -- //
	 this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
	 initFrame();
	 // -- Inicializado teclado e mouse-- //
	 this.addKeyListener(this);
	 this.addMouseListener(this);
	 this.addMouseMotionListener(this);
	 // INICIALIZANDO OBJETOS.
	 image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	 pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	 try {
		lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
	 lightmap.getRGB(0,0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels,0, lightmap.getWidth());
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
	 
	 /* New font
	 try {
		newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(30f);
	} catch (FontFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 */
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
		
		 if(this.saveGame)
		 {
			 this.saveGame = false;
			 String[] opt1 = {"level","vida"};
			 int[] opt2 = {this.Cur_Level,(int)player.life};
			 Menu.saveGame(opt1,opt2,10);
			 System.out.println("Jogo salvo");
		 }
		 
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
	 /*
	 public void drawRectangleExample(int xoff, int yoff)
	 {
		 for(int xx = 0; xx < 32; xx++)
		 {
			 for(int yy = 0; yy < 32; yy++)
			 {
				 int xOff = xx + xoff;
				 int yOff = yy + yoff;
				 if(xOff < 0 || yOff < 0 || xOff >= WIDTH || yOff >= HEIGHT)
					 continue;
				 pixels[xOff + (yOff * WIDTH)] = 0xff0000;
			 }
		 }
	 }
	 */
	 /*public void applyLight()
	 {
		 for(int xx = 0; xx < Game.WIDTH; xx++)
		 {
			 for(int yy = 0; yy < Game.HEIGHT; yy++)
			 {
				 if(lightMapPixels[xx + (yy * Game.WIDTH)] == 0xffffffff)
				 {
					 pixels[xx+(yy * Game.WIDTH)] = 0;
				 }else
				 {
					 continue;
				 }
				 
			 }
		 }
	 }
	 */
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
			
			//applyLight();
			//Render Ui //
			ui.render(g);
			 
			
			g.dispose();
			g = bs.getDrawGraphics();

		    g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null );
		    g.setFont(new Font("arial",Font.BOLD,20));
		    g.setColor(Color.white);
		    g.drawString("Munição: " + player.ammo, 600,18);
		    
		    //g.setFont(newFont);
		    //g.drawString("Teste com a nova fonte", 20, 20);
		    
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
		    /*
		    Graphics2D g2 = (Graphics2D)g;
		    double angleMouse = Math.atan2(my - 200+25, mx - 200+25);
		    g2.rotate(angleMouse,200+25,200+25);
			g.setColor(Color.red);
			g.fillRect(200, 200, 50, 50);
			*/
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
		
		if(e.getKeyCode() == KeyEvent.VK_X) {
			player.jump = true;
		}
		
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
			if(menu.options[menu.currentOption] == "new game" || player.life == 0 || player.life < 0){
				this.restartGame = true;
			} 
			if(gameState == "menu")
			{
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			gameState = "menu";
			menu.pause = true;
			
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(Game.gameState == "normal")
			{
				this.saveGame = true;
			}
			
			
			
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


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mx = e.getX();
		this.my = e.getY();
	}
	
	
	
	
	
	
	
}
