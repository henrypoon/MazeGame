package Maze;
/**
 * a class for displacing and generate mini map
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
//378*378
//126
public class minimap implements Transparency{
	private int lenX;
	private int lenY;
	private int[][] R;
	private int quaterlowerX ;
	private int quaterhigherX ;
	private int quaterlowerY ;
	private int quaterhigherY ;
	private int size;

/**
 * Constructor:
 * 
 * @param numX number of tile in x-axis
 * @param numY number of tile in y-axis
 */
	public minimap(int numX, int numY){
		this.lenX= numX/3;
		this.lenY= numY/3;
		this.R = new int[3][3];
		quaterlowerX = lenX/4;
		quaterhigherX = 3*(lenX/4);
		quaterlowerY = lenY/4;
		quaterhigherY = 3*(lenY/4);
		this.size = 60;
		this.init();
		R[0][0]=1;
	//	System.out.println(numX + "," +numY);
	}
	/**
	 * displace mini map in game console
	 * @param g current game graphic
	 * @return g
	 */
	private Graphics2D drawmap(Graphics2D g){
		int aqx = 0;
		int aqy = 0;
		for (int H =0 ; H<3; H++){
			aqx = 0;
			for(int W = 0; W<3;W++){
				if (R[H][W] == 0)
					g.setColor(Color.lightGray);
				else
					g.setColor(Color.BLACK);
				g.fillRect(aqx, aqy, this.size, this.size);
				aqx +=this.size;
			}
			aqy +=this.size;
		}
		return g;
	}
	/**
	 * Initialize 2D array in mini map 
	 */
	private void init(){
		for (int H =0 ; H<3; H++){
			for(int W = 0; W<3;W++){
				R[H][W]=0;
			}
		}
	}
	/**
	 * get the total pixel length of mini map
	 * @return integer number of pixel 
	 */
	public int getLength(){
		return this.size*3;
	}
	/**
	 * change the mini map displace 
	 * @param playerx tile index x of player
	 * @param playery tile index y of player
	 */
	private void change(int playerx, int playery){
		//System.out.println(playerx +"," +playery);
		//System.out.println(quaterlowerX + "," +quaterhigherX);
		//System.out.println((quaterlowerX+lenX) + "," +(quaterhigherX+lenX));
		//System.out.println((quaterlowerX+(2*lenX))+ "," +(quaterhigherX+(2*lenX)));
		if (quaterlowerX <playerx && playerx < quaterhigherX && quaterlowerY <playery && playery < quaterhigherY ){
			init();
			R[0][0] = 1;
		}else if (quaterlowerX+lenX <=playerx && playerx <= quaterhigherX+lenX && quaterlowerY <=playery && playery <= quaterhigherY){
			init();
			R[0][1] = 1;
		}else if (quaterlowerX+(2*lenX) <=playerx && playerx <= quaterhigherX+(2*lenX) && quaterlowerY <=playery && playery <= quaterhigherY){
			init();
			R[0][2] = 1;
		}else if (quaterlowerX <=playerx && playerx <= quaterhigherX && quaterlowerY+lenY <=playery && playery <= quaterhigherY+lenY){
			init();
			R[1][0] = 1;
		}else if (quaterlowerX+lenX <=playerx && playerx <= quaterhigherX+lenX && quaterlowerY+lenY <=playery && playery <= quaterhigherY+lenY){
			init();
			R[1][1] = 1;
		}else if (quaterlowerX+(2*lenX) <=playerx && playerx <= quaterhigherX+2*+lenX && quaterlowerY+lenY <=playery && playery <= quaterhigherY+lenY){
			init();
			R[1][2] = 1;
		}else if (quaterlowerX <=playerx && playerx <= quaterhigherX && quaterlowerY+(2*lenY) <=playery && playery <= quaterhigherY+(2*lenY)){
			init();
			R[2][0] = 1;
		}else if (quaterlowerX+lenX <=playerx && playerx <= quaterhigherX+lenX && quaterlowerY+(2*lenY) <=playery && playery <= quaterhigherY+(2*lenY)){
			init();
			R[2][1] = 1;
		}else if (quaterlowerX+(2*lenX) <=playerx && playerx <= quaterhigherX+(2*lenX) && quaterlowerY+(2*lenY) <=playery && playery <= quaterhigherY+(2*lenY)){
			init();
			R[2][2] = 1;

		}
		else{}
	}
	/**
	 * change mini map from graphic to Image 
	 * @param playerx tile index x of player
	 * @param playery tile index y of player
	 * @return minimap BufferedImage
	 */
	public Image getminimap(int playerx, int playery){
		BufferedImage temp = new BufferedImage(378, 378, TRANSLUCENT);
		Graphics2D g;
		g= (Graphics2D) temp.getGraphics();
		change(playerx, playery);
		g = drawmap(g);
		g.setComposite(AlphaComposite.DstOut);
		return temp;
	}
	
	@Override
	public int getTransparency() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//end minimap
}
