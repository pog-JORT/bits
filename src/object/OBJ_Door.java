package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        collision = true;
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            utilTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
