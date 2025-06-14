package com.coorddisplay.coordhud.client;

import com.coorddisplay.coordhud.config.CoordHUDConfig;
import com.coorddisplay.coordhud.Constants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // Keybindings - these would normally be registered through platform-specific code
    public static final KeyMapping TOGGLE_HUD = new KeyMapping(
        "key.coordhud.toggle_hud",
        GLFW.GLFW_KEY_F3,
        "key.categories.coordhud"
    );
    
    public static final KeyMapping TOGGLE_COMPASS = new KeyMapping(
        "key.coordhud.toggle_compass", 
        GLFW.GLFW_KEY_C,
        "key.categories.coordhud"
    );
    
    public static final KeyMapping OPEN_CONFIG = new KeyMapping(
        "key.coordhud.open_config",
        GLFW.GLFW_KEY_HOME,
        "key.categories.coordhud"
    );
    
    public static void handleKeyInputs() {
        CoordHUDConfig config = CoordHUDConfig.getInstance();
        
        if (TOGGLE_HUD.consumeClick()) {
            config.enableHUD = !config.enableHUD;
            config.save();
            Constants.LOG.info("CoordHUD toggled: " + (config.enableHUD ? "ON" : "OFF"));
        }
        
        if (TOGGLE_COMPASS.consumeClick()) {
            config.showCompass = !config.showCompass;
            config.save();
            Constants.LOG.info("Compass toggled: " + (config.showCompass ? "ON" : "OFF"));
        }
        
        if (OPEN_CONFIG.consumeClick()) {
            if (mc.screen == null) {
                mc.setScreen(new ConfigScreen(null));
            }
        }
    }
}
