package manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager implements ISoundManager {

    private Clip background;
    private long clipTime = 0;

    private volatile boolean exclusivePlaying = false;

    public SoundManager() {
        background = getClip(loadAudio("background"));
    }

    private AudioInputStream loadAudio(String url) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/media/audio/" + url + ".wav");
            if (audioSrc == null) {
                System.err.println("Audio resource not found: " + url);
                return null;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    private Clip getClip(AudioInputStream stream) {
        if (stream == null)
            return null;
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void resumeBackground() {
        if (background == null || exclusivePlaying)
            return;
        background.setMicrosecondPosition(clipTime);
        background.start();
    }

    @Override
    public void pauseBackground() {
        if (background == null)
            return;
        clipTime = background.getMicrosecondPosition();
        background.stop();
    }

    @Override
    public void restartBackground() {
        clipTime = 0;
        resumeBackground();
    }

    private void playOnce(String name) {
        Clip clip = getClip(loadAudio(name));
        if (clip == null)
            return;
        clip.start();
    }

    private void playExclusive(String name) {
        Clip clip = getClip(loadAudio(name));
        if (clip == null)
            return;

        exclusivePlaying = true;
        pauseBackground();

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                try {
                    clip.close();
                } catch (Exception ignored) {
                }
                exclusivePlaying = false;
                resumeBackground();
            }
        });

        clip.start();
    }

    @Override
    public void playJump() {
        playOnce("jump");
    }

    @Override
    public void playCoin() {
        playOnce("coin");
    }

    @Override
    public void playFireball() {
        playOnce("fireball");
    }

    @Override
    public void playGameOver() {
        playOnce("gameOver");
    }

    @Override
    public void playStomp() {
        playOnce("stomp");
    }

    @Override
    public void playOneUp() {
        playOnce("oneUp");
    }

    @Override
    public void playSuperMushroom() {
        playExclusive("superMushroom");
    }

    @Override
    public void playMarioDies() {
        playExclusive("marioDies");
    }

    @Override
    public void playFireFlower() {

    }
}