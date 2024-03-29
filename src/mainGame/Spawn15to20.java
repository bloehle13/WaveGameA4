package mainGame;

import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;


public class Spawn15to20 {
	private Handler handler;
	private HUD hud;
	private Game game;
	private CoopHud hud2; 
	private int scoreKeep = 0;
	private Random r = new Random();
	private int spawnTimer;
	private int levelTimer;
	private int voteTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int index;
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	private int timer = 0;
	private int bossCount;
	
	public Spawn15to20(Handler handler, HUD hud, CoopHud hud2, Spawn10to15 spawner2, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.hud2 = hud2;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud2.setScore(0);
		hud.setLevel(1);
		spawnTimer = 10;
		voteTimer = 20;
		levelTimer = 150;
		levelsRemaining = 5;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;
		timer = 120;
		bossCount = 0;

	}
	// Pre loads every level
	public void addLevels() {
		for (int i = 1; i <= 10; i++) {
			levels.add(i);
		}
	}
	//Called once every 60 seconds by the Game Loop
	
	public void tick() {
		if (levelNumber <= 0) {
			levelTimer--;
			if (tempCounter < 1) {// display intro game message ONE time
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Last Section . . . Good Luck",
						ID.Levels1to5Text));
				tempCounter++;
			}
			if (levelTimer <= 0) {// time to play!
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = levels.get(index);
			}

		}
		
		/*
		 * 
		* Each level works the same way
		* 
		* To keep comments on code to a minimum only the first level will be fully commented
		*
		*/
		else if (levelNumber == 1) {
			spawnTimer--;
			levelTimer--;
			voteTimer--;
			
			if (tempCounter < 1) {// called only once, but sets the levelTimer to how long we want this level to run for
				levelTimer = 2000;// 2000 / 60 method calls a second = 33.33 seconds long
				tempCounter++;// ensures the method is only called once
			}
			
			if (spawnTimer == 40) {
				handler.addObject(
						new EnemyCombination(1500, 900, -3, -10, ID.EnemyCombination, handler));
	
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemyCombination(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -3, -10, ID.EnemyCombination, handler));
				spawnTimer = 100;
			}
			
			if (levelTimer == 0) {// level is over
				handler.clearEnemies();// clear the enemies
				hud.setLevel(hud.getLevel() + 1);// Increment level number on HUD
				spawnTimer = 40;
				tempCounter = 0;// reset tempCounter
				if (levelsRemaining == 1) {// time for the boss!
					levelNumber = 101;// arbitrary number for the boss level
				} else {// not time for the boss, just go to the next level
					levels.remove(index);// remove the current level from being selected
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);// pick another level at random
					levelNumber = levels.get(index);// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 2) {
				spawnTimer--;
				levelTimer--;
				voteTimer--;
				if (tempCounter < 1) {
					levelTimer = 2000;
					tempCounter++;
				}
				
				if (spawnTimer == 30) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 30, 2, ID.EnemySweep, handler));
				} else if (spawnTimer == 20) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 30, -2, ID.EnemySweep, handler));
					
				} else if (spawnTimer == 10) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 30, 4, ID.EnemySweep, handler));
				} else if (spawnTimer == 0) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 30, -4, ID.EnemySweep, handler));
					spawnTimer = 80;
				}

				if (levelTimer == 0) {
					handler.clearEnemies();
					hud.setLevel(hud.getLevel() + 1);
					tempCounter = 0;
					if (levelsRemaining == 1) {
						levelNumber = 101;
					} else {
						levels.remove(index);
						levelsRemaining--;
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
					}
				}
			} else if (levelNumber == 3) {

				spawnTimer--;
				levelTimer--;
				voteTimer--;
				if (tempCounter < 1) {
					levelTimer = 1500;
					tempCounter++;
					
					handler.addPickup(new DecreaseSpeedPickup(ID.DecreaseSpeedPickup, handler));
					handler.addPickup(new IncreaseHealthPickup(ID.IncreaseHealthPickup, handler));
				}
				if (spawnTimer == 0) {
					handler.addObject(new EnemyDash(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -9, ID.EnemySmart, handler));
					spawnTimer = 100;
				}
				if (levelTimer == 0) {
					handler.clearEnemies();
					hud.setLevel(hud.getLevel() + 1);
					spawnTimer = 10;
					tempCounter = 0;
					if (levelsRemaining == 1) {
						levelNumber = 101;
					} else {
						levels.remove(index);
						levelsRemaining--;
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
					}
				}
			} else if (levelNumber == 4) {
				levelTimer--;
				voteTimer--;
				if (tempCounter < 1) {
					handler.addObject(new EnemyShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100,
							-20, ID.EnemyShooter, this.handler));

					if (hud.health <= 50){
						handler.addPickup(new IncreaseHealthPickup(ID.IncreaseHealthPickup, handler));
					} else {
						handler.addPickup(new DecreaseHealthPickup(ID.DecreaseHealthPickup, handler));
					}

					levelTimer = 1300;
					tempCounter++;
				}

				if (levelTimer == 0) {
					handler.clearEnemies();
					handler.pickups.clear();
					hud.setLevel(hud.getLevel() + 1);
					spawnTimer = 10;
					tempCounter = 0;
					if (levelsRemaining == 1) {
						levelNumber = 101;
					} else {
						levels.remove(index);
						levelsRemaining--;
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
					}
				}
			} else if (levelNumber == 5) {
				spawnTimer--;
				levelTimer--;
				voteTimer--;
				if (tempCounter < 1) {
					levelTimer = 1400;
					tempCounter++;
				}
				
				if (spawnTimer <= 0) {
					handler.addObject(new EnemyBurst(-200, 200, 40, 40, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
					spawnTimer = 180;
				}

				if (levelTimer == 0) {
					handler.clearEnemies();
					hud.setLevel(hud.getLevel() + 1);
					spawnTimer = 10;
					tempCounter = 0;
					if (levelsRemaining == 1) {
						levelNumber = 101;
					} else {
						levels.remove(index);
						levelsRemaining--;
						index = r.nextInt(levelsRemaining);
						levelNumber = levels.get(index);
					}
				}
			}
		
			else if (levelNumber == 101) {
				
				
				levelTimer--;
				if (tempCounter < 1) {
					levelTimer = 2000;
				tempCounter++;
				
				if (hud.health <= 60 && hud.health > 30) {
					handler.addPickup(new IncreaseHealthPickup(ID.IncreaseHealthPickup, handler));
				}
				if (hud.health <= 30 && hud.health > 20) {
				}
				if (hud.health <= 20) {
					handler.addPickup(new IncreaseSpeedPickup(ID.IncreaseSpeedPickup, handler));
				}

					handler.addObject(new BossEye(Game.WIDTH - 150, Game.HEIGHT - 200, ID.BossEye, handler, 1));
					handler.addObject(new BossEye(Game.WIDTH / 2 - 50, Game.HEIGHT - 200, ID.BossEye, handler, 2));
					handler.addObject(new BossEye(50, Game.HEIGHT - 200, ID.BossEye, handler, 3));
					handler.addObject(new BossEye(Game.WIDTH - 150, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 4));
					handler.addObject(new BossEye(Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 5));
					handler.addObject(new BossEye(50, Game.HEIGHT / 2 - 120, ID.BossEye, handler, 6));
					handler.addObject(new BossEye(Game.WIDTH - 150, 50, ID.BossEye, handler, 7));
					handler.addObject(new BossEye(Game.WIDTH / 2 - 50, 50, ID.BossEye, handler, 8));
					handler.addObject(new BossEye(50, 50, ID.BossEye, handler, 9));
					hud.setBossLevel("Boss Four");
					hud.setBoss(true);
				}

				if (levelTimer == 0) {
					bossCount++;
					GameObject tempObject = handler.object.get(1);
					if (tempObject.getId() == ID.BossEye) {
						handler.pickups.clear();
						handler.removeObject(tempObject);
						levelTimer += 200;
					}
				}
				
				if (bossCount == 9){
					game.gameState = STATE.Victory;
				}
			}
	}
	public void skipLevel() {
		if (levelsRemaining == 1) {
			handler.pickups.clear();
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsRemaining > 1) {
			handler.pickups.clear();
			Player.playerSpeed = 10;
			levels.remove(index);
			levelsRemaining--;
			System.out.println(levelsRemaining);
			tempCounter = 0;
			index = r.nextInt(levelsRemaining);
			levelNumber = levels.get(index);
		}
	}

	public void restart() {
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 10;
		index = r.nextInt(levelsRemaining);

	}
}
