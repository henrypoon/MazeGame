/**
 * Generate the single player gameboard
 * 
 * 
 */
/**
 */
package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import Maze.Ghost;
import Maze.Maze;
import Maze.Player;
import Maze.Tracemark;
import Maze.keyObject;
import Maze.minimap;
import Maze.swordObject;


public class SinglePlayer extends GameState implements ActionListener{
	private Maze m;
	private Player p1;
	private List<keyObject> keys; 
	private List<Ghost> ghosts; 
	private List<swordObject> swords;
	private List<Tracemark> mark;
	private int keyNum = 0;
	private int ghostNum = 0;
	private int level = 0;
	private int pauseFlag = 0;
	private int menuselection = 0;
	private int height = 4;
	private int width = 4;
	private int showtile = width;
	private int score = 100;
	private minimap mazemap;
	private int numberOfSword = 0;
	private int exitX;
	private int exitY;
	
	/**
	 * constructor 
	 * @param startLevel Game level
	 * @param gsm current GameStateManager
	 */
	public SinglePlayer(int startLevel,GameStateManager gsm){
		
		this.gsm = gsm;
		this.level = startLevel;
		if (gsm.getStartLevel() == 1){
			this.score = 100;
		}
		if (gsm.getStartLevel() == 4){
			this.score = 800;
		}
		if (gsm.getStartLevel() == 6){
			this.score = 1500;
		}
		init();
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	/**
	 * initial the game board
	 */
	
	public void init(){
		
		SPlevelGenerator();

		tips();
		
		swords = new ArrayList<swordObject>();
		keys = new ArrayList<keyObject>();
		ghosts = new ArrayList<Ghost>();
		mark = new ArrayList<Tracemark>();
		this.showtile = width;
		m = new Maze(height,width, this.level);
		mazemap = new minimap(width, width);
		p1= new Player("human",this.numberOfSword);
		Random ran = new Random();
		int x1 = ran.nextInt(this.width);	//choose random player start point
		int y1 = ran.nextInt(this.width);
		p1.settileX(x1);		
        p1.settileY(y1);
    	addingkeystogame  (this.keyNum,width,height); // first param is number 
		addingghoststogame(this.ghostNum,width,height);// first param is number
		

		this.exitX = ran.nextInt(this.width); //choosing random exit
		this.exitY = ran.nextInt(this.width);
		
		
		
		//adding sword to game
        if(swords.size() <= 5 && 9 <= this.level){
        	if (9 <= this.level && this.level < 15 ){// 1
        		addingswordtogame(1,width,height);
        	}else if (15<= this.level && this.level < 19){// 2
        		addingswordtogame(2,width,height);
        	}else if (19<= this.level && this.level < 25){// 3
        		addingswordtogame(3,width,height);
        	}else if (25<= this.level && this.level < 29){// 4
        		addingswordtogame(4,width,height);
        	}else{
        		addingswordtogame(5,width,height);
        	}
    
        }
		

		
	}
	/**
	 * generate the maze by level(number of swords, keys, monsters, sizes)
	 */
	
	private void SPlevelGenerator(){
		if (level < 6){
			this.width = 4 + (level-1)*2;
			this.height = 4 + (level-1)*2;
		} else {
			this.width = 12 + level;
			this.height = 12 + level;
		}
	
		
		if (3 < level && level < 6){
			this.keyNum = this.level;
			
		}
		if (6 <= level && level < 10){
			this.ghostNum = this.level - 3;
			this.keyNum = 6;
		}
		if (level >= 10){
			this.ghostNum = this.level - 6;
			this.keyNum = this.level - 5;
		}
	}
	/**
	 * pop up a instruction manuel
	 */
	private void tips(){
		if (level == 1){
			String s = 
	"Controls:"
			+ "\n\n\n"
			+ ""
			+ "W-A-S-D | Player 1 Movement\n"
			+ "E              | Player 1 Place tracer / Pick up tracer\n"
			+ "Z              | Activates/Deactivates Zoom mode\n"
			+ "ESC         | Pause Menu\n"
			+ "SPACE    | Bring up / close this menu\n\n\n"
			+ ""
			+ "Gamplay:\n\n"
			+ "Your goal is to reach the safe zone (Green Square) while avoiding the ghosts.\n"
			+ "The ability to finish the map is only unlocked when all Maps are collected.\n\n"
			+ "A Sword will sometimes spawn for you in a random area.\n"
			+ "This will grant you the ability to survive one ghost and eliminate it from the current map.\n"
			+ "Be warned, the enemies in this game have the ability to mimic your every action!\n\n"
			+ "Use this knowledge to your advantage as you encounter one in your path.";
JOptionPane.showMessageDialog(null, s, "Instruction", JOptionPane.INFORMATION_MESSAGE);
}
		

	}
	/**
	 * draw the game board
	 */
	
	@Override
	public void draw(Graphics2D g) {
	//	draw background
		Font font = new Font("Arial", Font.PLAIN, 40);
		g.setFont(font);
		Image graph;
		ImageIcon img = new ImageIcon("res/Images/loading.jpg");
		graph = img.getImage();
		g.drawImage(graph, 0, 0, 900, 900, null);

		//g = drawzoommaze(g, showtile); // number of tile display
		g = drawnewmaze(g,showtile);
	// fill right-hand-side 
		g.setColor(new Color(123, 140, 160));
		g.fillRect(900, 0, 380, 900);
	// draw mini map
		if (this.showtile != this.width){
				g.setColor(Color.white);
				g.drawString("MiniMap", 1020, 50);
				g.drawImage(mazemap.getminimap(p1.gettileX(), p1.gettileY()), 1000, 70, 1000+mazemap.getLength(), 70+mazemap.getLength(), 0, 0, mazemap.getLength(),  mazemap.getLength(), null);
		}
		
	
	// draw score board
		g =drawScoreboard(g);
		
		//draw button		
		
	
	  // 	g.drawString("Pause", 1060, 700);
	//	g.drawString("Generate New", 1015, 750);

	   	 if (this.pauseFlag == 1){
	 		//		Image test1;
				img = new ImageIcon("res/Images/PauseMenuBackground.jpg");
				graph = img.getImage();
				g.drawImage(graph, 480, 200, 400, 600, null); 
				
				img = new ImageIcon("res/Images/PauseMenuTitle.jpg");
				graph = img.getImage();
				g.drawImage(graph, 500, 220, 360, 150, null); 
			
				
				if (this.menuselection == 0 ){
					img = new ImageIcon("res/Images/Resume_hover.png");
					graph = img.getImage();
					
				}else{
					img = new ImageIcon("res/Images/Resume.png");
					graph = img.getImage();
				}
				g.drawImage(graph, 555, 450, 255, 120, null);
				
				if (this.menuselection == 1 ){
					img = new ImageIcon("res/Images/Giveup_hover.png");
					graph = img.getImage();
				}else{
					img = new ImageIcon("res/Images/Giveup.png");
					graph = img.getImage();
				}
				g.drawImage(graph, 555, 600, 255, 120, null);
				
				img = new ImageIcon("res/Images/Pause.png");
				graph = img.getImage();
				g.drawImage(graph, 960, 600, 255, 120, null);
				
				img = new ImageIcon("res/Images/NewMaze.png");
				graph = img.getImage();
				g.drawImage(graph, 960, 750, 255, 120, null);
				
	
	   	 	} else {
				if (this.menuselection == 2){
					img = new ImageIcon("res/Images/Pause_hover.png");
					graph = img.getImage();
				} else {
					img = new ImageIcon("res/Images/Pause.png");
					graph = img.getImage();
				}
				g.drawImage(graph, 960, 600, 255, 120, null);
				if (this.menuselection == 3){
					img = new ImageIcon("res/Images/NewMaze_hover.png");
					graph = img.getImage();
				} else {
					img = new ImageIcon("res/Images/NewMaze.png");
					graph = img.getImage();
				}
				g.drawImage(graph, 960, 750, 255, 120, null);
	   	 	}

	   	 
	}
	
	/**
	 * 	user button control
	 */
	
	@Override
	public void keyPressed(int k) {
		Ghost temp;

			p1.setDx(0);
			p1.setDy(0);
		
			if (k == KeyEvent.VK_ESCAPE){
				if (this.pauseFlag == 1){
					this.pauseFlag = 0;
				} else {
					this.pauseFlag = 1;
				}
			}
			
			if (this.pauseFlag == 1){
				if(k == KeyEvent.VK_ENTER){
					select();
				}
				if(k == KeyEvent.VK_UP) {
					this.menuselection--;
					if(this.menuselection == -1) {
						this.menuselection = 1;
					}
				}
				if(k == KeyEvent.VK_DOWN) {
					this.menuselection++;
					if(this.menuselection == 2) {
						this.menuselection = 0;
					}
				}
			}
			
			if (k == KeyEvent.VK_SPACE){
				tips();

			}
			if (this.pauseFlag == 0){
				if (k == KeyEvent.VK_W){
					p1.setDy(-1);
					p1.setDirection(1);
				}
				if (k == KeyEvent.VK_S){
					p1.setDy(1);
					p1.setDirection(3);
				}
				if (k == KeyEvent.VK_A){
					p1.setDx(-1);
					p1.setDirection(4);
				}
				if (k == KeyEvent.VK_D){
					p1.setDx(1);
					p1.setDirection(2);
				}
				
				
				if (k == KeyEvent.VK_Z){
					if (this.level > 5)
						this.zoomvalue();
				}
				
				if (k == KeyEvent.VK_E){
					int f = 0;
					if (mark!= null){
						for (Tracemark tm : mark){
							if ( tm.gettileX() ==p1.gettileX() && p1.gettileY()== tm.gettileY() ){
								mark.remove(tm);
								f = 1;
								break;
							}
						}
					}
					if (f==0){
						mark.add( new Tracemark("mark", p1.gettileX(), p1.gettileY()));
					}
				}
				
				
				
				
				collisionCheck();
		        if (p1.IsAlive() && ! m.ifwallbyint(p1.gettileX(), p1.gettileY(), p1.getdirection())) {
		            if (k == KeyEvent.VK_W || k == KeyEvent.VK_S || k == KeyEvent.VK_A || k == KeyEvent.VK_D){
			        	p1.move();
			     //   	System.out.println(keys.size());
			            if (keys.size() !=0){
			            	this.score -= 5;
			            } else {
				            this.score -= 2;
			            }
		            }    
		        }
		        
			   	 if (ghosts != null){
			   			for (int i =0 ; i < ghosts.size(); i ++){
					   		 temp = ghosts.get(i);
				   			 temp.setDx(0);
				   			 temp.setDy(0);
								if (k == KeyEvent.VK_W){
									temp.setDy(-1);
									temp.setDirection(1);
								}
								if (k == KeyEvent.VK_S){
									temp.setDy(1);
									temp.setDirection(3);
								}
								if (k == KeyEvent.VK_A){
									temp.setDx(-1);
									temp.setDirection(4);
								}
								if (k == KeyEvent.VK_D){
									temp.setDx(1);
									temp.setDirection(2);
								}
					   		 if (temp.IsAlive() && ! m.ifwallbyint(temp.gettileX(), temp.gettileY(), temp.getdirection())){
					   			 temp.move();
					   			 if (ghostcollisioncheck(i)){
					   				temp.reverseMove();
					   			 }
					   		 }
					   	 }
			   	
				   	 
			   	 }
		        //test for GameObject collisions
		        collisionCheck();
		        
		        
		        //end point,right bottom corner
	
		        if (keys.isEmpty()){//all the keys need to be get before going into exit
		        		if(p1.gettileX() == exitX && p1.gettileY()== exitY){
		        			score = score + level * 350;
				        	if(score < 0){
				        		score = 0;
				        	}
				            JOptionPane.showMessageDialog(null, "Congratulations, You have beaten the level! Your score was " + 
				            score, "End Game", JOptionPane.INFORMATION_MESSAGE);
				        	level++;
				        	this.numberOfSword = p1.getswordnumber();
				        	this.m = null;
				            init();
				        }

		        } else {
		        	if(p1.gettileX() == exitX && p1.gettileY()== exitY){
		        		JOptionPane.showMessageDialog(null, "You have to collect all the keys to clear the level!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		        	}
		        }
			}		        
		
	}
	
	
	@Override
	public void keyReleased(int k) {
		p1.setDx(0);
		p1.setDy(0);
		 if (ghosts != null){
		   	 for (int i =0 ; i < ghosts.size(); i ++){
					ghosts.get(i).setDx(0);
					ghosts.get(i).setDy(0);
		   	 }
		 }
		
	}
	
	/**
	 * prevent ghosts stand in the same tile 
	 * @param indexofghost
	 * @return
	 */
	private boolean ghostcollisioncheck(int indexofghost){
		int currghostx = ghosts.get(indexofghost).gettileX();
		int currghosty = ghosts.get(indexofghost).gettileY();
		for (int i =0 ; i < ghosts.size(); i ++){
			if (i == indexofghost){
				continue;
			}
			int tempghostx = ghosts.get(i).gettileX();
			int tempghosty = ghosts.get(i).gettileY();
			if (currghostx == tempghostx && currghosty == tempghosty){
				return true;
			}
		}
		return false;
	}
	/**
	 * this method is checking whether the player hit others(keys, swords, monster)
	 */
	private void collisionCheck(){
		//System.out.println("test");
		int tempx1 = p1.gettileX();
		int tempy1 = p1.gettileY();
		//collision of players and keys
		if (keys != null){
			for (int i =0 ; i < keys.size(); i ++){
				int tempkeyx = keys.get(i).gettileX();
				int tempkeyy = keys.get(i).gettileY();
				if ((tempx1 == tempkeyx && tempkeyy == tempy1)){
					keys.get(i).ChangeState();
					keys.remove(i);
					score += 100;
				}
			}
		}
		if (ghosts != null){
			for (int i =0 ; i < ghosts.size(); i ++){
				int tempghostx = ghosts.get(i).gettileX();
				int tempghosty = ghosts.get(i).gettileY();

					if (tempx1 == tempghostx && tempy1 == tempghosty){
						if(p1.getswordnumber() == 0){
							if (score <= 0){
								score = 0;
							}
							JOptionPane.showMessageDialog(null, "YOU HAVE BEEN KILLED! YOUR SCORE WAS"+" "+score+".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
							newrecord();
							gsm.setState(GameStateManager.MAINMENU);
						} else{
							p1.changeswordnumber(-1);
							ghosts.remove(i);
							this.score -= 50;
						}
					}
			}
		}
		if (swords != null){
			for (int i =0 ; i < swords.size(); i ++){
				int tempSwordx = swords.get(i).gettileX();
				int tempSwordy = swords.get(i).gettileY();
				if ((tempx1 == tempSwordx && tempSwordy == tempy1)){
					swords.get(i).ChangeState();
					swords.remove(i);
					this.p1.changeswordnumber(1);
				}
			}
		}
	}
	/**
	 * adding teh keys into the game
	 * @param number
	 * @param numXtile
	 * @param numYtile
	 */
	public void addingkeystogame(int number, int numXtile, int numYtile){
		Random r = new Random();
		for (int i =0; i<number; i++){
			keyObject temp = new keyObject("key", positive(r.nextInt()) % numXtile, positive(r.nextInt()) % numYtile);
			if (! keys.contains(temp)){
				if (temp.gettileX() != this.width && temp.gettileY()!= this.width){
					keys.add(temp);
				} else {
					i--;
				}
			}else{
				i--;
			}
		}
	}
	/**
	 * adding the sword into the games
	 * @param number
	 * @param numXtile
	 * @param numYtile
	 */
	public void addingswordtogame(int number, int numXtile, int numYtile) {
		Random r = new Random();
		for (int i = 0; i < number; i++) {
			swordObject temp = new swordObject("sword", positive(r.nextInt()) % numXtile, positive(r.nextInt()) % numYtile);
			if (!swords.contains(temp)) {
				swords.add(temp);
			} else {
				i--;
			}
		}
	}

	/**
	 * adding the ghosts into the games
	 * @param number
	 * @param numXtile
	 * @param numYtile
	 */
	public void addingghoststogame(int number, int numXtile, int numYtile){
		Random r = new Random();
		for (int i =0; i<number; i++){
			Ghost temp = new Ghost("ghost", positive(r.nextInt()) % numXtile, positive(r.nextInt()) % numYtile);
			if (! ghosts.contains(temp)){
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY())){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX()-1 && temp.gettileY()==p1.gettileY()) ){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX()+1 && temp.gettileY()==p1.gettileY()) ){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY()-1) ){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY()+1)){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == this.width - 1 && temp.gettileX() == this.width - 1)){
					i--;  //make sure player will not stuck at the very beginning
				}
				else{
					ghosts.add(temp);
				}
			}else{
				i--;
			}
		}
	}
/**
 * changing between positive and negative
 * @param i
 * @return
 */
	private int positive(int i){
		if (i < 0)
			return -i;
		else 
			return i;
	}

/**
 * 	user button control
 */
	@Override
	public void MousePressed(double x, double y) {
		//System.out.println(x+" "+y);
		if (this.pauseFlag == 1){
			if (x > 525 && x < 830){
				 if (y >= 5 && y < 580){
					 this.menuselection = 0;
				 }
				 if (y >= 580 && y < 725){
					 this.menuselection = 1;
				 }
	
			}
		} else {
			if (x > 930 && x < 1230){
				if (y < 735 && y > 585){
					this.menuselection = 2;
				}
				if (y >= 735 && y < 880){
					this.menuselection = 3;
				}
			}
		}
		select();
	}


/**
 * 	user button control
 */

	@Override
	public void MouseReleased(double x, double y) {
		if(this.pauseFlag == 0){
			if (x > 930 && x < 1230){
				if (y < 735 && y > 585){
					if (this.pauseFlag == 1){
						this.pauseFlag = 0;
					} else {
						this.pauseFlag = 1;
					}
				}
			}
		}
	}

/**
 * 	user mouse button control
 */
	//adding the hover effect under the mousemoved method

	@Override
	public void MouseMoved(double x, double y) {
	//	System.out.println(x+" "+y);
		if (this.pauseFlag == 1){
			if (x > 525 && x < 830){
				 if (y >= 5 && y < 580){
					 this.menuselection = 0;
				 }
				 if (y >= 580 && y < 725){
					 this.menuselection = 1;
				 }
	
			}
		} else {
			if (x > 930 && x < 1230){
				if (y < 735 && y > 585){
					this.menuselection = 2;
				}
				if (y >= 735 && y < 880){
					this.menuselection = 3;
				}
			}
		}
	}
	/**
	 * 	user button control
	 */
	
	private void select(){
		if (this.pauseFlag == 1){
			if (this.menuselection == 0){
				this.pauseFlag = 0;
			}else if (this.menuselection == 1){
				newrecord();
				gsm.setState(GameStateManager.MAINMENU);
			}
		} else if (this.pauseFlag == 0){
			if (this.menuselection == 2){
				this.pauseFlag = 1;
			}
			if (this.menuselection == 3){
				this.m = null;
				init();
			}
		}
	}

/**
 * setting teh zoomvalue
 * @return
 */
	private int zoomvalue(){
		if (this.showtile == this.width){
			this.showtile = 5;
			return 5;
		}
		if (this.showtile ==5){
			this.showtile =10;
			return 10;
		}
		this.showtile =this.width;
		return this.width;
	}
	/**
	 * draw score board on the game board
	 * @param g
	 * @return
	 */
	private Graphics2D drawScoreboard(Graphics2D g){
	   		 String leveltitle = "Level";		
	   		 String temp3 = Integer.toString(level);		
	   		 		
	   		 String playerName1 = "Player 1";		
	   		 		
	   		 String s = "Score";		
	   		 String temp1;		
	   		 if(score > 0){		
	   		 	temp1 = Integer.toString(score);		
	   		 }else{		
	   			score = 0;		
	   			temp1 = Integer.toString(score);		
	   		 }		
	   		 		
	   		 String ss = "Steps";	
	   		 String sword = "Swords";
	   		 String temp2 = Integer.toString(p1.getSteps());	
	   		 String temp4 = Integer.toString(numberOfSword);
	   		 		
	   		 Font font = new Font("Serif", Font.BOLD, 24);		
	   		 g.setFont(font);		
	   		 g.setColor(Color.WHITE);		
	   		 g.drawString(playerName1, 1280-270, 320);		
	   		 g.drawImage(p1.getEast(), 1280-160, 275, 50,50, null);		
	   		 g.drawString(leveltitle, 1280-215, 380);		
	   		 g.drawString(temp3, 1280-192, 405);		
	   		 g.drawString(s, 1280-215, 440);		
	   		 g.drawString(temp1, 1280-205, 465);		
	   		 g.drawString(ss, 1280-215, 500);		
	   		 g.drawString(temp2, 1280-193, 525);	
	   		 g.drawString(sword, 1280-222, 550);
	   		 g.drawString(temp4, 1280-193, 575);
	   		 //repaint();		
		return g;
	}
	/**
	 * creating the new record
	 */
	private void newrecord(){
		
		JFrame test = new JFrame();
		String temp = JOptionPane.showInputDialog(test,"Enter Your Name(maxium 10 character):", "Player");
		if (temp == null || temp.length() == 0)
			return;
		String line = this.score + "#" + temp + "#" + this.level +"\n";
		BufferedWriter f = null;
		try  
		{
			
			    FileWriter fstream = new FileWriter("src/save.txt", true); //true tells to append data.
			    f = new BufferedWriter(fstream);
			    if (temp != null){
			    	f.write(line);
			    } 
		}
		catch (IOException e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		finally
		{
			try {
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
/**
 * draw the maze on the game board
 * @param g
 * @param numberoftile
 * @return
 */
	
	private Graphics2D drawnewmaze(Graphics2D g, int numberoftile){
		Image maze = this.m.getmazemap();
		Graphics2D filledmaze;
		filledmaze = (Graphics2D) maze.getGraphics();
		//draw player 1
		if (this.p1.IsAlive()){
			int tempx = this.p1.gettileX() * this.m.gettileimagelength() + 22;
			int tempy = this.p1.gettileY() * this.m.gettileimagelength() + 22;
	    	if (p1.getdirection() == 1){
	    		filledmaze.drawImage(p1.getNorth(), tempx, tempy+10, 140, 140, null);
	    	} else if (p1.getdirection() == 3){
	    		filledmaze.drawImage(p1.getSouth(), tempx, tempy+10, 140, 140, null);
	    	} else if (p1.getdirection() == 2){
	    		filledmaze.drawImage(p1.getEast(), tempx, tempy+10, 140, 140, null);
	    	} else if (p1.getdirection() == 4){
	    		filledmaze.drawImage(p1.getWest(), tempx, tempy+10, 140, 140, null);
	    	}
		}
		
		
		
		
		
	   	 //print keys
	   	if (keys != null){
		   	 for (int i =0 ; i < keys.size(); i ++){
		   		 keyObject temp = keys.get(i);
		   		 if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					filledmaze.drawImage(temp.getMap(), tempx, tempy, 150, 150, null);
		   		 }
		   	 }
	   	 }
	   	 //print ghosts
	   	 if (ghosts != null){
		   	 for (int i =0 ; i < ghosts.size(); i ++){
		   		 Ghost temp = ghosts.get(i);
		   		if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					//System.out.println(tempx + " " + tempy);
		   			if (p1.getdirection() == 1){
		   				filledmaze.drawImage(temp.getGhostNorth(), tempx, tempy, 150, 150, null);
			    	} else if (p1.getdirection() == 3){
			    		filledmaze.drawImage(temp.getGhostSouth(), tempx, tempy, 150, 150, null);
			    	}else{
			    		filledmaze.drawImage(temp.getGhostNorth(), tempx, tempy, 150, 150, null);
			    	}
		   		}
		   	 }
	   	 }
	   	 //draw sword
	   	if (swords != null){
	   	//	System.out.println(swords.size());
		   	 for (int i =0 ; i < swords.size(); i ++){
		   		swordObject temp = swords.get(i);
		   		 if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					filledmaze.drawImage(temp.getSword(), tempx, tempy, 150, 150, null);
		   		 }
		   	 }
	   	 }
	   	
	   	
	   	 
	   	if (mark != null){
		   	 for (int i =0 ; i < mark.size(); i ++){
		   		 Tracemark temp = mark.get(i);
		   		 if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					filledmaze.drawImage(temp.getMark(), tempx, tempy, 150, 150, null);
		   		 }
		   	 }
	   	 }
	   	
	   	//draw exit 
	  //print extit
	  		Image tempexit = new ImageIcon("res/Images/EndFlag.png").getImage();
	     	 filledmaze.drawImage(tempexit, this.exitX * this.m.gettileimagelength()+50, this.exitY * this.m.gettileimagelength()+40, 100,100 , null);
	   	
	   	
	   	 
	   	 
	   	 int startx, starty, endx, endy;
	   	 if (p1.gettileX() < numberoftile/2){
	   		 startx = 0;
	   		 endx = numberoftile * this.m.gettileimagelength();
	   	 }else if (p1.gettileX() + numberoftile/2 >= this.width){
	   		 endx = m.gettotalWidth();
	   		 startx = m.gettotalWidth() - numberoftile * this.m.gettileimagelength();
	   	 }else{
	   		 startx = (p1.gettileX() - numberoftile/2) * this.m.gettileimagelength();
	   		 endx = startx + numberoftile * this.m.gettileimagelength();
	   	 }
	   
	   	 if (p1.gettileY() < numberoftile/2){
	   		 starty = 0;
	   		 endy = numberoftile * this.m.gettileimagelength();
	   	 }else if (p1.gettileY() + numberoftile/2 >= this.height){
	   		 endy = m.gettotalHeight();
	   		 starty = m.gettotalHeight() - numberoftile * this.m.gettileimagelength();
	   	 }else{
	   		starty = (p1.gettileY() - numberoftile/2) * this.m.gettileimagelength();
	   		 endy = starty + numberoftile * this.m.gettileimagelength();
	   	 }
	   	 
		g.drawImage(maze, 0,0,900,900 ,startx,starty,endx,endy,null);
//		System.out.println(startx +" "+ starty +" "+ endx +" "+ endy);
		return g;
	}
	//end of class
}
