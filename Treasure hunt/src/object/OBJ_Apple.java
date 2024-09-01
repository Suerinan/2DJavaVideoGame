package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Apple extends SuperObject{
	
	GamePanel gamePanel;

	public OBJ_Apple(GamePanel gamePanel) {
		
		this.gamePanel = gamePanel;
		
		name = "Apple";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/apple_red.png"));
			uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
