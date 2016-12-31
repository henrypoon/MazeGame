package Maze;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A Type of GameObject <br>
 * with act as sword with fight against with NPC  <br>
 */
public class swordObject extends GameObject{
	
    private Image sword;
    /**
     * Constructor:
     * 
     * @param name GameObject name
     * @param tilex the x index of maze which this Object 
     * @param tiley the y index of maze which this Object 
     */
	public swordObject(String name, int tilex, int tiley) {
		super(name);
		ImageIcon img = new ImageIcon("res/Images/sword.png");
		sword = img.getImage();
		
		super.myname = "sword";
		this.settileX(tilex);
		this.settileY(tiley);
		
	}
	/**
	 * overwrite method:<br>
	 * return true if both are in same tile
	 */
	public boolean equals(Object o){
		if (o instanceof swordObject){
			if (this.tilex == ((swordObject) o).tilex && this.tiley == ((swordObject) o).tiley){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * get the image of sword GameObject
	 * @return Image 
	 */
	public Image getSword(){
		return this.sword;
	}
}
