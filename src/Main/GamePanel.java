/**
 * set up JPanel, also set up multi-thread to process display, keyboard, mouse respond 
 */
package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

import GameState.GameStateManager;
/**
 * control the main thread and game logical control
 * 
 * 
 *
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel 
	implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	// dimensions
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 900;
	public static final int SCALE = 1;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	/**
	 *
	 */
	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	/**
	 * start the game thread
	 */
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}
	/**
	 * Initialize game component 
	 */
	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	/**
	 * logic control of the game 
	 */
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
	//		update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * display graphics
	 */
	private void draw() {	
		gsm.draw(g);
	}
	/**
	 * Initialize graphic setting
	 */
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
				WIDTH * SCALE, HEIGHT * SCALE,
				null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	/**
	 * game button control main screen
	 * @param key
	 */
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	/**
	 * game button control main screen
	 * @param key 
	 */
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	
	/**
	 * game button control main screen
	 * @param key
	 */
	 @Override
	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);
		
	}

	/**
	 * game button control main screen
	 * @param key
	 */
	 @Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
		
	}
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	/**
	 * game button control main screen
	 * @param key
	 */
	 @Override
	public void mouseMoved(MouseEvent e) {
		gsm.MouseMoved(e);
		
	}


}
















