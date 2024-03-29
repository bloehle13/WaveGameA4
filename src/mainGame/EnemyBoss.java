package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

/**
 * The first boss in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBoss extends GameObject {

	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private Image img;
	private int spawn;

	public EnemyBoss(ID id, Handler handler) {
		super(Game.WIDTH / 2 - 48, -120, id);
		this.handler = handler;
		velX = 0;
		velY = 2;
		img = getImage("images/JellyFish.png");
		this.health = 1000;//full health is 1000
	}

	 public void tick() {
		this.x += velX;
		this.y += velY;

		if (timer <= 0)
			velY = 0;
		else
			timer--;
		drawFirstBullet();
		if (timer <= 0)
			timer2--;
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			this.isMoving = true;
			spawn = r.nextInt(5);
			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y, ID.EnemyBossBullet, handler));
				this.health -= 3;
			}
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 120)
			velX *= -1;

	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.drawImage(img, (int) this.x, (int) this.y, 120, 120, null);

		
		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 3 - 250,  700, 1000, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 3 - 250,  700, this.health, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 3 - 250,  100, 1000, 50);

		
//	 Health Bar Before
		//.setColor(Color.GRAY);
		//g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		//g.setColor(Color.RED);
		//g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health, 50);
		//g.setColor(Color.WHITE);
		//g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);


	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 120, 120);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler));
	}
	
	

}
