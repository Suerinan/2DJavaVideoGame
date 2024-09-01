package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter { // PLACING OBJECTS
	
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		
		gamePanel.obj[0] = new OBJ_Chest(gamePanel);
		gamePanel.obj[0].worldX = 39 * gamePanel.tileSize;
		gamePanel.obj[0].worldY = 8 * gamePanel.tileSize;
		
		gamePanel.obj[2] = new OBJ_Door(gamePanel);
		gamePanel.obj[2].worldX = 36 * gamePanel.tileSize;
		gamePanel.obj[2].worldY = 11 * gamePanel.tileSize;
		
		gamePanel.obj[3] = new OBJ_Key(gamePanel);
		gamePanel.obj[3].worldX = 42 * gamePanel.tileSize;
		gamePanel.obj[3].worldY = 11 * gamePanel.tileSize;
		
		gamePanel.obj[4] = new OBJ_Boots(gamePanel);
		gamePanel.obj[4].worldX = 17 * gamePanel.tileSize;
		gamePanel.obj[4].worldY = (15 * gamePanel.tileSize)/2;
	}
}
