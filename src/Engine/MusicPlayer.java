package Engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class MusicPlayer {

    private static final int FADE_OUT_MILLIS = 600;

    private static Clip currentClip;
    private static String currentTrack;
    private static int requestId = 0;

    public static synchronized void play(String fileName) {
        if (fileName.equals(currentTrack)) {
            return;
        }
        currentTrack = fileName;
        final int myRequest = ++requestId;

        fadeOutAndClose(currentClip);
        currentClip = null;

        Thread loader = new Thread(() -> {
            Clip clip = loadClip(fileName);
            synchronized (MusicPlayer.class) {
                // If another track was requested while this one loaded, throw this one away
                if (clip == null || myRequest != requestId) {
                    if (clip != null) {
                        clip.close();
                    }
                    return;
                }
                currentClip = clip;
                AudioSettings.applyVolume(clip, AudioSettings.getMusicVolume());
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }, "music-loader");
        loader.setDaemon(true);
        loader.start();
    }

    public static synchronized void stop() {
        requestId++;
        currentTrack = null;
        fadeOutAndClose(currentClip);
        currentClip = null;
    }

    public static synchronized void updateVolume() {
        if (currentClip != null) {
            AudioSettings.applyVolume(currentClip, AudioSettings.getMusicVolume());
        }
    }

    private static Clip loadClip(String fileName) {
        try {
            File file = new File(Config.RESOURCES_PATH + fileName);
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(new FileInputStream(file)));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception e) {
            // A missing file or no audio device shouldn't crash the game
            System.out.println("Unable to play music " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    // Fades the old track out over a short time instead of cutting it off with a click
    private static void fadeOutAndClose(Clip clip) {
        if (clip == null) {
            return;
        }
        Thread fader = new Thread(() -> {
            float startVolume = AudioSettings.getMusicVolume();
            int steps = 20;
            for (int i = steps - 1; i >= 0; i--) {
                AudioSettings.applyVolume(clip, startVolume * i / steps);
                try {
                    Thread.sleep(FADE_OUT_MILLIS / steps);
                } catch (InterruptedException e) {
                    break;
                }
            }
            clip.stop();
            clip.close();
        }, "music-fader");
        fader.setDaemon(true);
        fader.start();
    }
}
    

