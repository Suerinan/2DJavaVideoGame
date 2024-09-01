package tile;

import java.awt.image.BufferedImage;

public class Tile { // JUST OBJECT TILE
	
	public BufferedImage image;
	public boolean collision = false;
	
	public void setCollision(boolean b) {
		this.collision = b;
	}
}
