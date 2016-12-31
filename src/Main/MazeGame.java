/**
 * Main class to set up JFrame
 */
package Main;

import javax.swing.JFrame;

import Main.GamePanel;
/**
 * create the main JFrame which hold the entire game 
 * 
 *
 */
public class MazeGame {
	/**
	 * start a game
	 * @param args
	 */
	public static void main(String[] args){
		JFrame window = new JFrame("Maze Game");
	//	window.setSize(1280, 900);
		
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
