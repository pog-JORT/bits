package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gamePanel;

    public int worldX, worldY, speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle bounds = new Rectangle(0, 0, 48, 48);
    public int boundsDefaultX, boundsDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter;
    public String[] dialogues = new String[20];
    public int dialogueIndex = 0;



    public Entity(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void setAction(){}
    public void speak(){
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        } else {
            gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
        }

        switch(gamePanel.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update(){

        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayer(this);


        if(!collisionOn){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // sprite animation
        spriteCounter++;
        if (spriteCounter > 20) {
            spriteNumber = (spriteNumber % 2) + 1;
            spriteCounter = 0;
        }

    };


    public void draw(Graphics2D g2d){

        BufferedImage image = null;


        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            switch(direction) {
                case "up":
                    if (spriteNumber == 1) {
                        image = up1;
                    } else if (spriteNumber == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        image = down1;
                    } else if (spriteNumber == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNumber == 1) {
                        image = left1;
                    } else if (spriteNumber == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        image = right1;
                    } else if (spriteNumber == 2) {
                        image = right2;
                    }
                    break;
            }

            g2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }





    public BufferedImage setup(String imageName){
        UtilityTool utilTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/NPC/" + imageName + ".png"));
            image = utilTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
