package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    Graphics2D g2d;
    Font dialogue01;
//    BufferedImage keyIcon;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;



    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        InputStream inputStream = getClass().getResourceAsStream("/font/dialogue_01.ttf");
        try {
            dialogue01 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } {

        }

//        OBJ_Key key = new OBJ_Key(gamePanel);
//        keyIcon = key.image;
    }

    public void showMessage(String message) {
        this.message = message;
        messageOn = true;
    }


    public void draw(Graphics2D g2d) {
        this.g2d = g2d;

        g2d.setFont(dialogue01);
        g2d.setColor(Color.white);


        // play state
        if (gamePanel.gameState == gamePanel.playState) {
            // do playState stuff later
        }

        // pause state
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }

        // dialogue state
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen(){

        String text = "PAUSED";

        int x = getXforCenterText(text);
        int y = gamePanel.screenHeight/2;
        g2d.drawString(text, x, y);

    }

    public void drawDialogueScreen(){

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 32F));

        // Window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize * 8;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 3;

        drawSubWindow(x, y, width, height);

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for(String line: currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += 40;

        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(20, 16, 19, 210);

        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, gamePanel.tileSize / 2, gamePanel.tileSize / 2);

        color = new Color(255, 255, 255);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x, y, width, height, gamePanel.tileSize / 2, gamePanel.tileSize / 2);

    }

    public int getXforCenterText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gamePanel.screenWidth/2 - length/2;
        return x;
    }

}
