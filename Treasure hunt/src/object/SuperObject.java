package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		if(worldX > gamePanel.player.worldX - gamePanel.player.screenX - gamePanel.tileSize && 
		   worldX < gamePanel.player.worldX + gamePanel.player.screenX + gamePanel.tileSize &&
		   worldY > gamePanel.player.worldY - gamePanel.player.screenY - gamePanel.tileSize && 
		   worldY < gamePanel.player.worldY + gamePanel.player.screenY + gamePanel.tileSize) {
			
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
		}
		
	}
}
