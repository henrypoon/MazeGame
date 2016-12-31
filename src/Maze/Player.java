package Maze;


import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A Type of GameObject <br>
 * with act as an enemy NPC which kill players<br>
 * 
 * @author thomas
 *
 */
public class Player extends GameObject{
    private int dy;	//movement of y
	private int dx; //movement of x 
	private int delta = 1 ;//direction of movem4ent  north = 1; east = 2; south = 3; west = 4;
    private Image player_south;
    private Image player_west;
    private Image player_north;
    private Image player_east;
    private int numberofsword;
    private int steps = 0;
	
	/**
	 * Constructor:
	 * 
	 * @param name GameObject name
	 * @param numberOfSword number of swordObject that player GmaeObject have in current state 
	 */
	public Player(String name,int numberOfSword) {
		super(name);
		// TODO Auto-generated constructor stub
		ImageIcon img = new ImageIcon("res/Images/player_south.png");
		player_south = img.getImage();
		img = new ImageIcon("res/Images/player_east.png");
		player_east = img.getImage();
		img = new ImageIcon("res/Images/player_north.png");
		player_north = img.getImage();
		img = new ImageIcon("res/Images/player_west.png");
		player_west = img.getImage();
		
		this.numberofsword = numberOfSword;
		this.tilex = 0;
		this.tiley = 0;
	}

	/**
	 * change player2 icon
	 */
	public void changeP2Icon(){
		ImageIcon img = new ImageIcon("res/Images/player2_south.png");
		this.player_south = img.getImage();
		img = new ImageIcon("res/Images/player2_east.png");
		this.player_east = img.getImage();
		img = new ImageIcon("res/Images/player2_north.png");
		this.player_north = img.getImage();
		img = new ImageIcon("res/Images/player2_west.png");
		this.player_west = img.getImage();		
	}
	/**
	 * get the number of sword that currently the player GameObject have
	 * @return integer number of sword
	 */
	public int getswordnumber(){
		return this.numberofsword;
	}
	/**		
	 * change the number of sword that currently the player GameObject have		
	 * @param i number of sword changes		
	*/
	public void changeswordnumber(int i){
		this.numberofsword += i;
	}
		
	
	/**
	 * a function waiting for GameBoard to check if valid act<br>
	 * this is the main method to moving player<br>
	 */
	public void move(){
        tilex += dx;
        tiley += dy;
        this.steps++;
	}
	/**
	 * get the number of step that currently player GameObject takes 
     * @return integer number of step
	 */
	public int getSteps(){
		return this.steps;
	}	
	/**
	 * change the moving direction of x-axis in maze tile<br>
	 * reference:<br>
	 * positive = East<br>
	 * negative = West<br>
	 * @param dx integer which determine the number of tile moves
	 */
	public void setDx(int dx){
		this.dx = dx;
		 
	}
	/**
	 * change the moving direction of y-axis in maze tile<br>
	 * reference:<br>
	 * positive = South<br>
	 * negative = North<br>
	 * @param dy integer which determine the number of tile moves
	 */
	public void setDy(int dy){
		this.dy = dy;
		 
	}
	/**
	 * change the direction of Player Object facing<br>
	 * reference:
	 *   1 facing north<br>
	 *   2 facing east<br>
	 *   3 facing south<br>
	 *   4 facing west<br>
	 * @param dx direction reference
	 */
	public void setDirection(int d){
		this.delta = d;
	}
	
	/**
	 * get the direction of Player Object facing<br>
	 * reference:
	 *   1 facing north<br>
	 *   2 facing east<br>
	 *   3 facing south<br>
	 *   4 facing west<br>
	 * @param dx direction reference
	 */
	public int getdirection(){
		return this.delta;
	}
	/**
	 * get the image of Player GameObject the facing South
	 * @return Image 
	 */
	public Image getSouth(){
		return this.player_south;
	}
	/**
	 * get the image of Player GameObject the facingWest
	 * @return Image 
	 */
	public Image getWest(){
		return this.player_west;
	}
	/**
	 * get the image of Player GameObject the facing North
	 * @return Image 
	 */
	public Image getNorth(){
		return this.player_north;
	}
	/**
	 * get the image of Player GameObject the facing East
	 * @return Image 
	 */
	public Image getEast(){
		return this.player_east;
	}

}
