package mainGame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Loads the window of the game, and sets the proper dimensions
 * @author Brandon Loehle
 * 5/30/16
 */

public class Window extends Canvas{

	private static final long serialVersionUID = 1L;
	
	public Window(int width, int height, String title, Game game){
		// Sets static window size regardless of screen size
		width = 1100;
		height = 700;
		
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(1100, 700));
		frame.setMaximumSize(new Dimension(1100, 700));
		frame.setMinimumSize(new Dimension(1100, 700));
		//packs the frame based on the users monitor
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	
	}


}
