package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
    
    GamePanel gamePanel;
    Font arial_40, arial_80_bold;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;    
    double playTime;
    
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_80_bold = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gamePanel);
        keyImage = key.image;
    }
    
    public void showMessage(String msg) {
        this.message = msg;
        messageOn = true;
    }
    
    public void draw(Graphics2D g2) {
        
        if (gameFinished) {
            
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            
            String text;
            int textLength;
            int x;
            int y;
            
            text = "You found the treasure!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gamePanel.screenWidth - textLength) / 2;
            y = gamePanel.screenHeight / 2 - (gamePanel.tileSize * 3);
            g2.drawString(text, x, y);
            
            int minutes = (int) playTime / 60;
            int seconds = (int) playTime % 60;
            String formattedTime = String.format("%02d:%02d", minutes, seconds);
            text = "Your time is: " + formattedTime + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gamePanel.screenWidth - textLength) / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 4);
            g2.drawString(text, x, y);
            
            g2.setFont(arial_80_bold);
            g2.setColor(Color.MAGENTA);
            text = "Congratulations!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gamePanel.screenWidth - textLength) / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
            g2.drawString(text, x, y);

            gamePanel.gameThread = null;
            
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.drawString("x " + gamePanel.player.hasKey, 74, 65);
            
            playTime += (double) 1 / 60;
            int minutes = (int) playTime / 60;
            int seconds = (int) playTime % 60;
            String formattedTime = String.format("%02d:%02d", minutes, seconds);
            g2.drawString("Time: " + formattedTime, gamePanel.tileSize * 11, 65);
            
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gamePanel.tileSize / 2, gamePanel.screenHeight / 2);
                messageCounter++;
                if (messageCounter > 60) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
