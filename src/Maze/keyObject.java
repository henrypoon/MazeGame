package Maze;

import java.awt.Image;
import javax.swing.ImageIcon;
/**
 * A Type of GameObject <br>
 * with act as a key which open next level door <br>
 * 
 * 
 *
 */
public class keyObject extends GameObject{
	
    private Image map;
    /**
     * Constructor:
     * 
     * @param name GameObject name
     * @param tilex the x index of maze which this Object 
     * @param tiley the y index of maze which this Object 
     */
	public keyObject(String name, int tilex, int tiley) {
		super(name);
		ImageIcon img = new ImageIcon("res/Images/map.png");
		map = img.getImage();
		
		super.myname = "key";
		this.settileX(tilex);
		this.settileY(tiley);
		
	}
	/**
	 * overwrite method:<br>
	 * return true if both are in same tile
	 */
	public boolean equals(Object o){
		if (o instanceof keyObject){
			if (this.tilex == ((keyObject) o).tilex && this.tiley == ((keyObject) o).tiley){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * get the image of Key GameObject
	 * @return Image 
	 */
	public Image getMap(){
		return this.map;
	}
}
