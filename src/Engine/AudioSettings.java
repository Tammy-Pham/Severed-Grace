package Engine;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class AudioSettings {

    private static final String SETTINGS_FILE = "settings.properties";

    private static float musicVolume = 0.7f;
    private static float sfxVolume = 0.8f;

    static {
        load();
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static float getSfxVolume() {
        return sfxVolume;
    }

    public static void setMusicVolume(float volume) {
        musicVolume = clamp(volume);
        MusicPlayer.updateVolume();
        save();
    }

    public static void setSfxVolume(float volume) {
        sfxVolume = clamp(volume);
        save();
    }

    // Sets a clip's loudness from a 0.0 - 1.0 volume
    public static void applyVolume(Clip clip, float volume) {
        if (clip.isControlSupported(BooleanControl.Type.MUTE)) {
            ((BooleanControl) clip.getControl(BooleanControl.Type.MUTE)).setValue(volume <= 0f);
        }
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Squaring the volume makes the slider feel even to the ear (50% sounds about half as loud)
            float amplitude = Math.max(volume * volume, 0.0001f);
            float decibels = (float) (20.0 * Math.log10(amplitude));
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), decibels)));
        }
    }

    private static float clamp(float volume) {
        // Round to the nearest 5% so repeated taps don't drift (e.g. 0.7000001)
        float rounded = Math.round(volume * 20f) / 20f;
        return Math.max(0f, Math.min(1f, rounded));
    }

    private static void load() {
        File file = new File(SETTINGS_FILE);
        if (!file.exists()) {
            return;
        }
        try (InputStream in = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(in);
            musicVolume = clamp(Float.parseFloat(properties.getProperty("musicVolume", String.valueOf(musicVolume))));
            sfxVolume = clamp(Float.parseFloat(properties.getProperty("sfxVolume", String.valueOf(sfxVolume))));
        } catch (Exception e) {
            System.out.println("Unable to load " + SETTINGS_FILE + ", using default volumes: " + e.getMessage());
        }
    }

    private static void save() {
        Properties properties = new Properties();
        properties.setProperty("musicVolume", String.valueOf(musicVolume));
        properties.setProperty("sfxVolume", String.valueOf(sfxVolume));
        try (OutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(out, "Severed Grace audio settings");
        } catch (Exception e) {
            System.out.println("Unable to save " + SETTINGS_FILE + ": " + e.getMessage());
        }
    }
}
    

