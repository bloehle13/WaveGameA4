
package mainGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import mainGame.Game.STATE;

/**
 * Handles key input from the user
 * 
 * @author Brandon Loehle 5/30/16
 * 
 *Edited: Brian Carballo, Kyle DeGennaro, Lauren Heery, 
Alexandra Martin, and Christina Popik

 */

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[5];
	private boolean[] keyDown2 = new boolean[5];
	private int speed;
	private Game game;
	private HUD hud;
	private AttackHUD attackHUD;
	private Player player, player2;
	private Spawn1to5 spawner;
	private Upgrades upgrades;
	private String ability = "";
	private PlayerBullets shooting;
	private GameObject playerObject;

	// uses current handler created in Game as parameter
	public KeyInput(Handler handler, Game game, HUD hud, AttackHUD attackHUD, Player player, Player player2,
			Spawn1to5 spawner, Upgrades upgrades) {
		this.handler = handler;
		this.speed = Player.playerSpeed;
		this.game = game;
		this.player = player;
		this.player2 = player2;
		this.hud = hud;
		this.attackHUD = attackHUD;
		this.spawner = spawner;
		this.upgrades = upgrades;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = false;
		keyDown2[0] = false;
		keyDown2[1] = false;
		keyDown2[2] = false;
		keyDown2[3] = false;
		keyDown2[4] = false;
		

						
	// if coop then assign the keys to different values								
	// as he is the only one
	// the user can control
					
		}

	
// making movement own function to called instead of run in each 
	public void movement(KeyEvent e) {
		int key = e.getKeyCode();
		this.speed = Player.playerSpeed;
		
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
					// using only if's allows multiple keys to be triggered at once
			if(game.gameState == STATE.Defense){
				if (tempObject.getId() == ID.Player2) {// find the player object,
					playerObject = tempObject;
				}
			}		
			else if (tempObject.getId() == ID.Player) {// find the player object,
						playerObject = tempObject;
					}
				
		// key events for player 1
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			playerObject.setVelY(-(this.speed));
			keyDown[0] = true;
			
		}
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			playerObject.setVelX(-(this.speed));
			keyDown[1] = true;
		}
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			playerObject.setVelY(this.speed);
			keyDown[2] = true;
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			playerObject.setVelX(this.speed);
			keyDown[3] = true;
		}
		if (key == KeyEvent.VK_SPACE) {
			upgrades.levelSkipAbility();
		}
		
		if (key == KeyEvent.VK_ENTER && attackHUD.getAmmo() == 0) {
			attackHUD.setAmmo(360);
		}
		
		}
		//adding pause menu
		if (key == KeyEvent.VK_ESCAPE) {
			if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
				if(game.isPaused() == true){
					game.unPause();
				}
					else {
						game.pause();
						
					}
				}
			}
			

		}		
					
		

	
	// movement method of the character movement 
	public void moveCoop(KeyEvent e) {
	
			int key = e.getKeyCode();
			this.speed = Player.playerSpeed;
			
			
			for (int i = 0; i < handler.object.size(); i++) {
				
				GameObject tempObject = handler.object.get(i);
						// using only if's allows multiple keys to be triggered at once
		
		//finds what key strokes associate with Player
		
		if (game.gameState == STATE.Coop) {
			// find the player object, as he is the only one the user can control 
			if (playerObject.getId() == ID.Player) {
				// key events for player 1
				if (key == KeyEvent.VK_W) {
					playerObject.setVelY(-(this.speed));
					keyDown[0] = true;
				}
				if (key == KeyEvent.VK_A) {
					playerObject.setVelX(-(this.speed));
					keyDown[1] = true;
				}
				if (key == KeyEvent.VK_S) {
					playerObject.setVelY(this.speed);
					keyDown[2] = true;
				}
				if (key == KeyEvent.VK_D) {
					playerObject.setVelX(this.speed);
					keyDown[3] = true;
					keyDown[4] = true;

				}
				if (key == KeyEvent.VK_SPACE) {
					upgrades.levelSkipAbility();
				}
			}

			// temp object tracks the keys for player 2 differently, alters
			// the keydown2 array separately
			if (playerObject.getId() == ID.Player2) {
				if (key == KeyEvent.VK_UP) {
					playerObject.setVelY(-(this.speed));
					keyDown2[0] = true;
				}
				if (key == KeyEvent.VK_LEFT) {
					playerObject.setVelX(-(this.speed));
					keyDown2[1] = true;
				}
				if (key == KeyEvent.VK_DOWN) {
					playerObject.setVelY(this.speed);
					keyDown2[2] = true;
				}
				if (key == KeyEvent.VK_RIGHT) {
					playerObject.setVelX(this.speed);
					keyDown2[3] = true;
					keyDown2[4] = true;

				}
			}
			 	

		}

	}
	
}
		
	// finds what key strokes associate with Player
	public void keyPressed(KeyEvent e) {
		// finds for the which game mode 
		if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
			movement(e); //
		if(game.gameState == STATE.Coop) {
			moveCoop(e);
			}
		}
	}	
	

		//  released key method  
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject playerObject = handler.object.get(i);
			if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
				if (playerObject.getId() == ID.Player || playerObject.getId() == ID.Player2) {
					// key events for player 1
					if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
						keyDown[0] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
						keyDown[1] = false;// playerObject.setVelX(0);
					if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
						keyDown[2] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
						keyDown[3] = false;// playerObject.setVelX(0);
						keyDown[4] = false;
					}

					// vertical movement
					if (!keyDown[0] && !keyDown[2])
						playerObject.setVelY(0);
					// horizontal movement
					if (!keyDown[1] && !keyDown[3])
						playerObject.setVelX(0);
				}

			}
		}
	}
	}
			/* if (game.gameState == STATE.Attack) {
				if (playerObject.getId() == ID.Player) {
					keyReleased(KeyEvent e)


			// changed the function of key inputs for coop
			// here the velocity is set independently of the playerObject used/
			// keys used
				if (game.gameState == STATE.Coop) {
				if (playerObject.getId() == ID.Player) {
					// key events for player 1
					if (key == KeyEvent.VK_W)
						keyDown[0] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_A)
						keyDown[1] = false;// playerObject.setVelX(0);
					if (key == KeyEvent.VK_S)
						keyDown[2] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_D) {
						keyDown[3] = false;// playerObject.setVelX(0);
						keyDown[4] = false;
					}

					// vertical movement
					if (!keyDown[0] && !keyDown[2])
						playerObject.setVelY(0);
					// horizontal movement
					if (!keyDown[1] && !keyDown[3])
						playerObject.setVelX(0);
				}

				if (playerObject.getId() == ID.Player2) {
					// key events for player 2
					if (key == KeyEvent.VK_UP)
						keyDown2[0] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_LEFT)
						keyDown2[1] = false;// playerObject.setVelX(0);
					if (key == KeyEvent.VK_DOWN)
						keyDown2[2] = false;// playerObject.setVelY(0);
					if (key == KeyEvent.VK_RIGHT) {
						keyDown2[3] = false;// playerObject.setVelX(0);
						keyDown2[4] = false;
					}

					// vertical movement
					if (!keyDown2[0] && !keyDown2[2])
						playerObject.setVelY(0);
					// horizontal movement
					if (!keyDown2[1] && !keyDown2[3])
						playerObject.setVelX(0);

				}
			}
		}
		}
	}
//=======
//package mainGame;
//
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.net.URL;
//import java.util.ArrayList;
//
//import javax.swing.ImageIcon;
//
//import mainGame.Game.STATE;
//
///**
// * Handles key input from the user
// * 
// * @author Brandon Loehle 5/30/16
// * 
// *Edited: Brian Carballo, Kyle DeGennaro, Lauren Heery, 
//Alexandra Martin, and Christina Popik
//
// */
//
//public class KeyInput extends KeyAdapter {
//
//	private Handler handler;
//	private boolean[] keyDown = new boolean[5];
//	private boolean[] keyDown2 = new boolean[5];
//	private int speed;
//	private Game game;
//	private HUD hud;
//	private AttackHUD attackHUD;
//	private Player player, player2;
//	private Spawn1to5 spawner;
//	private Upgrades upgrades;
//	private String ability = "";
//	private PlayerBullets shooting;
//	private GameObject playerObject;
//	public Image image;
//
//	// uses current handler created in Game as parameter
//	public KeyInput(Handler handler, Game game, HUD hud, AttackHUD attackHUD, Player player, Player player2,
//			Spawn1to5 spawner, Upgrades upgrades) {
//		this.handler = handler;
//		this.speed = Player.playerSpeed;
//		this.game = game;
//		this.player = player;
//		this.player2 = player2;
//		this.hud = hud;
//		this.attackHUD = attackHUD;
//		this.spawner = spawner;
//		this.upgrades = upgrades;
//		keyDown[0] = false;
//		keyDown[1] = false;
//		keyDown[2] = false;
//		keyDown[3] = false;
//		keyDown[4] = false;
//		keyDown2[0] = false;
//		keyDown2[1] = false;
//		keyDown2[2] = false;
//		keyDown2[3] = false;
//		keyDown2[4] = false;
//		
//
//						
//	// if coop then assign the keys to different values								
//	// as he is the only one
//	// the user can control
//					
//		}
//
//	
//// making movement own function to called instead of run in each 
//	public void movement(KeyEvent e) {
//		int key = e.getKeyCode();
//		this.speed = Player.playerSpeed;
//		
//		for (int i = 0; i < handler.object.size(); i++) {
//			GameObject tempObject = handler.object.get(i);
//					// using only if's allows multiple keys to be triggered at once
//			if(game.gameState == STATE.Defense){
//				if (tempObject.getId() == ID.Player2) {// find the player object,
//					playerObject = tempObject;
//				}
//			}		
//			else if (tempObject.getId() == ID.Player) {// find the player object,
//						playerObject = tempObject;
//					}
//				
//		// key events for player 1
//		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
//			playerObject.setVelY(-(this.speed));
//			keyDown[0] = true;
//		}
//		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
//			playerObject.setVelX(-(this.speed));
//			keyDown[1] = true;
//		}
//		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
//			playerObject.setVelY(this.speed);
//			keyDown[2] = true;
//		}
//		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
//			playerObject.setVelX(this.speed);
//			keyDown[3] = true;
//		}
//		if (key == KeyEvent.VK_SPACE) {
//			upgrades.levelSkipAbility();
//		}
//		
//		if (key == KeyEvent.VK_ENTER && attackHUD.getAmmo() == 0) {
//			attackHUD.setAmmo(360);
//		}
//		
//		}
//		//adding pause menu
//		if (key == KeyEvent.VK_ESCAPE) {
//			if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
//				if(game.isPaused() == true){
//					game.unPause();
//			
//				}
//					else {
//						game.pause();
//						PauseMenu pauseMenu = new PauseMenu(handler); 
//					}
//				}
//			}
//			
//
//		}		
//					
//		}
//	
//	// movement method of the character movement 
//	public void moveCoop(KeyEvent e) {
//	
//			int key = e.getKeyCode();
//			this.speed = Player.playerSpeed;
//			
//			
//			for (int i = 0; i < handler.object.size(); i++) {
//				
//				GameObject tempObject = handler.object.get(i);
//						// using only if's allows multiple keys to be triggered at once
//		
//		//finds what key strokes associate with Player
//		
//		if (game.gameState == STATE.Coop) {
//			// find the player object, as he is the only one the user can control 
//			if (playerObject.getId() == ID.Player) {
//				// key events for player 1
//				if (key == KeyEvent.VK_W) {
//					playerObject.setVelY(-(this.speed));
//					keyDown[0] = true;
//				}
//				if (key == KeyEvent.VK_A) {
//					playerObject.setVelX(-(this.speed));
//					keyDown[1] = true;
//				}
//				if (key == KeyEvent.VK_S) {
//					playerObject.setVelY(this.speed);
//					keyDown[2] = true;
//				}
//				if (key == KeyEvent.VK_D) {
//					playerObject.setVelX(this.speed);
//					keyDown[3] = true;
//					keyDown[4] = true;
//
//				}
//				if (key == KeyEvent.VK_SPACE) {
//					upgrades.levelSkipAbility();
//				}
//			}
//
//			// temp object tracks the keys for player 2 differently, alters
//			// the keydown2 array separately
//			if (playerObject.getId() == ID.Player2) {
//				if (key == KeyEvent.VK_UP) {
//					playerObject.setVelY(-(this.speed));
//					keyDown2[0] = true;
//				}
//				if (key == KeyEvent.VK_LEFT) {
//					playerObject.setVelX(-(this.speed));
//					keyDown2[1] = true;
//				}
//				if (key == KeyEvent.VK_DOWN) {
//					playerObject.setVelY(this.speed);
//					keyDown2[2] = true;
//				}
//				if (key == KeyEvent.VK_RIGHT) {
//					playerObject.setVelX(this.speed);
//					keyDown2[3] = true;
//					keyDown2[4] = true;
//
//				}
//			}
//			 	
//
//		}
//
//	}
//	
//}
//		
//	// finds what key strokes associate with Player
//	public void keyPressed(KeyEvent e) {
//		// finds for the which game mode 
//		if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
//			movement(e); //
//		if(game.gameState == STATE.Coop) {
//			moveCoop(e);
//			}
//		}
//	}	
//	
//
//		//  released key method  
//	public void keyReleased(KeyEvent e) {
//		int key = e.getKeyCode();
//
//		for (int i = 0; i < handler.object.size(); i++) {
//			GameObject playerObject = handler.object.get(i);
//			if (game.gameState == STATE.Game || game.gameState == STATE.Attack || game.gameState == STATE.Defense) {
//				if (playerObject.getId() == ID.Player || playerObject.getId() == ID.Player2) {
//					// key events for player 1
//					if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP)
//						keyDown[0] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
//						keyDown[1] = false;// playerObject.setVelX(0);
//					if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)
//						keyDown[2] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
//						keyDown[3] = false;// playerObject.setVelX(0);
//						keyDown[4] = false;
//					}
//
//					// vertical movement
//					if (!keyDown[0] && !keyDown[2])
//						playerObject.setVelY(0);
//					// horizontal movement
//					if (!keyDown[1] && !keyDown[3])
//						playerObject.setVelX(0);
//				}
//
//			// changed the function of key inputs for coop
//			// here the velocity is set independently of the playerObject used/
//			// keys used
//				if (game.gameState == STATE.Coop) {
//				if (playerObject.getId() == ID.Player) {
//					// key events for player 1
//					if (key == KeyEvent.VK_W)
//						keyDown[0] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_A)
//						keyDown[1] = false;// playerObject.setVelX(0);
//					if (key == KeyEvent.VK_S)
//						keyDown[2] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_D) {
//						keyDown[3] = false;// playerObject.setVelX(0);
//						keyDown[4] = false;
//					}
//
//					// vertical movement
//					if (!keyDown[0] && !keyDown[2])
//						playerObject.setVelY(0);
//					// horizontal movement
//					if (!keyDown[1] && !keyDown[3])
//						playerObject.setVelX(0);
//				}
//
//				if (playerObject.getId() == ID.Player2) {
//					// key events for player 2
//					if (key == KeyEvent.VK_UP)
//						keyDown2[0] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_LEFT)
//						keyDown2[1] = false;// playerObject.setVelX(0);
//					if (key == KeyEvent.VK_DOWN)
//						keyDown2[2] = false;// playerObject.setVelY(0);
//					if (key == KeyEvent.VK_RIGHT) {
//						keyDown2[3] = false;// playerObject.setVelX(0);
//						keyDown2[4] = false;
//					}
//
//					// vertical movement
//					if (!keyDown2[0] && !keyDown2[2])
//						playerObject.setVelY(0);
//					// horizontal movement
//					if (!keyDown2[1] && !keyDown2[3])
//						playerObject.setVelX(0);
//
//				}
//			}
//		}
//		}
//	}
//
//}