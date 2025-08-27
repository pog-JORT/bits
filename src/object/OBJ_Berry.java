package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Berry extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Berry(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Berry";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/berry.png")));
            utilTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize); // assign if needed
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
