package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.net.URL;
import mainGame.*;


public class PauseMenu {

	private Image img;

	public PauseMenu() {

		img = null;
		try {
			URL imageURL = Game.class.getResource("images/PauseMenu.png");
			// add image for our pause menu here
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
