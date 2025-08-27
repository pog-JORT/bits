package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
//    public int keys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize/2;
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize/2;

        bounds = new Rectangle(8, 16, 32, 24);
        boundsDefaultX = bounds.x;
        boundsDefaultY = bounds.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY= gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        up1 = setup("player_up_01");
        up2 = setup("player_up_02");
        down1 = setup("player_down_01");
        down2 = setup("player_down_02");
        left1 = setup("player_left_01");
        left2 = setup("player_left_02");
        right1 = setup("player_right_01");
        right2 = setup("player_right_02");

    }

    public BufferedImage setup(String imageName){
        UtilityTool utilTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/player/" +imageName + ".png"));
            image = utilTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);

        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed) {

            // sprite animation
            spriteCounter++;
            if (spriteCounter > 20) {
                spriteNumber = (spriteNumber % 2) + 1;
                spriteCounter = 0;
            }

            // check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // check object collision
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npcs);
            interactNPC(npcIndex);

            // if !collisionOn player can move
            if(!collisionOn){
                switch(direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // movement & direction
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }
        }
    }

    public void pickUpObject(int index){
        if(index != 999){
        }
    }

    public void interactNPC(int index){
        if(index != 999){
            if(gamePanel.keyHandler.enterPressed){
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npcs[index].speak();
            }
        }
        gamePanel.keyHandler.enterPressed = false;
    }

}
