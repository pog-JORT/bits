package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    final int FPS = 60;

    // System
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound se = new Sound();
    Sound music = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;


    // Entity and Object
    public Player player = new Player(this,keyHandler);
    public SuperObject[] objects = new SuperObject[10];
    public Entity npcs[] = new Entity[10];

    // Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        // can be "focused" and receive key input because true
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;

    }


    public void run(){

        // delta time loop
        double drawInterval = 1_000_000_000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if(gameState == playState){
            player.update();

            for(Entity npc: npcs){
                if (npc != null) {
                    npc.update();
                }
            }
        }
        if(gameState == pauseState){
            // nothing
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // debug
        long drawStart = 0;
        if (keyHandler.debugging) {
            drawStart = System.nanoTime();
        }


        Graphics2D g2d = (Graphics2D) g;

        // tiles
        tileManager.draw(g2d);

        // objects
        for (SuperObject object : objects) {
            if (object != null) {
                object.draw(g2d, this);
            }
        }

        // npc
        for (Entity npc : npcs) {
            if (npc != null) {
                npc.draw(g2d);
            }
        }

        // player
        player.draw(g2d);

        // ui
        ui.draw(g2d);

        // debug
        if (keyHandler.debugging) {

        long drawEnd = System.nanoTime();
        long timePassed = drawEnd - drawStart;

        g2d.setColor(Color.white);
        g2d.drawString("Draw Time: " + timePassed, 10, 400);
        System.out.println("Draw Time: " + timePassed);
        }

        g2d.dispose();
    }

    public void playMusic(int index){
        music.setFile(index);
        music.playSound();
        music.loopSound();
    }

    public void stopMusic(){
        music.stopSound();
    }

    public void playSE(int index){
        se.setFile(index);
        se.playSound();
    }

}
