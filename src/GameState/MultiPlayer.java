/**
 * generate multiplayer game
 * 
 * 
 */

package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import Maze.Ghost;
import Maze.Maze;
import Maze.Player;
import Maze.Tracemark;
import Maze.keyObject;
import Maze.swordObject;


public class MultiPlayer extends GameState implements ActionListener{
	private Maze m;
	private Player p1;
	private Player p2;
	private List<keyObject> keys; 
	private List<Ghost> ghosts; 
	private List<swordObject> swords;
	private List<Tracemark> mark1;
	private List<Tracemark> mark2;
	private int keyNum = 0;
	private int ghostNum = 0;
	private int level = 0;
	private int pauseFlag = 0;
	private int menuselection = 0;
	private int height = 4;
	private int width = 4;
	private int showtile = width;
	private int numberOfSword1 = 0;
	private int numberOfSword2 = 0;
	private int exitX;
	private int exitY;
	private int noticeFlag = 0;
	
	/**
	 *Constructor:
	 * @param startLevel game level
	 * @param gsm current GameStateManager
	 */
	public MultiPlayer(int startLevel,GameStateManager gsm){
		
		this.gsm = gsm;
		this.level = startLevel;
		init();
	}
	

	

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	/**
	 *initialize game 
	 */
	public void init(){
		tips();
		MPlevelGenerator();
		swords = new ArrayList<swordObject>();
		keys = new ArrayList<keyObject>();
		ghosts = new ArrayList<Ghost>();
		mark1 = new ArrayList<Tracemark>();
		mark2 = new ArrayList<Tracemark>();
		this.showtile = width;
		m = new Maze(height,width, this.level);
		p1= new Player("human",this.numberOfSword1);
        p2 = new Player("human",this.numberOfSword2);
        p2.changeP2Icon();
		Random ran = new Random();
		int x1 = ran.nextInt(this.width);
		int y1 = ran.nextInt(this.width);
		int x2 = ran.nextInt(this.width);
		int y2 = ran.nextInt(this.width);
		p1.settileX(x1);
        p1.settileY(y1);
        p2.settileX(x2);
        p2.settileY(y2);
    	addingkeystogame  (this.keyNum,width,height); // first param is number 
		addingghoststogame(this.ghostNum,width,height);// first param is number
		
		this.exitX = ran.nextInt(this.width);
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
        	}else{// 5
        		addingswordtogame(5,width,height);
        	}
        	//System.out.println("Adding keys");
        }
		//System.out.println(this.exitX+" "+this.exitY);
		
	}
	
	
	/**
	 *auto generate game level
	 */
	private void MPlevelGenerator(){
		if (level < 6){
			this.width = 4 + (level-1)*3;
			this.height = 4 + (level-1)*3;
		} else {
			this.width = 14 + level;
			this.height = 14 + level;
		}
	
		
		if (3 < level && level < 6){
			this.keyNum = this.level + 2;
			
		}
		if (6 <= level && level < 10){
			this.ghostNum = this.level;
			this.keyNum = 8;
		}
		if (level >= 10){
			this.ghostNum = this.level ;
			this.keyNum = this.level - 2;
		}
	}
	
	/**
	 * pop out game instruction
	 */
	private void tips(){
		if (level == 1){
			String s = 
"Controls:"
	+ "\n\n\n"
	+ ""
	+ "W-A-S-D | Player 1 Movement\n"
	+ "I-J-K-L   | Player 2 Movement\n"
	+ "E              | Player 1 Place tracer / Pick up tracer\n"
	+ "O              | Player 2 Place tracer / Pick up tracer\n"
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
	+ "Use this knowledge to your advantage as you encounter one in your path.\n\n"
	+ "In this multiplayer game mode, you and your friend are to work together to solve the maze\n"
	+ "Remember that only one person has to escape the level for both of you to beat it!";
        	JOptionPane.showMessageDialog(null, s, "Instruction", JOptionPane.INFORMATION_MESSAGE);
        }
		

	}
	
	/**
	 * draw game on JFrame
	 *@param g current graphics
	 *@return g
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

		g = drawnewmaze(g,showtile);
	// fill right-hand-side 
		g.setColor(new Color(123, 140, 160));
		g.fillRect(900, 0, 380, 900);
		
	
	// draw score board
		g =drawScoreboard(g);
		
		//draw button
		img = new ImageIcon("res/Images/Pause.png");
		graph = img.getImage();
		g.drawImage(graph, 960, 600, 255, 120, null);	
		
		img = new ImageIcon("res/Images/NewMaze.png");
		graph = img.getImage();
		g.drawImage(graph, 960, 750, 255, 120, null);	
	   	 
	   	 if (this.pauseFlag == 1){
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
	
	
	
	@Override
	/**
	 * player controls
	 */
	public void keyPressed(int k) {
		Ghost temp;

		p1.setDx(0);
		p1.setDy(0);
		p2.setDx(0);
		p2.setDy(0);
	
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
			
			if (k == KeyEvent.VK_I){
				p2.setDy(-1);
				p2.setDirection(1);
			}
			if (k == KeyEvent.VK_K){
				p2.setDy(1);
				p2.setDirection(3);
			}
			if (k == KeyEvent.VK_J){
				p2.setDx(-1);
				p2.setDirection(4);
			}
			if (k == KeyEvent.VK_L){
				p2.setDx(1);
				p2.setDirection(2);
			}
			
			
			if (k == KeyEvent.VK_E){
				int f = 0;
				if (mark1!= null){
					for (Tracemark tm : mark1){
						if ( tm.gettileX() ==p1.gettileX() && p1.gettileY()== tm.gettileY() ){
							mark1.remove(tm);
							f = 1;
							break;
						}
					}
				}
				if (f==0){
					mark1.add( new Tracemark("mark", p1.gettileX(), p1.gettileY()));
				}
			}
			
			
			
			if (k == KeyEvent.VK_O){
				int f = 0;
				if (mark2!= null){
					for (Tracemark tm : mark2){
						if ( tm.gettileX() ==p2.gettileX() && p2.gettileY()== tm.gettileY() ){
							mark2.remove(tm);
							f = 1;
							break;
						}
					}
				}
				if (f==0){
					mark2.add( new Tracemark("mark", p2.gettileX(), p2.gettileY()));
				}
			}
			
			
			
			

			collisionCheck();
	        if (p1.IsAlive() && ! m.ifwallbyint(p1.gettileX(), p1.gettileY(), p1.getdirection())) {
	            p1.move();
	        }
	        if (p2.IsAlive() && ! m.ifwallbyint(p2.gettileX(), p2.gettileY(), p2.getdirection())) {
	            p2.move();
	        }
	        //mainly test for ghost 
	        
		   	 if (ghosts != null){

		   			for (int i =0 ; i < ghosts.size()/2; i ++){
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
		   			for (int i = ghosts.size()/2 ; i < ghosts.size(); i ++){
				   		 temp = ghosts.get(i);
			   			 temp.setDx(0);
			   			 temp.setDy(0);
							if (k == KeyEvent.VK_I){
								temp.setDy(-1);
								temp.setDirection(1);
							}
							if (k == KeyEvent.VK_K){
								temp.setDy(1);
								temp.setDirection(3);
							}
							if (k == KeyEvent.VK_J){
								temp.setDx(-1);
								temp.setDirection(4);
							}
							if (k == KeyEvent.VK_L){
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
			   	 
			   	 
		   	 }
		        //test for GameObject collisions
	        collisionCheck();
		        
		        
		        //end point,right bottom corner
	        if (p1.gettileX() != this.exitX && p2.gettileX() != this.exitX
	        		&& p1.gettileX() != this.exitY && p2.gettileY() != this.exitY)
	       this.noticeFlag = 0; 
	       
	        
	        
	        if (keys.isEmpty()){//all the keys need to be get before going into exit
	        		if(p1.gettileX() == exitX && p1.gettileY()==exitY){
			            JOptionPane.showMessageDialog(null, "Congratulations, Player1 beaten the level!", "End Game", JOptionPane.INFORMATION_MESSAGE);
			        	level++;
			        	this.numberOfSword1 = p1.getswordnumber();
			        	this.numberOfSword2 = p2.getswordnumber();
			        	this.m = null;
			            init();
			        }
			        if(p2.gettileX() == exitX && p2.gettileY()== exitY){
			            JOptionPane.showMessageDialog(null, "Congratulations, Player2 beaten the level!", "End Game", JOptionPane.INFORMATION_MESSAGE);
			            level++;
			        	this.numberOfSword1 = p1.getswordnumber();
			        	this.numberOfSword2 = p2.getswordnumber();
			            this.m = null;
			            init();
			        }
	        } else {
	        	     	
	        	
	        	
	 
	        	if(p1.gettileX() == exitX && p1.gettileY()== exitY && this.noticeFlag == 0){
	        		JOptionPane.showMessageDialog(null, "You have to collect all the keys to clear the level", "Notice", JOptionPane.INFORMATION_MESSAGE);
	        		this.noticeFlag = 1;
	        	}
	        	if(p2.gettileX() == exitX && p2.gettileY()== exitY && this.noticeFlag == 0){
	        		JOptionPane.showMessageDialog(null, "You have to collection all the keys to clear the level", "Notice", JOptionPane.INFORMATION_MESSAGE);
	        		this.noticeFlag = 1;
	        	}
	        }		        
		
	}
	
	
	@Override
	/**
	 *player controls
	 *@param k keyboard value
	 */
	public void keyReleased(int k) {
		p1.setDx(0);
		p1.setDy(0);
		p2.setDx(0);
		p2.setDy(0);
		 if (ghosts != null){
		   	 for (int i =0 ; i < ghosts.size(); i ++){
					ghosts.get(i).setDx(0);
					ghosts.get(i).setDy(0);
		   	 }
		 }
		
	}
	/**
	 * check if ghost object standing on the same tile
	 *
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
	 * check if game object collision
	 *
	 */
			
	private void collisionCheck(){
		int tempx1 = p1.gettileX();
		int tempy1 = p1.gettileY();
		int tempx2 = p2.gettileX();
		int tempy2 = p2.gettileY();
		//collision of players and keys
		if (keys != null){
			for (int i =0 ; i < keys.size(); i ++){
				int tempkeyx = keys.get(i).gettileX();
				int tempkeyy = keys.get(i).gettileY();
				if ((tempx1 == tempkeyx && tempkeyy == tempy1)||(tempx2 == tempkeyx && tempkeyy == tempy2)){
					keys.get(i).ChangeState();
					keys.remove(i);
				}
			}
		}
		if (ghosts != null){
			for (int i =0 ; i < ghosts.size(); i ++){
				int tempghostx = ghosts.get(i).gettileX();
				int tempghosty = ghosts.get(i).gettileY();

				
				if (tempx1 == tempghostx && tempy1 == tempghosty){
					if(p1.getswordnumber() == 0){
						JOptionPane.showMessageDialog(null, "PLAYER1 HAVE BEEN KILLED!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
						gsm.setState(GameStateManager.MAINMENU);
					} else{
						p1.changeswordnumber(-1);
						ghosts.remove(i);
					}
				}
				if (tempx2 == tempghostx && tempy2 == tempghosty){
					if(p2.getswordnumber() == 0){
						JOptionPane.showMessageDialog(null, "PLAYER2 HAVE BEEN KILLED!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
						gsm.setState(GameStateManager.MAINMENU);
					} else{
						p2.changeswordnumber(-1);
						ghosts.remove(i);
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
				if ((tempx2 == tempSwordx && tempSwordy== tempy2)){
					swords.get(i).ChangeState();
					swords.remove(i);
					this.p2.changeswordnumber(1);
				}
			}
		}
	}
	/**
	 *auto generate keys Object to game
	 *@param number number of key to be add
	 *@param numXtile total tile x-
	 *@param numYtile total tile
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
	 *auto generate swords Object to game
	 *@param number number of key to be add
	 *@param numXtile total tile x-
	 *@param numYtile total tile
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
	 *auto generate ghosts Object to game
	 *@param number number of key to be add
	 *@param numXtile total tile x-
	 *@param numYtile total tile
	 */
	
	public void addingghoststogame(int number, int numXtile, int numYtile){
		Random r = new Random();
		for (int i =0; i<number; i++){
			Ghost temp = new Ghost("ghost", positive(r.nextInt()) % numXtile, positive(r.nextInt()) % numYtile);
			if (! ghosts.contains(temp)){
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY()) || (temp.gettileX() == p2.gettileX() && temp.gettileY()== p2.gettileY())){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX()-1 && temp.gettileY()==p1.gettileY()) || (temp.gettileX() == p2.gettileX()-1 && temp.gettileY()== p2.gettileY())){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX()+1 && temp.gettileY()==p1.gettileY()) || (temp.gettileX() == p2.gettileX()+1 && temp.gettileY()== p2.gettileY())){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY()-1) || (temp.gettileX() == p2.gettileX() && temp.gettileY()== p2.gettileY()-1)){
					i--;  //make sure player will not stuck at the very beginning
				}
				if ((temp.gettileX() == p1.gettileX() && temp.gettileY()==p1.gettileY()+1) || (temp.gettileX() == p2.gettileX()+1 && temp.gettileY()== p2.gettileY()+1)){
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
  *change negative int to positive
  */
	private int positive(int i){
		if (i < 0)
			return -i;
		else 
			return i;
	}


	@Override
	/**
	 *user controls
	 */
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




	@Override
	/**
	 *user controls
	 */
	public void MouseReleased(double x, double y) {
		if(this.pauseFlag == 0){
			if (x > 930 && x < 12300){
				if (y < 735 && y > 585){
					if (this.pauseFlag == 1){
						this.pauseFlag = 0;
					} else {
						this.pauseFlag = 1;
					}
				}
				if (y >= 715 && y < 755){
					//init();
			
				}
			}
		}
	}


	//adding the hover effect under the mousemoved method

	@Override
	/**
	 *user controls
	 */
	public void MouseMoved(double x, double y) {
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
	 *menu controls
	 */
	private void select(){
		if (this.pauseFlag == 1){
			if (this.menuselection == 0){
				this.pauseFlag = 0;
			}else if (this.menuselection == 1){
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
	 *display scoreboard
	 *@param g current graphics
	 * @return g
	 */
	private Graphics2D drawScoreboard(Graphics2D g){
	
	   		String leveltitle = "Level";		
	   		String temp1 = Integer.toString(p1.getswordnumber());
	   		String temp2 = Integer.toString(p2.getswordnumber());
	   		String temp3 = Integer.toString(level);		
	   				
	   		String playerName1 = "Player 1";		
	   		String playerName2 = "Player 2";	
	   		String numOfS1 = "Swords(Player1)";
	   		String numOfS2 = "Swords(Player2)";
	   				
	   		Font font = new Font("Serif", Font.BOLD, 24);		
	   		g.setFont(font);		
	   		g.setColor(Color.WHITE);		
	   		g.drawString(playerName1, 1280-270, 320);		
	   		g.drawImage(p1.getEast(), 1280-160, 295, 50,50, null);		
	   		g.drawString(playerName2, 1280-270, 370);		
	   		g.drawImage(p2.getEast(), 1280-160, 345, 50,50, null);		
	   		g.drawString(leveltitle, 1280-220, 440);		
	   		g.drawString(temp3, 1280-197, 465);	
	   		g.drawString(numOfS1, 1280-272, 490);
	   		g.drawString(temp1, 1280-197, 520);
	   		g.drawString(numOfS2, 1280-272, 550);
	   		g.drawString(temp2, 1280-197, 580);
		return g;
	}
	/**
	 *display maze
	 *@param g current graphics
	 * @return g
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
		
		//draw player 2
		if (this.p2.IsAlive()){
			int tempx = this.p2.gettileX() * this.m.gettileimagelength() + 22;
			int tempy = this.p2.gettileY() * this.m.gettileimagelength() + 22;
	    	if (p2.getdirection() == 1){
	    		filledmaze.drawImage(p2.getNorth(), tempx, tempy+10, 140, 140, null);
	    	} else if (p2.getdirection() == 3){
	    		filledmaze.drawImage(p2.getSouth(), tempx, tempy+10, 140, 140, null);
	    	} else if (p2.getdirection() == 2){
	    		filledmaze.drawImage(p2.getEast(), tempx, tempy+10, 140, 140, null);
	    	} else if (p2.getdirection() == 4){
	    		filledmaze.drawImage(p2.getWest(), tempx, tempy+10, 140, 140, null);
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
	   	 
	   	 //drawsword
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
	   	
  		Image tempexit = new ImageIcon("res/Images/EndFlag.png").getImage();
 	 	filledmaze.drawImage(tempexit, this.exitX * this.m.gettileimagelength()+50, this.exitY * this.m.gettileimagelength()+40, 100, 100, null);
	
	
	   	
	   	
	   	if (mark1 != null){
		   	 for (int i =0 ; i < mark1.size(); i ++){
		   		 Tracemark temp = mark1.get(i);
		   		 if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					filledmaze.drawImage(temp.getMark(), tempx, tempy, 150, 150, null);
		   		 }
		   	 }
	   	 }
	   	
	   	if (mark2 != null){
		   	 for (int i =0 ; i < mark2.size(); i ++){
		   		 Tracemark temp = mark2.get(i);
		   		 if (temp.IsAlive()){
		 			int tempx = temp.gettileX() * this.m.gettileimagelength() + 22;
					int tempy = temp.gettileY() * this.m.gettileimagelength() + 22;
					filledmaze.drawImage(temp.getMark(), tempx, tempy, 150, 150, null);
		   		 }
		   	 }
	   	 }
	   	 
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
