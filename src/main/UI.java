package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

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
    BufferedImage heart_full, heart_half, heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue;
    public int commandNumber = 0;



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

        // Create HUD Objects
        SuperObject heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;

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

        // title state
        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }

        // play state
        if (gamePanel.gameState == gamePanel.playState) {
            drawPlayerLife();
        }

        // pause state
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // dialogue state
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gamePanel.tileSize/2;
        int y = gamePanel.tileSize/2;
        int i = 0;

        // draw max life
        while (i < gamePanel.player.maxLife / 2){
            g2d.drawImage(heart_empty, x, y, null);
            i++;
            x+= gamePanel.tileSize;
        }

        //reset
        x = gamePanel.tileSize/2;
        y = gamePanel.tileSize/2;
        i = 0;

        //draw current life
        while (i < gamePanel.player.life){
            g2d.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.player.life){
                g2d.drawImage(heart_full, x, y, null);
            }
            i++;
            x+= gamePanel.tileSize;
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

    public void drawTitleScreen(){

        g2d.setColor(new Color(0, 120, 80));
        g2d.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Title Name
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 96F));
        String text = "The Bit Game";
        int x = getXforCenterText(text);
        int y = gamePanel.tileSize * 3;

        g2d.setColor(Color.black);
        g2d.drawString(text, x+5, y+5);


        // Main Color
        g2d.setColor(Color.white);
        g2d.drawString(text, x, y);


        // Image
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
        y += gamePanel.tileSize * 1.5;
        g2d.drawImage(gamePanel.player.right1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // Menu
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenterText(text);
        y += gamePanel.tileSize * 4;
        g2d.drawString(text, x, y);
        if(commandNumber == 0){
            g2d.drawString(">", x-gamePanel.tileSize/2, y);
        }

        text = "LOAD GAME";
        x = getXforCenterText(text);
        y += gamePanel.tileSize;
        g2d.drawString(text, x, y);
        if(commandNumber == 1){
            g2d.drawString(">", x-gamePanel.tileSize/2, y);
        }

        text = "EXIT";
        x = getXforCenterText(text);
        y += gamePanel.tileSize;
        g2d.drawString(text, x, y);
        if(commandNumber == 2){
            g2d.drawString(">", x-gamePanel.tileSize/2, y);
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
