package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {	// GET TILE IMAGES, READS MAP FILE AND LOADS ITS INFORMATION, DRAWS THE MAP (JUST RENDRES VISIBLE PART)

	GamePanel gamePanel;
	public Tile[] tile;
	public int mapTileNumber[][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[10];
		mapTileNumber = new int [gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		getTileImage();
		loadMap("/maps/finalMap-v1.txt");
	}
	
	public void getTileImage() {
		setup(0,"grass_1", false);
		setup(1,"grey_wall", true);
		setup(2,"water_1", true);
		setup(3,"earth_1", false);
		setup(4,"sand_1", false);
		setup(5,"tree_basic", true);
		setup(6,"tree_apple", true);
		setup(7,"tree_orange", true);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
			tile[index].collision = collision;
			
		}catch(Exception e) {
			
		}
	}
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldCol) {
				String line = br.readLine();
				while(col < gamePanel.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					mapTileNumber[col][row] = num;
					col++;
				}
				if(col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldColumn = 0;
		int worldRow = 0;
		
		while(worldColumn < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			
			int tileNum = mapTileNumber[worldColumn][worldRow];
			
			int worldX = worldColumn*gamePanel.tileSize;
			int worldY = worldRow*gamePanel.tileSize;
			int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
			
			if(worldX > gamePanel.player.worldX - gamePanel.player.screenX - gamePanel.tileSize && 
			   worldX < gamePanel.player.worldX + gamePanel.player.screenX + gamePanel.tileSize &&
			   worldY > gamePanel.player.worldY - gamePanel.player.screenY - gamePanel.tileSize && 
			   worldY < gamePanel.player.worldY + gamePanel.player.screenY + gamePanel.tileSize) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldColumn++;
			
			if(worldColumn == gamePanel.maxWorldCol) {
				worldColumn = 0;
				worldRow++;
			}
		}
	}
}
