package Maze;

/** 
 *  a Class with control border of the maze<br>
 *  important note:<br>
 *  using object name to access to GameObject<br> 
 *  need to change GAMEOBJECT STATE for ADD/DELETE a wall<br>
 */
public class Wall{
	private GameObject North;
	private GameObject East;
	private GameObject South;
	private GameObject West;
	private int xCo;
	private int yCo;
	private int imagereference;
	/**
	 * Constructor:
	 * 
	 * @param x tile index in x-axis
	 * @param y tile index in y-axis
	 */
	public Wall(int x, int y){
		this.North = new GameObject("wall_north");
		North.settileX(x);
		North.settileY(y);
		this.East  = new GameObject("wall_east");
		East.settileX(x);
		East.settileY(y);
		this.South = new GameObject("wall_south");
		South.settileX(x);
		South.settileY(y);
		this.West  = new GameObject("wall_west");
		West.settileX(x);
		West.settileY(y);
		this.xCo = x;
		this.yCo = y;
	}
	/**
	 * change the existence of wall GameObject
	 * @param name of Wall GameObject
	 */
	public void flip(String n){
        GameObject o = getWall(n);	    
		o.ChangeState();
	}
	/**
	 * check if wall Object contain a particular Wall GameObject
	 * @param n name of Wall GameObject
	 * @return true if exists
	 */
	public boolean isWall(String n){
		GameObject o = getWall(n);
		//System.out.println(this.North.GetName());
		//System.out.println(o.GetName());
		if (o.GetName().equals("wall_north")){
			if (o.IsAlive())
				return true;
			else
				return false;
		}else if (o.GetName().equals("wall_west")){
			if (o.IsAlive())
				return true;
			else
				return false;
		}else if (o.GetName().equals("wall_south")){
			if (o.IsAlive())
				return true;
			else
				return false;
		}else if (o.GetName().equals("wall_east")){
		if (o.IsAlive())
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	/**
	 * print wall Game Object in ASCII on console
	 */	
	public void PrintOnConsole(String n){
		GameObject o = getWall(n);
		//System.out.println(this.North.GetName());
		//System.out.println(o.GetName());
		if (o.GetName().equals("wall_north")){
			if (o.IsAlive())
				System.out.print("+---");
			else
				System.out.print("+   ");
		}else if (o.GetName().equals("wall_west")){
			if (o.IsAlive())
				System.out.print("|   ");
			else
				System.out.print("    ");
		}else{
			
		}
			
	}
	
	/**
	 * check if wall exist by wall GameObject name
	 * @param n name of Wall GameObejct
	 * @return true if exists
	 */
	public boolean iswallbyint(String n){
		if ( n == "wall_north"){
			if (this.imagereference % 3 != 0)
				return true;
			else
				return false;
		}else if ( n == "wall_east"){
			if (this.imagereference % 5 != 0)
				return true;
			else
				return false;
		}else if ( n == "wall_south"){
			if (this.imagereference % 7 != 0)
				return true;
			else
				return false;
		}else if ( n == "wall_west"){
			if (this.imagereference % 11 != 0)
				return true;
			else
				return false;
		}else{
			return true;
		}
		
	}
	
	/**
	 * get the GameObject name by given name String
	 * @param name String of Wall GameObject
	 * @return GameObject's name
	 */
	public GameObject getWall(String name){
		if (name.equals( this.North.GetName() ) )
			return this.North;
		if (name.equals( this.East.GetName() ) )
			return this.East;
		if (name.equals( this.South.GetName() ) )
			return this.South;
		if (name.equals( this.West.GetName() ) )
			return this.West;
		return null;
		
	}
	/**
	 * get the x tile coordinate
	 * @return integer tile in x-axis
	 */
	public int GetX(){
		return this.xCo;
	}
	/**
	 * get the y tile coordinate
	 * @return integer tile in y-axis
	 */
	public int GetY(){
		return this.yCo;
	}
	
	/**
	 * a function the calculate the wall's value to put in save file
	 * 1 = wall_north
	 * 2 = wall_east
	 * 4 = wall_south
	 * 8 = wall_west
	 * @return
	 */
	public int calReference(){
		int temp = 1155;
		if (this.isWall("wall_north")){
			temp /= 3;
		}
		if (this.isWall("wall_east")){
			temp /= 5;
		}
		if (this.isWall("wall_south")){
			temp /= 7;
		}
		if (this.isWall("wall_west")){
			temp /= 11;
		}
		return temp;
	}
	/**
	 * get the Image Reference number
	 * @return integer id
	 */
	public int getImageReference(){
		return this.imagereference;
	}
	/**
	 * change the Image Reference number
	 * @param i Image Reference ID
	 * @return integer id
	 */
	public void setImageReference(int i){
		this.imagereference = i;
	}
}
//END Wall Class
