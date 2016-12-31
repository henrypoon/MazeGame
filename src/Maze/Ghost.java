package Maze;
import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * A Type of GameObject <br>
 * with act as an enemy NPC which kill players<br>
 */
public class Ghost extends GameObject{

    private int dy;	//movement of y
	private int dx; //movement of x 
	private int delta =1; //direction of movement  north = 1; east = 2; south = 3; west = 4;
    private Image ghost_north;
    private Image ghost_south;
	/**
	 * Constructor:
	 * 
	 * @param name GameObject name
	 * @param tilex the x index of maze which this Object 
	 * @param tiley the y index of maze which this Object 
	 */
	public Ghost(String name, int tilex, int tiley) {
		super(name);
		
		ImageIcon img = new ImageIcon("res/Images/ghost_north.png");
		ghost_north = img.getImage();
		img = new ImageIcon("res/Images/ghost_south.png");
		ghost_south = img.getImage();
		this.settileX(tilex);
		this.settileY(tiley);
		}

	/**
	 * a function waiting for GameBoard to check if valid act<br>
	 * this is the main method to moving player<br>
	 */
	public void move(){
        tilex += dx;
        tiley += dy;

	}
	/**
	 * a function waiting for GameBoard to check if valid act<br>
	 * this is the main method to moving player<br>
	 */
	public void reverseMove(){
        tilex -= dx;
        tiley -= dy;
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
	 * change the direction of Ghost Object facing<br>
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
	 * get the direction of Ghost Object facing<br>
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
	 * return true when both are in the same tile 
	 */
	@Override
	public boolean equals(Object o){
		if (o instanceof Ghost){
			if (this.tilex == ((Ghost) o).tilex && this.tiley == ((Ghost) o).tiley){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * get the image of Ghost GameObject the facing North, East and West
	 * @return Image 
	 */
	public Image getGhostNorth(){
		return this.ghost_north;
	}
	/**
	 * get the image of Ghost GameObject the facing Sourth
	 * @return Image 
	 */
	public Image getGhostSouth(){
		return this.ghost_south;
	}
}
