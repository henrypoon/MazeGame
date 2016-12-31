package Maze;

import java.awt.Image;
/**
 * Basic GameObject method and variable value<br>
 * contain sub-class to specified usage and gameObject function
 * 
 * 
 *
 */
public class GameObject{
	protected boolean alive;//is present in game
	protected String myname;//name of object
    protected int tilex; //coor-x
    protected int tiley; //coor-y
    protected Image image;//use image file for the object
	/**
	 * Constructor:
	 * Also create an object which present in game
	 * @param name  Object name
	 * 
	 */
	GameObject(String name){
		this.alive = true;	
		this.myname = name;
	}
	/**
	 * Reverse the state of existence of a particular GameObject
	 */
	public void ChangeState(){
		this.alive = !this.alive;
	}
	/**
	 * Check if a particular GameObject is present in this state <br>
	 * if exist return true <br>
	 * if not return false
	 * @return boolean
	 */
	public boolean IsAlive(){
		return this.alive;
	}
	/**
	 * Get the name of the GameObject
	 * 
	 * @return name in String
	 */
	public String GetName(){
		return this.myname;
	}
	/**
	 * override:<br>
	 * o should be a String 
	 * return true if GameObject name are equals
	 */
	@Override
	public boolean equals(Object o){
		if (o instanceof String){
			if (o == ((GameObject) o).myname)
				return true;
		}
		return false;
	}
    
	/**
	 * change the x-axis maze tiles value
	 * 
	 * @param x index of the tile (0 <= x < number of tile in x-axis)
	 */
    public void settileX(int x) {
        this.tilex = x;
    }
	/**
	 * change the y-axis maze tiles value
	 * 
	 * @param y index of the tile (0 <= y < number of tile in y-axis)
	 */   
    public void settileY(int y) {
        this.tiley = y;
    }
    /**
     * get the X-axis maze tiles value
     * @return index of maze x
     */
    public int gettileX() {
        return tilex;
    }
/**
 * get the y-axis maze tile value
 * @return index of maze y
 */
    public int gettileY() {
        return tiley;
    }
    
}
//End GameObject class