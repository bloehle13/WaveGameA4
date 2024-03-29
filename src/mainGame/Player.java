package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * The main player in the game
 *
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {

	private Thread healthThread;
	Random r = new Random();
	Handler handler;
	private HUD hud;
	private CoopHud hud2;
	private AttackHUD attackHUD;
	private ServerHUD serverHUD;
	private Game game;
	private int damage;
	private Image img;
	private Image playerDamaged;
	private KeyInput key;
	private int playerWidth, playerHeight;
	public static int playerSpeed = 10;
	private int timer;
	public int voteCount;  ///***CHANGE 
	private boolean shooting;
	private double bulletX;
	private double bulletY;
	private int bulletSpeed;
	public boolean takingDamage;
	private int takingDamageTimer;
	public Player(double x, double y, ID id, Handler handler, HUD hud, CoopHud hud2, AttackHUD attackHUD, ServerHUD serverHUD, Game game) {

		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 1;
		this.hud2 = hud2;
		this.attackHUD = attackHUD;
		this.serverHUD = serverHUD;
		bulletX = 0;
		bulletY = 0;
		shooting = true;
		timer = 60;
		voteCount = 0;

		playerDamaged = getImage("images/PlayerDamaged.png");

		

		if (this.id == ID.Player){
	
			img = getImage("images/PurpleFish.png");
		}
		takingDamageTimer = 0;

	}

	@Override
	public void tick() {
		if (takingDamage == true) {
			Thread thread = new Thread(new Sound(), "PlayerDamage");
			thread.start();

		}
	
		if(takingDamageTimer > 0) {
			takingDamageTimer--;
		}
		
		
		this.x += velX;
		this.y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 95);
		y = Game.clamp(y, 0, Game.HEIGHT - 125);

		collision();

		if (timer == 0) {
			timer = 60;
			playerSpeed = 10;
		}
		if (game.gameState == STATE.Game)
			checkIfDead();
		if (game.gameState == STATE.Attack) {
			checkIfDeadAttack();

			if (shooting == true) {
				handler.addObject(new PlayerBullets(this.x, this.y, bulletX, bulletY, this.attackHUD, ID.PlayerBullets,
						this.handler));
				attackHUD.setAmmo(attackHUD.getAmmo() - 1);
			}

			if (attackHUD.getAmmo() <= 0) {
				setShooting(false);
			}
		}
	}

	public void checkIfDead() {
		if (hud.health <= 0) {// player is dead, game over!
			if (hud.getExtraLives() == 0) {
				Thread thread = new Thread(new Sound(), "DeathNoise");
				thread.start();
				game.renderGameOver();
				game.getGameOver().setWhoDied(0);
				game.gameState = STATE.GameOver;
			}

			else if (hud.getExtraLives() > 0) {// has an extra life, game
												// continues

				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.restoreHealth();
			}
		}
	}

	public void checkIfDeadAttack() {
		if (attackHUD.health <= 0) {// player is dead, game over!
			game.renderGameOver();
			game.gameState = STATE.GameOver;
		}
	}

	public void checkIfDeadCoop() {
		int finalVote = 20;
		if ((hud.health <= 0 || hud2.health <= 0) || (hud.getVote() == finalVote || hud2.getVote() == finalVote)) {// player
																													// is
																													// dead,
																													// game
																													// over!
			if (hud.health <= 0 || hud.getVote() == finalVote) {
				if (hud.getExtraLives() == 0 && hud.health <= 0) {
					game.getGameOver().setWhoDied(1);
					game.gameState = STATE.GameOver;
				}
				if (hud.getVote() >= finalVote) {
					game.getGameOver().setWinner(1);
					game.gameState = STATE.GameOver;
				}

			}
			if (hud2.health <= 0 || hud2.getVote() == finalVote) {
				if (hud2.getExtraLives() == 0 && hud2.health <= 0) {
					game.getGameOver().setWhoDied(2);
					game.gameState = STATE.GameOver;
				}
				if (hud2.getVote() >= finalVote) {
					game.getGameOver().setWinner(2);
					game.gameState = STATE.GameOver;
				}
			}

			if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
			if (hud2.getExtraLives() > 0) {
				hud2.setExtraLives(hud2.getExtraLives() - 1);
				hud2.setHealth(100);
			}
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {

		hud.updateScoreColor(Color.white);
		hud2.updateScoreColor(Color.white);
		attackHUD.updateScoreColor(Color.white);

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
					/////**************CHR CHANGE ********
			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart || tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye || tempObject.getId() == ID.CoinPickup
					|| tempObject.getId() == ID.EnemyCombination || tempObject.getId() == ID.SmartBoss || tempObject.getId() == ID.SquidBoss) {// tempObject
																									// is
																									// an
																									// enemy

				// collision code

				if (getBounds().intersects(tempObject.getBounds())) {// player
																		// hit
																		// an
																		// enemy
					if (this.id == ID.Player) {
						System.out.println("Colliding With Player");
						takingDamage = true;
						takingDamageTimer = 10; //change amount here
						hud.health -= damage;
						hud.updateScoreColor(Color.red);
						attackHUD.health -= 0.5;
					} else {
						hud2.health -= damage;
						hud2.updateScoreColor(Color.red);
					}

				} 

			}  

			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will
				// get hurt once the boss starts moving
				if (this.y <= 138 && tempObject.isMoving) {
					if (this.id == ID.Player) {
						hud.health -= 2;
						hud.updateScoreColor(Color.red);
						attackHUD.health -= 2;
						attackHUD.updateScoreColor(Color.red);
					} else {
						hud2.health -= 2;
						hud2.updateScoreColor(Color.red);
					}
				}
			}

		}

		// for loop that checks to see if player runs into pickup
		// if player does, affect player, remove item from array
		for (int i = 0; i < handler.pickups.size(); i++) {
			Pickup tempObject = handler.pickups.get(i);
			if (tempObject.getId() == ID.IncreaseHealthPickup) {
				if (getBounds().intersects(tempObject.getBounds())) {

					if (hud.health >= 60) {
						hud.setHealth(100);
					} else {
						hud.setHealth(hud.health + 40);
					}
					handler.removePickup(tempObject);

					// Plays sound effect on different thread
					// Each sound effect is the same except for which string is
					// called
													////*********CHAR CHANGE
					Thread thread = new Thread(new Sound(), "IncreaseHealthPickupNoise");
					thread.start();

				}

			}                /// *****NEED TO CHANGE FOR CHAR ------
			if (tempObject.getId() == ID.DecreaseHealthPickup) {
				if (getBounds().intersects(tempObject.getBounds())) {

					if (hud.health <= 40) {
						hud.setHealth(10);
					} else {
						hud.setHealth(hud.health - 20);

					}
					handler.removePickup(tempObject);
															/// *****CHANGE 
					Thread thread = new Thread(new Sound(), "DecreaseHealthPickupNoise");
					thread.start();

				}
			}						/// *****CHANGE 
			if (tempObject.getId() == ID.IncreaseSpeedPickup) {
				if (getBounds().intersects(tempObject.getBounds())) {
					playerSpeed = 20;
					handler.removePickup(tempObject);
					Thread thread = new Thread(new Sound(), "IncreaseSpeedPickupNoise");
					thread.start();
				}
			}		
	 
									/// *****CHANGE 
			if (tempObject.getId() == ID.DecreaseSpeedPickup) {
				if (getBounds().intersects(tempObject.getBounds())) {
					playerSpeed = 5;
					handler.removePickup(tempObject);
					Thread thread = new Thread(new Sound(), "NFLSound");
					thread.start();

				}
			}
									/// *****CHANGE 
			if (tempObject.getId() == ID.CoinPickup) {
				if (getBounds().intersects(tempObject.getBounds())) {
					
					hud.setHillaryX(hud.getHillaryX() - 2);
					hud.setHillaryY(hud.getHillaryY() - 2);

					handler.removePickup(tempObject);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.white);
		if (takingDamage == false && takingDamageTimer == 0) {
			g.drawImage(img, (int) this.x, (int) this.y, 75, 85, null);
		
		} else{
			g.drawImage(playerDamaged, (int) this.x, (int) this.y, 75, 85, null);
			takingDamage = false;
	
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 75, 85);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
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

	public void setCount() {
		voteCount++;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	public boolean getShooting() {
		return shooting;
	}

	public void setBulletX(double bulletX) {
		this.bulletX = bulletX;
	}

	public void setBulletY(double bulletY) {
		this.bulletY = bulletY;
	}

}