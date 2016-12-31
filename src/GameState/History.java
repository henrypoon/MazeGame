package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ImageIcon;
/**
 * a class that extends GameState <br>
 * display and load saved local pass highest score records
 * @author thomas
 *
 */
public class History extends GameState{
	/**
	 * a type representation of the history data that store in game
	 * @author thomas
	 *
	 */
	
	private Font font;
	
	public class history_struct implements Comparable<history_struct>{
		private int score;
		private int level;
		private String name;
		/**
		 * constructor:
		 * 
		 * @param score the score that gain by the player
		 * @param name player name
		 * @param level that the player reach
		 */
		public history_struct(int score, String name, int level){
			this.name = name;
			this.score = score;
			this.level = level;
		}
		/**
		*compare the score of pass history 
		*/
		@Override
	    public int compareTo(history_struct comparesto) {
	        int compareScore=((history_struct)comparesto).getIntScore();
	        /* For Ascending order*/
	        return compareScore - this.score;

	        /* For Descending order do like this */
	        //return compareage-this.studentage;
	    }
		/**
		*  get scorce of recrod
		*  @return integer scorce of this record
		*/
		public int getIntScore(){
			return this.score;
		}
	    
	    
	    /**
		*  get scorce of recrod
		*  @return String scorce of this record
		*/
		public String getscore(){
			return Integer.toString(this.score);
		}
		/**
		*  get level of recrod
		*  @return String level of this record
		*/
		public String getlevel(){
			return Integer.toString(this.level);
		}
		/**
		*  get name of recrod
		*  @return String name of this record
		*/
		public String getname(){
			return this.name;
		}
	}
	
	private Scanner sc;
	private int backbutton = 0;
	private ArrayList<history_struct> mylist;

	/**
	* constructor:
	* @param gsm current GameStateManager Object
	*/
	public History(GameStateManager gsm){
		this.gsm = gsm;
		this.mylist = new ArrayList<history_struct>(50);
		read();
		Collections.sort(mylist);
	}
	
	public void init() {}

/*	@Override
	public void update() {
		
	}
*/
	@Override
	/**
	 * display graphic on JFrame
	 *@param g current graphic
	 */
	public void draw(Graphics2D g) {
		font = new Font("Arial", Font.PLAIN, 40);
		g.setFont(font);
		//draw background
		Image background;
		ImageIcon img = new ImageIcon("res/Images/MainBackground.jpg");
		background = img.getImage();
		g.drawImage(background, 0, 0, 1280, 900, null);
		//draw logo
		Image title;
		img = new ImageIcon("res/Images/HistoryTitle.png");
		title = img.getImage();
		g.drawImage(title, 400,50, 500, 112, null);

		//draw title
		g.drawString("Rank", 300, 250);
		g.drawString("Score", 420, 250);
		g.drawString("Name", 620, 250);
		
		g.drawString("Level", 900,250);

		//print the history
		printList(g);
		
		//draw back button
		img = new ImageIcon("res/Images/Back.png");
		Image graph = img.getImage();
		if (backbutton == 1){
			img = new ImageIcon("res/Images/Back_hover.png");
			graph = img.getImage();
			
		}
		g.drawImage(graph, 520,700, 255, 120, null);
		g.setColor(Color.RED);
		
		
	}
	/**
	 * read the save file to recovery the pass record of the game
	 */
	private void read(){
		try
		{
			sc = new Scanner(new FileReader("src/save.txt"));
			String line = null;
			if (sc.hasNext() != false){
				if (sc != null){
					//System.out.println("start loop");
					do{
						line = sc.nextLine();
						if (line.length() == 0)
							continue;
						//System.out.println(line);
						String[] parts = line.split("#");
						history_struct temp = new history_struct(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
						//System.out.println(temp);
						mylist.add( temp );
						
						//System.out.println(mylist.size());
						

					} while (sc.hasNextLine() );
				}
			}

		} 
	  
		catch (FileNotFoundException e) {
			//System.out.println(e);
		}
		finally
		{

			if (sc != null) sc.close();
		}
	}
	
	/**
	 * method that draw out history play record on screen
	 * @param g graphic Object that are going to display on screen
	 * @return g
	 */
	private Graphics2D printList(Graphics2D g){

		int i=0;
		for (history_struct temp: this.mylist){
			g.drawString(String.valueOf(i+1), 300, 300+i*40);
			g.drawString(temp.getscore(), 420, 300+i*40);
			g.drawString(temp.getname(), 620, 300+i*40);
			g.drawString(temp.getlevel(), 900, 300+i*40);
			i++;
			if (i == 9){
				break;
			}
		}
		return g;
	}
	
	/**
	 * 	user keyboards control
	 */
	
	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_UP){
			this.backbutton = 0;
		} 
		if (k == KeyEvent.VK_DOWN){
			this.backbutton = 1;
		}
		if (k == KeyEvent.VK_ENTER){
			if (this.backbutton == 1){
				gsm.setState(GameStateManager.MAINMENU);
			}
		}
		if (k == KeyEvent.VK_BACK_SPACE){
			gsm.setState(GameStateManager.MAINMENU);
		}
	}

	@Override
	public void keyReleased(int k) {		
	}

	@Override
	/**
	 * 	action list for button control
	 */
	public void MousePressed(double x, double y) {
		if (x > 500 && x < 780){
			if (y > 680 && y < 830){
				gsm.setState(GameStateManager.MAINMENU);
			}
		} 
	}

	@Override
	public void MouseReleased(double x, double y) {
		
	}

	@Override
	/**
	 * 	user button control 
	 */
	public void MouseMoved(double x, double y) {
		//System.out.println(x+" "+y);
		if (x > 500 && x < 780){
			if (y > 680 && y < 830){
				this.backbutton = 1;
			}
		} else {
			this.backbutton = 0;
		}
	}
	
}
