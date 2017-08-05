package window.setting;

import window.main.Main;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Setting {
    private static final Setting ourInstance = loadSettings();
    //where to save the checkpoints
    private String checkpointSavePath;
    //where to save the maps
    private String mapSaveLocation;
    //The number of minutes to wait before saving
    private int checkpointSaveFrequency;
    //the random seed
    private int randomSeed;
    //choose between false = 2d or true = 3d
    private boolean gameDimension;
    //when spawning, how many ai to spawn
    private int numberOfGenerationsAtOnce;
    //how strong the gravity is in m/s²
    private double gravity;

    private Setting(String checkpointSavePath, String mapSaveLocation, int checkpointSaveFrequency, int randomSeed, boolean gameDimension, int numberOfGenerationsAtOnce, double gravity) {
        this.checkpointSavePath = checkpointSavePath;
        this.mapSaveLocation = mapSaveLocation;
        this.checkpointSaveFrequency = checkpointSaveFrequency;
        this.randomSeed = randomSeed;
        this.gameDimension = gameDimension;
        this.numberOfGenerationsAtOnce = numberOfGenerationsAtOnce;
        this.gravity = gravity;
    }

    private Setting() {
        checkpointSaveFrequency = 10;
        checkpointSavePath = "./" + Main.APPLICATION_NAME + "/checkpoints/";
        mapSaveLocation = "./" + Main.APPLICATION_NAME + "/maps/";
        numberOfGenerationsAtOnce = 1;
        gravity = 9.807;


        File checkpointsDirectory = new File(checkpointSavePath);

        if (!checkpointsDirectory.exists())
            checkpointsDirectory.mkdirs();

        File mapsDirectory = new File(mapSaveLocation);
        if (!mapsDirectory.exists())
            mapsDirectory.mkdirs();
    }

    public static Setting getInstance() {
        return ourInstance;
    }

    private static Setting loadSettings() {
        Setting setting = null;
        try {
            Preferences p = Preferences.userNodeForPackage(Setting.class);

            System.out.println(p.absolutePath());
            if (p.keys().length > 0) {
                System.out.println("Getting old settings");

                setting = new Setting(p.get(SaveKeyConstant.CHECKPOINT_KEY, "./" + Main.APPLICATION_NAME + "/checkpoints/"),
                        p.get(SaveKeyConstant.MAP_KEY, "./" + Main.APPLICATION_NAME + "/maps/"), p.getInt(SaveKeyConstant.CHECKPOINT_FREQUENCY_KEY, 10), p.getInt(SaveKeyConstant.RANDOM_SEED_KEY, 0), p.getBoolean(SaveKeyConstant.DIMENSION_KEY, false), p.getInt(SaveKeyConstant.N_OBJECTS_KEY, 1), p.getDouble(SaveKeyConstant.GRAVITY_KEY, 9.807));
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        if (setting == null)
            setting = new Setting();

        setting.saveSettings();

        return setting;

    }

    public String getCheckpointSavePath() {
        return checkpointSavePath;
    }

    public void setCheckpointSavePath(String checkpointSavePath) {
        if (new File(checkpointSavePath).exists())
            this.checkpointSavePath = checkpointSavePath;
    }

    public String getMapSaveLocation() {
        return mapSaveLocation;
    }

    public void setMapSaveLocation(String mapSaveLocation) {

        if (new File(mapSaveLocation).exists())
            this.mapSaveLocation = mapSaveLocation;
    }

    public int getCheckpointSaveFrequency() {
        return checkpointSaveFrequency;
    }

    public void setCheckpointSaveFrequency(int checkpointSaveFrequency) {
        this.checkpointSaveFrequency = Math.max(checkpointSaveFrequency, 0);
    }

    public int getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    public boolean isGameDimension() {
        return gameDimension;
    }

    public void setGameDimension(boolean gameDimension) {
        this.gameDimension = gameDimension;
    }

    public int getNumberOfGenerationsAtOnce() {
        return numberOfGenerationsAtOnce;
    }

    public void setNumberOfGenerationsAtOnce(int numberOfGenerationsAtOnce) {
        this.numberOfGenerationsAtOnce = Math.max(numberOfGenerationsAtOnce, 1);
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = Math.max(gravity, 0);
    }

    public void saveSettings() {
        Preferences preferences = Preferences.userNodeForPackage(Setting.class);
        preferences.put(SaveKeyConstant.CHECKPOINT_KEY, checkpointSavePath);
        preferences.put(SaveKeyConstant.MAP_KEY, mapSaveLocation);
        preferences.putInt(SaveKeyConstant.CHECKPOINT_FREQUENCY_KEY, checkpointSaveFrequency);
        preferences.putInt(SaveKeyConstant.RANDOM_SEED_KEY, randomSeed);
        preferences.putBoolean(SaveKeyConstant.DIMENSION_KEY, gameDimension);
        preferences.putInt(SaveKeyConstant.N_OBJECTS_KEY, numberOfGenerationsAtOnce);
        preferences.putDouble(SaveKeyConstant.GRAVITY_KEY, gravity);
    }
}
