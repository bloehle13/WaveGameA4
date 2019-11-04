package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
/**
 * 
 * @author Kyle Horton
 * 
 * Coin pickup for Seal Boss.
 *
 */
public class CoinPickup extends Pickup {

	private Handler handler;
	private int timer;

	public CoinPickup(ID id, Handler handler) {
		super((Game.WIDTH - 40) * Math.random(), -40, id);

		this.handler = handler;
		this.timer = 10;
		velX = 0;
		velY = 5;
		img = getImage("images/CoinPickup.png");

	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;

	}

	@Override
	public void tick() {
		this.y += velY;
		this.x += velX;

	}

	@Override
	public void render(Graphics g) {
		
			g.drawImage(img, (int) this.x, (int) this.y, 40, 40, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 40, 40);
	}
}
