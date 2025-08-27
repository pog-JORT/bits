package main;

import entity.NPC_Girl;
import object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

    }

    public void setObject() {


    }

    public void setNPC(){
        gamePanel.npcs[0] = new NPC_Girl(gamePanel);
        gamePanel.npcs[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npcs[0].worldY = gamePanel.tileSize * 21;
    }

}
