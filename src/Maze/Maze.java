package Maze;


import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Generator.BackTrackingGenerator;
import Generator.Generator;
import Generator.KruskalGenerator;

/**
 * 	Main Maze Object in game
 * 
 *	important note:
 *	access a wall using GameObject's name
 *  wall_north ; wall_east ; wall_south ; wall_west
 *  using (Wall.flip(String name) to add/ delete a wall 
 */
public class Maze implements Transparency{
	
	private Wall[][] mymaze;
	private int height;
	private int width;
	private Generator generator;
	private BufferedImage entireMaze;
	private int tileImagelength = 200;
	/**
	 * Constructor:
	 * creating a 2D wall array for making border for maze
	 * require a maze generator to generate a fun maze
	 * deflaut as the maze is fill up with border
	 * @param H number of tile in x-axis
	 * @param W number of tile in y-axis
	 */ 
	public Maze(int H, int W, int level){
		if (level <=2){
		 	this.generator = new KruskalGenerator();
		 }else{
			this.generator = new BackTrackingGenerator();
		 }
		this.mymaze = new Wall[H][W];
		for (int eachx = 0; eachx < H; eachx++){
			for (int eachy = 0; eachy < W; eachy++){
				mymaze[eachx][eachy] = new Wall(eachx,eachy);
			}
		}
		this.height = H;
		this.width = W;
		generator.generation(this);
		calAllReference();
		buildmazeImage();
	}

	
	/**
	 * get the wall Object from the 2D wall array 
	 * @param x index of tile in x-axis
	 * @param y index if tile in y-axis
	 * @return wall Object
	 */
	public Wall getWall(int x,int y){
		return this.mymaze[x][y];
	}
	
	/**
	 * get the number of tile in x-axis of the maze
	 * @return integer size of wall in x-axis
	 */
	public int getX(){
		return this.width;
	}
	/**
	 * get the number of tile in y-axis of the maze
	 * @return integer size of wall in y-axis
	 */
	public int getY(){
		return this.height;
	}
	
	/**
	 * check if a wall GameObject present in the direction of Player GameObject or Ghost GameObject 
	 * @param x GameObject current tile index in x-axis
	 * @param y GameObject current tile index in y-axis
	 * @param direction direction index
	 * @return true if a is present
	 */
	public boolean ifwallbyint(int x, int y, int direction){
		if (x <0 || y< 0 || direction ==0)
			return false;
	//	//System.out.println(x + "," + y
	//			+"  "+ (this.dirtoname(direction) ));
		return this.mymaze[y][x].iswallbyint(this.dirtoname(direction));
	}
	
	
	
	/**
	 * delete a wall GameObject by given (x,y) index of maze tile and wall name
	 * @param x tile index in x-axis
	 * @param y tile index in y-axis
	 * @param wallname name of the wall Object
	 */
public void deleteEdge(int x,int y,String wallname){
	this.mymaze[y][x].flip(wallname);
	if (y != 0 && wallname == "wall_north"){
		this.mymaze[y-1][x].flip(GetOppositeEdge(wallname));
	}
	if (x != (this.width-1) && wallname == "wall_east"){
		this.mymaze[y][x+1].flip(GetOppositeEdge(wallname));
	}
	if (y != (this.height-1) && wallname == "wall_south"){
		this.mymaze[y+1][x].flip(GetOppositeEdge(wallname));
	}
	if (x != 0 && wallname == "wall_west"){
		this.mymaze[y][x-1].flip(GetOppositeEdge(wallname));
	}
}
	/**
	 * get the opposing name of wall GameObject
	 * @param name wall GameObeject name
	 * @return opposite wall name
	 */
private static String GetOppositeEdge(String name){
	if (name.equals( "wall_north" ))
		return "wall_south";
	if (name.equals( "wall_east" ))
		return "wall_west";
	if (name.equals( "wall_south" ))
		return "wall_north";
	if (name.equals( "wall_west" ))
		return "wall_east";
	return null;
}

/*
/**
 *   a function draw entire maze out by coordinate
 * 	 @param I = graphic g
 *   @param startx coor-x of left hand corner of maze grid
 *   @param starty coor-y of left hand corner of maze grid
 *   @param len length of each box
public Graphics drawMaze(Graphics I,int startx, int starty, int len){
	int StartX = startx; 
	int StartY = starty; 
	int Length = len; 
	//draw horizontal line
	int X;
	int Y = StartY;
	for (int i = 0; i < this.height; i++) {
		X = StartX;
		for (int j = 0; j < this.width; j++) {
			if(this.mymaze[i][j].isWall("wall_north")){
				I.drawLine(X, Y, X+Length, Y);
			//	I.drawOval(X, Y, 11, 11);
			}
			if(i == (this.height-1) ) {
				if(this.mymaze[i][j].isWall("wall_south")){
					I.drawLine(X, Y+Length, X+Length, Y+Length);
				}
			}
			X +=Length;
		}
		Y +=Length;
	}
	//draw vertical line
	X = StartX;
	for (int j = 0; j < this.width; j++) {
		Y = StartY;
		for (int i = 0; i < this.height; i++) {
			if(this.mymaze[i][j].isWall("wall_west")){
				I.drawLine(X, Y, X, Y+Length);
			//	I.drawOval(X, Y, 5, 5);
			//	I.drawString("("+i+"x"+j+")", 100+X*10, 10+Y*10);
			}
			if(j == (this.width-1) ) {
				if(this.mymaze[i][j].isWall("wall_east")){
					I.drawLine(X+Length, Y, X+Length, Y+Length);
				}
			}
			Y +=Length;
		}
		X +=Length;
	}

	return I;
}
*/
	/**
	 * Debug method :
	 * display maze in ASCII on console
	 */
public void DisplayMaze(){
	for (int i = 0; i < width; i++) {
		// draw the north edge
		for (int j = 0; j < height; j++) {
			mymaze[j][i].PrintOnConsole("wall_north");
		}
		System.out.println("+");
		// draw the west edge
		for (int j = 0; j < height; j++) {
			mymaze[j][i].PrintOnConsole("wall_west");
		}
		System.out.println("|");
	}
	// draw the bottom line
	for (int j = 0; j < height; j++) {
		System.out.print("+---");
	}
	System.out.println("+");
}


/**
 *  use for change direction int to wall name
 *@param reference index of wall GameObeject name
 *@return name of wall GameObject
 */
	private String dirtoname(int d){
		if (d ==1)
			return "wall_north";
		if (d ==4)
			return "wall_west";
		if (d ==3)
			return "wall_south";
		if (d ==2)
			return "wall_east";
		
		return null;
	}

/**
 * Find the neighbour of a grid, it will actually store the wall that connect to those neighbours
 * 
 * @param current wall Object that checking
 * @return ArrayList <GameObject> wall GameObject s
 */
	public ArrayList<GameObject> getNeighbour(Wall current){
		int x = current.GetX();
		int y = current.GetY();
		ArrayList<GameObject> getNeighbour = new ArrayList<GameObject>();
		if (x == 0 && y == 0){
			getNeighbour.add(current.getWall("wall_east"));
			getNeighbour.add(current.getWall("wall_south"));
		} else if (x == 0 && y == this.height-1){
			getNeighbour.add(current.getWall("wall_north"));
			getNeighbour.add(current.getWall("wall_east"));
		} else if (x == this.width -1 && y == 0){
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_south"));
		} else if (x == this.width -1 && y == this.height -1){
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_north"));
		} else if (x == 0 && 0 < y && y < this.height-1){
			getNeighbour.add(current.getWall("wall_east"));
			getNeighbour.add(current.getWall("wall_north"));
			getNeighbour.add(current.getWall("wall_south"));
		} else if (0 < x && x < this.width-1 && y == 0){
			getNeighbour.add(current.getWall("wall_east"));
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_south"));
		} else if (x == this.width-1 && 0 < y && y < this.height-1){
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_south"));
			getNeighbour.add(current.getWall("wall_north"));
		} else if (0 < x && x < this.width-1 && y == this.height -1){
			getNeighbour.add(current.getWall("wall_east"));
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_north"));
		} else {
			getNeighbour.add(current.getWall("wall_east"));
			getNeighbour.add(current.getWall("wall_south"));
			getNeighbour.add(current.getWall("wall_west"));
			getNeighbour.add(current.getWall("wall_north"));
		}
		return getNeighbour;
	}
	/**
	 * apply reference value for wall currently present
	 */
	private void calAllReference(){
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.mymaze[i][j].setImageReference(this.mymaze[i][j].calReference());
			}
		}
	}
	/**
	 * build the entire map into buffered Image<br>
	 * and store in entiremaze
	 */
	private void buildmazeImage(){
		BufferedImage temp = new BufferedImage(this.tileImagelength*this.width, this.tileImagelength*this.height, TRANSLUCENT);
		Graphics2D g;
		g= (Graphics2D) temp.getGraphics();
		int X=0;
		int Y=0;
		for (int H=0; H <this.height; H++){
			Y = 0;
			for (int W=0; W <this.width;W++){
				BufferedImage i = null;
				try {
				    i = ImageIO.read( new File( referencetoname( this.mymaze[W][H].getImageReference() ) ) );
				} catch (IOException e) { }
				g.drawImage(i, X, Y, X+this.tileImagelength, Y+this.tileImagelength, 0, 0, this.tileImagelength, this.tileImagelength, null);
				Y+= this.tileImagelength;
			}
			X+= this.tileImagelength;
		}
		this.entireMaze = temp;
		
	}
	/**
	 * get the String of wall Object image
	 * @param RID reference ID
	 * @return Image Path
	 */
	private static String referencetoname(int RID){
		String temp = null;
		////System.out.println(RID);
		switch(RID){
		case 1:
			temp="res/temp/base2.png";
			break;
		case 3: //north
			temp="res/temp/n2.png";
			break;
		case 5://east
			temp="res/temp/e2.png";
			break;
		case 7://south
			temp="res/temp/s2.png";
			break;
		case 11://west
			temp="res/temp/w2.png";
			break;
		case 15://n-e
			temp="res/temp/n-e2.png";
			break;
		case 21://n-s
			temp="res/temp/n-s2.png";
			break;
		case 33://n-w
			temp="res/temp/n-w2.png";
			break;
		case 35://e-s
			temp="res/temp/e-s2.png";
			break;
		case 55://e-w
			temp="res/temp/e-w2.png";
			break;
		case 77://s-w
			temp="res/temp/s-w2.png";
			break;
		case 105://n-e-s
			temp="res/temp/n-e-s2.png";
			break;
		case 385://e-s-w
			temp="res/temp/e-s-w2.png";
			break;
		case 231://n-s-w
			temp="res/temp/n-s-w2.png";
			break;
		case 165://n-e-w
			temp="res/temp/n-e-w2.png";
			break;
		case 1155://n-e-s-w
			temp="res/temp/n-e-s-w2.png";
			break;
		}
		return temp;
	}
	/**
	 * copy entire maze into new buffered Image
	 * @return maze map
	 */
	public BufferedImage getmazemap(){
		BufferedImage bi = this.entireMaze;
			 ColorModel cm = bi.getColorModel();
			 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
			 WritableRaster raster = bi.copyData(null);
			 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	/**
	 * return number of pixel contain by the maze in y-direction 
	 * @return number of pixel 
	 */
	public int gettotalHeight(){
		return this.tileImagelength*this.height;
	}
	/**
	 * return number of pixel contain by the maze in x-direction 
	 * @return number of pixel 
	 */
	public int gettotalWidth(){
		return this.tileImagelength*this.width;
	}
	/**
	 * return number of pixel contain the Image of tile  
	 * @return number of pixel 
	 */
	public int gettileimagelength(){
		return this.tileImagelength;
	}

	@Override
	public int getTransparency() {
		return 0;
	}

}
//End Maze class