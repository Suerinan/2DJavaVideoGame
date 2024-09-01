package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{ // JUST THE PLAYABLE CHARACTER STUFF
	
	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
		screenY = gamePanel.screenHeight / 2- (gamePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gamePanel.tileSize * 5 / 2;
		worldY = gamePanel.tileSize * 1;
		speed = 2;
		direction = "down";
	}
	
	public void getPlayerImage() {
		up1 = setup("eish_up_1");
		up2 = setup("eish_up_2");
		down1 = setup("eish_down_1");
		down2 = setup("eish_down_2");
		right1 = setup("eish_right_1");
		right2 = setup("eish_right_2");
		left1 = setup("eish_left_1");
		left2 = setup("eish_left_2");
		standing1 = setup("eish_standing_1");
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void update() {
		if(keyHandler.upPressed == true || keyHandler.downPressed == true 
		|| keyHandler.leftPressed == true || keyHandler.rightPressed == true){
			
			if(keyHandler.upPressed == true) {
				direction = "up";
			}
			else if(keyHandler.downPressed == true) {
				direction = "down";
			}
			else if(keyHandler.leftPressed == true) {
				direction = "left";
			}
			else if(keyHandler.rightPressed == true) {
				direction = "right";
			}
			
			// Check tile collision
			collisionOn = false;
			gamePanel.collisionChecker.checkTile(this);
			
			// Check object collision
			int objIndex = gamePanel.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNumber == 1) {
					spriteNumber = 2;
				}
				else if(spriteNumber == 2) {
					spriteNumber = 1;
				}
				spriteCounter = 0;
			}
		}else {
			direction = "standing";
		}
	}
	
	public void pickUpObject(int index) {
		
		if(index != 999) {
			
			String objectName = gamePanel.obj[index].name;
			
			switch(objectName) {
				case "Apple":
					gamePanel.obj[index] = null;
					break;
				case "Key":
					gamePanel.playSoundEffect(1);
					hasKey++;
					gamePanel.obj[index] = null;
					gamePanel.ui.showMessage("You got a key!");
					break;
				case "Door":
					if(hasKey > 0) {
						gamePanel.playSoundEffect(4);
						gamePanel.obj[index] = null;
						hasKey--;
						gamePanel.ui.showMessage("A door has been opened...");
					}else {
						gamePanel.ui.showMessage("A mysterious key is needed");
					}
					break;
				case "Chest":
					gamePanel.obj[index] = null;
					gamePanel.ui.gameFinished = true;
					gamePanel.stopMusic();
					gamePanel.playSoundEffect(2);
					break;
				case "Boots":
					gamePanel.playSoundEffect(3);
					speed+=2;
					gamePanel.obj[index] = null;
					gamePanel.ui.showMessage("Speed boosted");
					break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNumber == 1) {
				image = up1;
			}
			if(spriteNumber == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNumber == 1) {
				image = down1;
			}
			if(spriteNumber == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNumber == 1) {
				image = left1;
			}
			if(spriteNumber == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNumber == 1) {
				image = right1;
			}
			if(spriteNumber == 2) {
				image = right2;
			}
			break;
		case "standing":
			image = standing1;
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}
