package com.coorddisplay.coordhud.config;

import com.coorddisplay.coordhud.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CoordHUDConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "coordhud-config.json";
    private static CoordHUDConfig instance;

    // HUD Position
    public int hudX = 5;
    public int hudY = 5;
    public boolean enableHUD = true;
    
    // Display Options
    public boolean showCoordinates = true;
    public boolean showBiome = true;
    public boolean showDirection = true;
    public boolean showTime = true;
    public boolean showFPS = true;
    public boolean showChunkInfo = true;
    public boolean showDimension = true;
    public boolean showSpeed = false;
    public boolean showCompass = true;
    
    // Color Settings (ARGB format)
    public int textColor = 0xFFFFFFFF; // White
    public int backgroundColor = 0x80000000; // Semi-transparent black
    public int borderColor = 0xFF555555; // Gray
    
    // Display Format
    public boolean showBackground = true;
    public boolean showBorder = true;
    public float textScale = 1.0f;
    public int backgroundPadding = 3;
    
    // Performance
    public int updateFrequency = 1; // Updates per tick (1 = every tick, 2 = every 2 ticks, etc.)
    
    // Compass Settings
    public boolean showCardinalDirections = true;
    public boolean showDegrees = true;
    public int compassSize = 50;
    
    // Advanced Features
    public boolean showNetherCoords = true; // Show overworld coords when in nether
    public boolean showLightLevel = false;
    public boolean showSlimeChunk = false;
    public boolean showTPS = false; // Server TPS (if available)
    public boolean showWeather = true;
    public boolean showSeed = false; // Only in dev environment
    public boolean showPlayerHealth = false;
    public boolean showPlayerFood = false;
    public boolean showPlayerArmor = false;
    public boolean showBlockUnderPlayer = true;
    public boolean showTargetedBlock = false; // Block player is looking at
    public boolean showMotion = false; // Movement vector
    public boolean showRotation = false; // Pitch and yaw
    public boolean showWorldTime = true; // Minecraft world time
    public boolean showRealTime = false; // Real world time
    public boolean showMobSpawning = false; // Can mobs spawn at current location
    public boolean show24HourTime = false; // Use 24 hour format for time
    
    // Theme Options
    public String theme = "default"; // default, dark, rainbow, minimal
    public boolean useCustomColors = false;
    public boolean enableAnimations = true;
    public boolean pulseOnDamage = true;
    public boolean flashOnNight = false;
    
    // Keybinds
    public String toggleHudKey = "KEY_F3"; // Key to toggle HUD
    public String toggleCompassKey = "KEY_C"; // Key to toggle compass
    public String configMenuKey = "KEY_HOME"; // Key to open config menu
    
    public static CoordHUDConfig getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }
    
    public static CoordHUDConfig load() {
        File configFile = new File(Minecraft.getInstance().gameDirectory, CONFIG_FILE);
        
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                CoordHUDConfig config = GSON.fromJson(reader, CoordHUDConfig.class);
                if (config != null) {
                    Constants.LOG.info("Config loaded successfully");
                    return config;
                }
            } catch (IOException e) {
                Constants.LOG.error("Failed to load config file", e);
            }
        }
        
        Constants.LOG.info("Creating new config file");
        CoordHUDConfig config = new CoordHUDConfig();
        config.save();
        return config;
    }
    
    public void save() {
        File configFile = new File(Minecraft.getInstance().gameDirectory, CONFIG_FILE);
        
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(this, writer);
            Constants.LOG.info("Config saved successfully");
        } catch (IOException e) {
            Constants.LOG.error("Failed to save config file", e);
        }
    }
    
    public void reload() {
        instance = load();
    }
}
