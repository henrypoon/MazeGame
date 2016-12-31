/**
 * Each page of the game stand for one state, main menu, history, maze game etc.
 * 
 * each state has their own keyboard or mouse listener, can check how does it do on the game board class
 *  
 */
package GameState;

import java.awt.event.MouseEvent;
/**
 * game system control 
 * hold up the entire game
 */
public class GameStateManager {
	
	private GameState[] gameStates;
	private int playStyle = 0;
	private int currentState;
	private int startLevel = 1;
	public static final int NUMGAMESTATES = 5;
	public static final int MAINMENU = 0;
	public static final int SINGLEPLAYER = 1;
	public static final int MULTIPLAYER = 2;
	public static final int DIFFICULTY = 3;
	public static final int HISTORY = 4;

	/**
	 * Constructor:
	 * display main menu
	 */
	public GameStateManager() {
		
		gameStates = new GameState[NUMGAMESTATES];
		
		currentState = MAINMENU;
		loadState(currentState);
		
	}
	/**
	 * change game state for options
	 * @param state  GameState reference number
	 */
	private void loadState(int state) {
		if(state == MAINMENU
				)
			gameStates[state] = new MainMenu(this);
		if(state == SINGLEPLAYER){
			//gameStates[state] = new GameBoard(1,this.startLevel,this);
			this.playStyle = 1;
			gameStates[state] = new SinglePlayer(this.startLevel,this);
		} 	
		if(state == MULTIPLAYER){
			this.playStyle = 2;
			gameStates[state] = new MultiPlayer(this.startLevel,this);
		} 
		if(state == HISTORY){
			gameStates[state] = new History(this);
		}
		if(state == DIFFICULTY){
			gameStates[state] = new Difficulty(this);
		}
	}
	/**
	 * remove the existing GameState Object
	 * @param state GameState reference number
	 */	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	/**
	 * acting containing unload and load GameState
	 * @param state GameState reference number
	 */
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
/*	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	/**
	 * set game game difficulty level
	 * @param sl game level
	 */

public void setStartLevel(int sl){
		this.startLevel = sl;
	}
	/**
	 * get the current game difficulty level
	 * @return integer game level
	 */
	public int getStartLevel(){
		return this.startLevel;
	}
	/**
	 * set number of player for game 
	 * @param sl number of player(s) in game (1 or 2)
	 */
		public void setPlayStyle(int sl){
		this.playStyle = sl;
	}
	/**
	 * get number of player  for game 
	 * @return number or player(s)(1 or 2)
	 */
	public int getPlayStyle(){
		return this.playStyle;
	}

	
	
	
	/**
	 * draw game screen
	 * @param g current graphics
	 */
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	/**
	 * user control
	 * @param k keypress value
	 */
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	/**
	 * user control
	 * @param k keypress value
	 */
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	/**
	 * user control
	 * @param e MouseEvent value
	 */
	public void mousePressed(MouseEvent e) {
		gameStates[currentState].MousePressed(e.getX(),e.getY());
	}
	/**
	 * user control
	 * @param e MouseEvent value
	 */
	public void mouseReleased(MouseEvent e) {
		gameStates[currentState].MouseReleased(e.getX(),e.getY());
		
	}
	/**
	 * user control
	 * @param e MouseEvent value
	 */
	public void MouseMoved(MouseEvent e){
		gameStates[currentState].MouseMoved(e.getX(),e.getY());
	}
	
}









