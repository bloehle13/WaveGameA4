package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private BufferedImage img;
	private Image fish;
	private Image title;
	private Image shell;
	private int fishPos;
	private int fishPosY;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to5 spawner;
	public Image image,image2;
	

	
	public Menu(Game game, Handler handler, HUD hud, Spawn1to5 spawner) {
		//fish = getImage("images/TrumpImage.png");
		//title = getImage("images/title.png");
		//shell = getImage("images/seashell.png");
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		fishPos = 0;
		fishPosY = 300;
		r = new Random();
		addColors();
		//Old method to read images *Changed since does not support gifs!*
		/*
		img = null;
		try {
			img = ImageIO.read(new File("images/background2.gif"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		 */
			
		//List of new potential menu screens
		//image = new ImageIcon("images/background1.jpg").getImage();
	   // image = getImage("images/WaveGameTitle.png")
;	    //image = new ImageIcon("images/background2.gif").getImage();
	    
	    //removed fireworks
		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
		colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}

	

	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
		colorPick.add(Color.pink);
	}

	public void tick() {
		timer--;
		fishPos = fishPos + 20;
		if (fishPos >= 1100) {
			fishPos = -600;
			if (fishPosY >= 40) {
			fishPosY = fishPosY/2;
			} else {
				fishPosY = 300;
			}
			}
		
		if (timer <= 0) {
			handler.object.clear();
			//colorIndex = r.nextInt(6);
			//Removed fireworks
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
			colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		g.drawImage(fish, fishPos ,fishPosY , 162, 108, null);
		g.drawImage(shell, 100 , 70 , 300, 200, null);
		g.drawImage(title, 150 , 50 , 776, 265, null);
		
		
		//get size of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		
		if (game.gameState == STATE.Menu) {
			
			handler.render(g);
			
			Font font = new Font("Roboto", 1, 35);
			Font font2 = new Font("Roboto", 1, 30);
			Color color1 = new Color(255, 255, 225);
			Color color2 = new Color(0, 133, 180);

			//Color color3 = new Color(0, 173, 209);



		//	Color color3 = new Color(0, 173, 209);	
			
			//Wave Game Image
		//	g.drawImage(image, 190, 100, 720, 100, null);
			
			//Game Modes

			g.setFont(font);
			g.setFont(new Font("Arial", Font.PLAIN, (int)width/50));
			g.setColor(color1);
			g.drawString("GAME MODES", 680, 250);

			// Waves Button
			g.setColor(color1);
			g.drawRect(420, 270, 720, 50);
			
			g.setFont(font2);
			g.setColor(color1);
			g.setColor(color1);
			g.drawString("WAVES",725, 307);

			// Coop Button
			//g.setColor(color1);
			//g.drawRect(400, 330, 960, 50);
			//g.setFont(font2);
			//g.setColor(color1);
			//g.drawString("CO-OP", 490, 365);

// Server Defense Button
			//g.setColor(color1);
			//g.drawRect(400, 390, 720, 50); //left margin, top margin, width, height 
			//g.setFont(font2);
			//.setColor(color1);
			//g.drawString("SERVER DEFENSE",  490, 365);

			// Attack Button
			g.setColor(color1);
			g.drawRect(420, 330, 720, 50);
			g.setFont(font2);
			g.setColor(color1);
			g.drawString("ATTACK",  720, 365);

			// Help Button
						g.setColor(color1);
						g.drawRect(420, 550, 200, 80);
						g.setFont(font);
						g.setColor(color1);
						g.drawString("HELP", 475, 605);

						// Credits Button
						g.setColor(color1);
						g.drawRect(680, 550, 200, 80);
						g.setFont(font);
						g.setColor(color1);
						g.drawString("CREDITS", 705, 605);

						// Quit Button
						g.setColor(color1);
						g.drawRect(935, 550, 200, 80);
						g.setFont(font);
						g.setColor(color1);
						g.drawString("QUIT", 990, 605);

		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
			Font font = new Font("Roboto", 1, 20);
			Font font2 = new Font("Roboto", 1, 15);
			Font font3 = new Font("Roboto", 1, 90);
			
			
			g.setFont(font3);
			g.setColor(Color.black);
			g.drawString("HOW TO PLAY", 260, 120);

			g.setFont(font);
			g.setColor(Color.black);
			g.drawString("Instructions:", 40, 190);// + " \n"
			
			g.setFont((font));
			g.setColor(Color.white);
			g.drawString("Use WASD or the arrow keys to move around and avoid enemies and succeed through the levels.", 160, 190);
			
			g.setFont((font2));
			g.setColor(Color.white);
			g.drawString("The game consists of four game modes to choose from:" , 40, 245);
				
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Waves:", 90, 290);
					
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Avoid the enemies that appear. After every 10 levels,", 153, 290);
			g.drawString("a boss level will appear. Beat all the bosses to ", 153, 310);
			g.drawString("win the game.", 153, 330);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("CO-OP:", 90, 375);
			g.setFont(font2);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Player 1 moves by WASD keys and Player 2 moves by", 153, 375);
			g.drawString("arrow keys.The first player to collect 20 treats wins.", 153, 395);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Coral Reef Defense:", 90, 450);
			g.setFont(font2);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Defend the Coral Reef from incoming enemies!", 242, 450);
			g.drawString("As you get closer to the enemies, they will be", 242, 470);
			g.drawString("pulled away from the reef.", 242, 490);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Attack:", 90, 525);
			g.setFont(font2);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Shoot back at the enemies using the mouse click.", 152, 525);
			g.drawString("Reload with ‘R’ or ‘Enter’ keys.", 155, 545);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("________________________________________________________________________________________________________________________________________________", 40, 575);
			
			g.setFont((font2));
			g.setColor(Color.white);
			g.drawString("The game contains the following pick ups:" , 560, 245);
			
			g.setFont((font2));
			g.setColor(Color.black);
			g.drawString("HELP PLAYER" , 560, 290);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Seaweed:", 610, 320);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Increases player’s health", 688, 320);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Pearl:", 610, 350);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Increases the player’s speed for the rest of that level", 665, 350);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Shell:", 610, 380);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Gives the player an extra life", 661, 380);
			
			g.setFont((font2));
			g.setColor(Color.black);
      
			g.drawString("HURT PLAYER", 560, 425);

			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Shark:", 610, 455);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Decreases player’s health", 665, 455);
			
			g.setFont(font2);
			g.setColor(Color.cyan);
			g.drawString("Stingray:", 610, 485);
			
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Decreases the player’s speed for the rest", 683, 485);
		
		
			g.setColor(Color.white);
			g.drawRect(500, 600, 120, 40);
			g.drawString("Back", 543, 625);
		}

	}

}