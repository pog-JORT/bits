package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){

        soundURL[0] = getClass().getResource("/sound/background01.wav");
        soundURL[1] = getClass().getResource("/sound/coinPickUp.wav");
        soundURL[2] = getClass().getResource("/sound/powerUp.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/locked.wav");
    }

    public void setFile(int index){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public void playSound(){
        clip.start();
    }

    public void loopSound(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopSound(){
        clip.stop();
    }

}
