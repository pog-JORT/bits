package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTileNumber;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[50];
        mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/maps/map02.txt");
    }

    public void getTileImage(){
            makeTile(0,"grass00", false);
            makeTile(1,"grass00", false);
            makeTile(2,"grass00", false);
            makeTile(3,"grass00", false);
            makeTile(4,"grass00", false);
            makeTile(5,"grass00", false);
            makeTile(6,"grass00", false);
            makeTile(7,"grass00", false);
            makeTile(8,"grass00", false);
            makeTile(9,"grass00", false);
            makeTile(10,"grass00", false);
            makeTile(11,"grass01", false);
            makeTile(12,"water00", true);
            makeTile(13,"water01", true);
            makeTile(14,"water02", true);
            makeTile(15,"water03", true);
            makeTile(16,"water04", true);
            makeTile(17,"water05", true);
            makeTile(18,"water06", true);
            makeTile(19,"water07", true);
            makeTile(20,"water08", true);
            makeTile(21,"water09", true);
            makeTile(22,"water10", true);
            makeTile(23,"water11", true);
            makeTile(24,"water12", true);
            makeTile(25,"water13", true);
            makeTile(26, "sand00", false);
            makeTile(27, "sand02", false);
            makeTile(28, "sand03", false);
            makeTile(29, "sand04", false);
            makeTile(30, "sand05", false);
            makeTile(31, "sand06", false);
            makeTile(32, "sand07", false);
            makeTile(33, "sand08", false);
            makeTile(34, "sand09", false);
            makeTile(35, "sand10", false);
            makeTile(36, "sand11", false);
            makeTile(37, "sand12", false);
            makeTile(38, "sand13", false);
            makeTile(39, "dirt01", false);
            makeTile(40, "stone_brick", true);
            makeTile(41, "tree", true);
            makeTile(42, "tree_fruit", true);





    }

    public void makeTile(int index, String imagePath, boolean collision) {
        UtilityTool utilTool = new UtilityTool();
        try{
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
            tiles[index].image = utilTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseTile(String s) {
        // Prefer integers; fallback to single-letter A..Z => 10..35
        try { return Integer.parseInt(s); }
        catch (NumberFormatException ignore) {
            if (s.length() == 1) {
                char c = Character.toUpperCase(s.charAt(0));
                if (c >= 'A' && c <= 'Z') return 10 + (c - 'A');
            }
            return 0; // default/fallback
        }
    }

    public void loadMap(String mapFilePath) {
        try (InputStream is = Objects.requireNonNull(getClass().getResourceAsStream(mapFilePath));
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int row = 0;
            String line;
            while (row < gamePanel.maxWorldRow && (line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] tokens = line.split("\\s+");
                int colsThisLine = Math.min(tokens.length, gamePanel.maxWorldCol);

                for (int col = 0; col < colsThisLine; col++) {
                    int n = parseTile(tokens[col]);
                    // clamp to valid tile range
                    if (n < 0 || n >= tiles.length) n = 0;
                    mapTileNumber[col][row] = n;
                }
                // if line is short, remaining cells stay default 0
                row++;
            }
            // optional: fill any remaining rows with 0s (already defaulted by int[][])
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void draw(Graphics2D g2d) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                g2d.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
