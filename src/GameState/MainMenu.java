package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
/**
 * create main menu for the maze game 
 */
public class MainMenu extends GameState {
	

	private int currentChoice = 0;
	private String[] options = {
		"res/Images/SinglePlayer",
		"res/Images/MultiPlayer",
		"res/Images/Difficulty",
		"res/Images/History",
		"res/Images/Quit"
	};
	
	
	private Font font;
	/**
	 * Constructor
	 * @param gsm current GameStateManager Object
	 */
	public MainMenu(GameStateManager gsm) {
		
		this.gsm = gsm;
	
			
	}
	
	public void init() {}
	
	/*	public void update() {
		//	bg.update();
		}
	*/
	/**
	 * displace main menu on JFrame
	 * @param g current graphic displace
	 */
	public void draw(Graphics2D g) {
		
		// draw bg
		Image background;
		ImageIcon img = new ImageIcon("res/Images/MainBackground.jpg");
		background = img.getImage();
		g.drawImage(background, 0, 0, 1280, 900, null);
		//draw titile
		Image title;
		img = new ImageIcon("res/Images/MazeGameTitle.png");
		title = img.getImage();
		g.drawImage(title, 400, 80, 500, 112, null); //350, 50, 500, 112
		
		
		// draw menu options
		font = new Font("Arial", Font.PLAIN, 40);
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			String png = ".png";
			String hover = "";
			if(i == currentChoice) {
				hover = "_hover";
			}
			img = new ImageIcon(options[i]+hover+png);
			Image button;
			button = img.getImage();
			g.drawImage(button, 520,300+i*100,255,120,null);
		}
		
	}
	/**
	 * menu control
	 */
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.SINGLEPLAYER);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.MULTIPLAYER);
		}
		if(currentChoice == 2) {
			gsm.setState(GameStateManager.DIFFICULTY);
		} 
		if(currentChoice == 3){
			gsm.setState(GameStateManager.HISTORY);
		}
		if(currentChoice == 4){
			System.exit(0);
		}
	}
	/**
	 * user control for menu
	 */
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}

	@Override
	/**
	 * user control for menu
	 */
	public void MousePressed(double x, double y) {
		if (x > 435 && x < 755){
			 if (y >= 5 && y < 400){
				 currentChoice = 0;
			 }
			 if (y >= 400 && y < 500){
				 currentChoice = 1;
			 }
			 if (y >= 500 && y < 600){
				 currentChoice = 2;
			 }
			 if (y >= 600 && y < 700){
				 currentChoice = 3;
			 }
			 if (y >= 700 && y < 800){
				 currentChoice = 4;
			 }
		}
		select();
	}

	@Override
	public void MouseReleased(double x, double y) {
		
	}
	@Override
	/**
	 * user control for menu
	 */
	public void MouseMoved(double x, double y) {
	//	System.out.println(x +" " +y);
		if (x > 435 && x < 755){
			 if (y >= 5 && y < 400){
				 currentChoice = 0;
			 }
			 if (y >= 400 && y < 500){
				 currentChoice = 1;
			 }
			 if (y >= 500 && y < 600){
				 currentChoice = 2;
			 }
			 if (y >= 600 && y < 700){
				 currentChoice = 3;
			 }
			 if (y >= 700 && y < 800){
				 currentChoice = 4;
			 }
		}
	}
	
}










