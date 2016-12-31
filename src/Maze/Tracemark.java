package Maze;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * A Type of GameObject <br>
 * with act as tracemark that drop by Player for road recognition<br>
 * 
 * 
 *
 */
public class Tracemark extends GameObject{
	
	private Image mark;
	/**
     * Constructor:
     * 
     * @param name GameObject name
     * @param tilex the x index of maze which this Object 
     * @param tiley the y index of maze which this Object 
     */
	public Tracemark(String name, int tilex, int tiley) {
		super(name);
		ImageIcon img = new ImageIcon("res/Images/nutsandbolts.png");
		mark = img.getImage();
		
		super.myname = "key";
		this.settileX(tilex);
		this.settileY(tiley);
	}
	/**
	 * overwrite method:<br>
	 * return true if both are in same tile
	 */
	public boolean equals(Object o){
		if (o instanceof Tracemark){
			if (this.tilex == ((Tracemark) o).tilex && this.tiley == ((Tracemark) o).tiley){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * get the image of Tracemark GameObject
	 * @return Image 
	 */
	public Image getMark(){
		return this.mark;
	}
	
}
