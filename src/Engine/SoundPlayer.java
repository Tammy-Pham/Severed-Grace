package Engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

// Plays short sound effects (.wav files) from the Resources folder
public class SoundPlayer {

    // Plays a sound effect once, e.g. SoundPlayer.play("Chest-Open.wav")
    // Each call gets its own Clip, so the same sound can overlap itself.
    public static void play(String fileName) {
        float volume = AudioSettings.getSfxVolume();
        if (volume <= 0f) {
            return; // sound effects are turned off
        }
        try {
            File file = new File(Config.RESOURCES_PATH + fileName);
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(new FileInputStream(file)));

            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            AudioSettings.applyVolume(clip, volume);

            // Free the audio line once the sound finishes
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            // A missing file or no audio device shouldn't crash the game
            System.out.println("Unable to play sound " + fileName + ": " + e.getMessage());
        }
    }
}