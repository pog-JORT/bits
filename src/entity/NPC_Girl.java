package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_Girl extends Entity{

    public NPC_Girl(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;


        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("girl_up_01");
        up2 = setup("girl_up_02");
        down1 = setup("girl_down_01");
        down2 = setup("girl_down_02");
        left1 = setup("girl_left_01");
        left2 = setup("girl_left_02");
        right1 = setup("girl_right_01");
        right2 = setup("girl_right_02");
    }

    public void setAction(){

        actionLockCounter ++;
        Random random = new Random();
        if (actionLockCounter == 120){
            int i = random.nextInt(1,100) ;
            if(i<=25){
                direction = "up";
            } else if(i > 25 && i<=50){
                direction = "down";
            }  else if(i > 50 && i<=75){
                direction = "left";
            }  else {
                direction = "right";
            }
            actionLockCounter = 0;

        }

    }

    public void speak(){
        super.speak();
    }

    public void setDialogue(){
        dialogues[0] = "Hey, help!";
        dialogues[1] = "Theres a spider!!";
        dialogues[2] = "It's really big!";
        dialogues[3] = "EEEEEEKKK!";
    }


}
