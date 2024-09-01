package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{ // CONTROLS THE GAME TROUGH CREATING A FLUX OF 60 LIMITED FRAMES PER SECOND IN WICH IT CALLS CLASSES FUNCTIONS TO SET THE ACTIONS THE PROGRAM SHOULD TAKE
	// Screen settings
	final int originalTileSize = 16; // Any tile default size
	final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	//World settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	int FPS = 60;
	
	// Game system
	TileManager tileManager = new TileManager(this);	
	KeyHandler keyHandler = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	Thread gameThread;
	public UI ui = new UI(this);
	
	// Entity and objects
	public Player player = new Player(this,keyHandler);
	public SuperObject obj[] = new SuperObject[10];
	
	// Game state
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	public GamePanel(){
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // For better eficiency as it paints offsceen
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	public void setUpGame() {
		assetSetter.setObject();
		playMusic(0);
		gameState = playState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
				
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		if(gameState == playState) {
			player.update();
		}
		if(gameState == pauseState) {
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// drawing background before player so it doesn't hide player
		tileManager.draw(g2);
		
		// drawing objects
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// drawing player
		player.draw(g2);
		
		// drawing UI
		ui.draw(g2);
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSoundEffect(int i) {
		
		se.setFile(i);
		se.play();
	}
}
