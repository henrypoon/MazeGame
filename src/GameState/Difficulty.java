package GameState;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
/**
 * displace difficulty setting and graphics
 * 
 *
 */
public class Difficulty extends GameState{
	
	private int currentChoice = 0;
	private String[] options = {
		"res/Images/Easy",
		"res/Images/Normal",
		"res/Images/Hard",
		"res/Images/Back",
	};
	
	private Font font;
	
	/**		
	 * Constructor:		
	 * @param gsm current GameStateManager		
	 */
	public Difficulty(GameStateManager gsm){
		this.gsm = gsm;
	}

	@Override
	public void init() {
		
	}

	/**
	 * displace difficulty menu
	 * @param g current graphic 
	 * @return g
	 */
	@Override
	public void draw(Graphics2D g) {
		//draw background
		Image background;
		ImageIcon img = new ImageIcon("res/Images/MainBackground.jpg");
		background = img.getImage();
		g.drawImage(background, 0, 0, 1280, 900, null);
		
		
		img = new ImageIcon("res/Images/DifficultyTitle.png");
		Image title = img.getImage();		
		g.drawImage(title, 400,50, 500, 112, null);
		//draw logo
	//	Image title;
	//	img = new ImageIcon("res/Images/HistoryTitle.png");
	//	title = img.getImage();
	//	g.drawImage(title, 0, 0, 1280, 900, null);
		
		
		font = new Font("Arial", Font.PLAIN, 60);
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
			if (i <= 2){
				g.drawImage(button, 520,250+i*100,255,120,null);
			} else {
				g.drawImage(button, 520,700,255,120,null);
			}
			
		}
		
	}
/**
 * menu control
 */
	private void select() {
		if(currentChoice == 0) {
			gsm.setStartLevel(1);
		}
		if(currentChoice == 1) {
			gsm.setStartLevel(4);
		}
		if(currentChoice == 2) {
			gsm.setStartLevel(6);
		} 
		if(currentChoice == 3){
			gsm.setState(GameStateManager.MAINMENU);
		}
		gsm.setState(GameStateManager.MAINMENU);
	}
	/**
	 * user control for menu control
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
	/**
	 * user control for menu control
	 */
	@Override
	public void MousePressed(double x, double y) {
		if (x > 560 && x < 780){
			 if (y >= 5 && y < 375){
				 currentChoice = 0;
			 }
			 if (y >= 375 && y < 475){
				 currentChoice = 1;
			 }
			 if (y >= 475 && y < 565){
				 currentChoice = 2;
			 }
			 if (y >= 640 && y < 710){
				 currentChoice = 3;
			 }
		}
		select();
	}

	@Override
	public void MouseReleased(double x, double y) {

		
	}
	/**
	 * user control for menu control
	 */
	public void MouseMoved(double x, double y) {
		//System.out.println(x +" " +y);
		if (x > 560 && x < 780){
			 if (y >= 5 && y < 375){
				 currentChoice = 0;
			 }
			 if (y >= 375 && y < 475){
				 currentChoice = 1;
			 }
			 if (y >= 475 && y < 565){
				 currentChoice = 2;
			 }
			 if (y >= 690 && y < 830){
				 currentChoice = 3;
			 }
		}
	}
}
