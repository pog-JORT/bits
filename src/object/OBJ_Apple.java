package object;


import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Apple extends SuperObject {

    GamePanel gamePanel;


    public OBJ_Apple(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Apple";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/apple.png")));
            utilTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
