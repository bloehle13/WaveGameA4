package mainGame;

import java.awt.Color;

import java.lang.Math; 

import java.awt.Graphics;
import java.awt.Rectangle;

public class HealthPowerUp extends Pickup {

	private Handler handler;

	//private int timer;
	private int sizeX;
	private int sizeY;
	private int identity; 
	private Color color; 
	
	public void setColor() {
		if( identity < 15) {
			color = Color.green;
		}
		else if( identity < 25) {
			color = Color.blue;
		}
		else {
			color = Color.pink;
		}
	}
	
	public HealthPowerUp(ID id, Handler handler) {
		super((Game.WIDTH - 70)*Math.random(), (Game.HEIGHT - 120)*Math.random(), id);
		this.handler = handler;

		//this.timer = 60;
		this.sizeX = 50;
		this.sizeY = 50;
		
		this.identity = (int)(Math.random()* 100); 
		this.setColor();

	}

	@Override
	public void tick() {
		

		/*if(timer > 0) {
			this.updateSize();
	
			timer --;
		}*/
	}
		
	//}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.drawImage(img, (int) this.x, (int) this.y, 50, 50, null);
		g.fillRect((int) this.x, (int) this.y, 50, 50);
		
		

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 50, 50);
	}
	
	public void updateSize() {
		this.sizeX--;
		this.sizeY--;

		if (sizeX <= 0) {
			handler.removePickup(this);
		}
	}

}