package com.coorddisplay.coordhud.client;

import com.coorddisplay.coordhud.config.CoordHUDConfig;
import com.coorddisplay.coordhud.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final CoordHUDConfig config;
    
    public ConfigScreen(Screen parent) {
        super(Component.literal("CoordHUD Configuration"));
        this.parent = parent;
        this.config = CoordHUDConfig.getInstance();
    }
    
    @Override
    protected void init() {
        super.init();
        
        int buttonWidth = 200;
        int buttonHeight = 20;
        int x = this.width / 2 - buttonWidth / 2;
        int y = 40;
        int spacing = 25;
        
        // Toggle buttons for major features
        addRenderableWidget(Button.builder(
            Component.literal("HUD: " + (config.enableHUD ? "ON" : "OFF")),
            button -> {
                config.enableHUD = !config.enableHUD;
                button.setMessage(Component.literal("HUD: " + (config.enableHUD ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Coordinates: " + (config.showCoordinates ? "ON" : "OFF")),
            button -> {
                config.showCoordinates = !config.showCoordinates;
                button.setMessage(Component.literal("Coordinates: " + (config.showCoordinates ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Biome: " + (config.showBiome ? "ON" : "OFF")),
            button -> {
                config.showBiome = !config.showBiome;
                button.setMessage(Component.literal("Biome: " + (config.showBiome ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Direction: " + (config.showDirection ? "ON" : "OFF")),
            button -> {
                config.showDirection = !config.showDirection;
                button.setMessage(Component.literal("Direction: " + (config.showDirection ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Time: " + (config.showTime ? "ON" : "OFF")),
            button -> {
                config.showTime = !config.showTime;
                button.setMessage(Component.literal("Time: " + (config.showTime ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("FPS: " + (config.showFPS ? "ON" : "OFF")),
            button -> {
                config.showFPS = !config.showFPS;
                button.setMessage(Component.literal("FPS: " + (config.showFPS ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Compass: " + (config.showCompass ? "ON" : "OFF")),
            button -> {
                config.showCompass = !config.showCompass;
                button.setMessage(Component.literal("Compass: " + (config.showCompass ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        addRenderableWidget(Button.builder(
            Component.literal("Weather: " + (config.showWeather ? "ON" : "OFF")),
            button -> {
                config.showWeather = !config.showWeather;
                button.setMessage(Component.literal("Weather: " + (config.showWeather ? "ON" : "OFF")));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing;
        
        // Theme cycle button
        addRenderableWidget(Button.builder(
            Component.literal("Theme: " + config.theme),
            button -> {
                switch (config.theme) {
                    case "default" -> config.theme = "dark";
                    case "dark" -> config.theme = "rainbow";
                    case "rainbow" -> config.theme = "minimal";
                    case "minimal" -> config.theme = "default";
                }
                button.setMessage(Component.literal("Theme: " + config.theme));
            })
            .bounds(x, y, buttonWidth, buttonHeight)
            .build());
        y += spacing * 2;
        
        // Save and Close buttons
        addRenderableWidget(Button.builder(
            Component.literal("Save & Close"),
            button -> {
                config.save();
                this.minecraft.setScreen(parent);
            })
            .bounds(x - 50, y, buttonWidth / 2 - 5, buttonHeight)
            .build());
            
        addRenderableWidget(Button.builder(
            Component.literal("Cancel"),
            button -> this.minecraft.setScreen(parent))
            .bounds(x + buttonWidth / 2 + 5, y, buttonWidth / 2 - 5, buttonHeight)
            .build());
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
}
