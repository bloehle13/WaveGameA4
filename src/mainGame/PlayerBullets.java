package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 
 * @author Kyle Horton
 *
 *This class creates the bullets the player shoots in Attack.
 */

public class PlayerBullets extends GameObject{
	
	private Handler handler;
	private AttackHUD attackHUD;

	public PlayerBullets(double x, double y, double velX, double velY, AttackHUD attackHUD, ID id, Handler handler) {
		super(x + 20, y + 30, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.attackHUD = attackHUD;
	}

	@Override
	public void tick() {
		
		this.x += velX;
		this.y += velY;
		clearBullets();
		collision();

		
	}
	
	// removes the bullets from screen
	public void clearBullets() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.PlayerBullets) {
				if (tempObject.getX() >= Game.WIDTH || tempObject.getY() >= Game.HEIGHT) {
					handler.removeObject(tempObject);
				}
			}

		}

	}
	
	// checks if bullets hit enemies
	public void collision() {
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.EnemySmart ) {// tempObject is an
															// enemy

				// collision code

				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy with bullets
					attackHUD.setScore(attackHUD.getScore() + 10);
					handler.removeObject(tempObject);
					handler.removeObject(this);
					Thread thread = new Thread(new Sound(), "shoot");
					thread.start();
					}
				}
			
			if (tempObject.getId() == ID.SmartBoss){
				if (getBounds().intersects(tempObject.getBounds())){
					tempObject.setHealth(tempObject.getHealth() - 10);
					handler.removeObject(this);
				}
			}

			}
		
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, 4, 4);
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}
}
