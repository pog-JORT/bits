package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // debug
    boolean debugging = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // not using keyTyped
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // playstate
        if (gamePanel.gameState == gamePanel.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_P) {
                    gamePanel.gameState = gamePanel.pauseState;
            }
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }


        // pauseState
        else if(gamePanel.gameState == gamePanel.pauseState) {
            if(code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }}
        }

        // dialogue state
        else if(gamePanel.gameState == gamePanel.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.playState;
            }
        }



        // Debug
        if(code == KeyEvent.VK_T){
            if(!debugging){
                debugging = true;
            }
            else if (debugging){
                debugging = false;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
