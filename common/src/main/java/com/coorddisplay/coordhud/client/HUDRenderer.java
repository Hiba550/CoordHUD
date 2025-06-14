package com.coorddisplay.coordhud.client;

import com.coorddisplay.coordhud.config.CoordHUDConfig;
import com.coorddisplay.coordhud.data.HUDDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class HUDRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    private static HUDDataProvider.HUDData cachedData;
    private static int updateCounter = 0;
    
    public static void renderHUD(GuiGraphics graphics) {
        CoordHUDConfig config = CoordHUDConfig.getInstance();
        
        if (!config.enableHUD || mc.options.hideGui || mc.level == null || mc.player == null) {
            return;
        }
        
        // Update data based on frequency
        updateCounter++;
        if (updateCounter >= config.updateFrequency || cachedData == null) {
            cachedData = HUDDataProvider.gatherData();
            updateCounter = 0;
        }
        
        List<String> lines = buildDisplayLines(config, cachedData);
        if (lines.isEmpty()) return;
        
        Font font = mc.font;
        int maxWidth = getMaxWidth(font, lines);
        int totalHeight = lines.size() * (font.lineHeight + 1) - 1;
        
        int x = config.hudX;
        int y = config.hudY;
        
        // Render background
        if (config.showBackground) {
            int bgX = x - config.backgroundPadding;
            int bgY = y - config.backgroundPadding;
            int bgWidth = (int)(maxWidth * config.textScale) + config.backgroundPadding * 2;
            int bgHeight = (int)(totalHeight * config.textScale) + config.backgroundPadding * 2;
            
            int bgColor = getThemeBackgroundColor(config);
            graphics.fill(bgX, bgY, bgX + bgWidth, bgY + bgHeight, bgColor);
            
            if (config.showBorder) {
                int borderColor = getThemeBorderColor(config);
                // Top
                graphics.fill(bgX, bgY, bgX + bgWidth, bgY + 1, borderColor);
                // Bottom
                graphics.fill(bgX, bgY + bgHeight - 1, bgX + bgWidth, bgY + bgHeight, borderColor);
                // Left
                graphics.fill(bgX, bgY, bgX + 1, bgY + bgHeight, borderColor);
                // Right
                graphics.fill(bgX + bgWidth - 1, bgY, bgX + bgWidth, bgY + bgHeight, borderColor);
            }
        }
        
        // Render text
        graphics.pose().pushPose();
        graphics.pose().scale(config.textScale, config.textScale, 1.0f);
        
        int scaledX = (int)(x / config.textScale);
        int scaledY = (int)(y / config.textScale);
        
        for (int i = 0; i < lines.size(); i++) {
            int textColor = getThemeColor(config, lines.get(i), config.textColor);
            graphics.drawString(font, lines.get(i), scaledX, scaledY + i * (font.lineHeight + 1), 
                              textColor, false);
        }
        
        graphics.pose().popPose();
        
        // Render compass if enabled
        if (config.showCompass && cachedData != null) {
            renderCompass(graphics, config, cachedData.yaw);
        }
    }
    
    
    private static List<String> buildDisplayLines(CoordHUDConfig config, HUDDataProvider.HUDData data) {
        List<String> lines = new ArrayList<>();
        
        if (config.showCoordinates && data.coordinates != null) {
            lines.add(data.coordinates);
        }
        
        if (config.showBiome && data.biome != null && !data.biome.equals("Biome: -")) {
            lines.add(data.biome);
        }
        
        if (config.showDirection && data.direction != null && !data.direction.equals("Direction: -")) {
            lines.add("Facing: " + data.direction);
        }
        
        if (config.showTime && data.time != null && !data.time.equals("Time: -")) {
            lines.add(data.time);
        }
        
        if (config.showFPS && data.fps != null && !data.fps.equals("FPS: -")) {
            lines.add(data.fps);
        }
        
        if (config.showChunkInfo && data.chunkInfo != null && !data.chunkInfo.equals("Chunk: - / -")) {
            lines.add(data.chunkInfo);
        }
        
        if (config.showDimension && data.dimension != null && !data.dimension.equals("Dimension: -")) {
            lines.add(data.dimension);
        }
        
        if (config.showSpeed && data.speed != null && !data.speed.equals("Speed: -")) {
            lines.add(data.speed);
        }
        
        if (config.showNetherCoords && data.netherCoords != null && !data.netherCoords.isEmpty()) {
            lines.add(data.netherCoords);
        }
        
        if (config.showLightLevel && data.lightLevel != null && !data.lightLevel.equals("Light: Unknown")) {
            lines.add(data.lightLevel);
        }
        
        if (config.showSlimeChunk && data.slimeChunk != null) {
            lines.add(data.slimeChunk);
        }
        
        if (config.showWeather && data.weather != null) {
            lines.add(data.weather);
        }
        
        if (config.showSeed && data.seed != null && !data.seed.equals("Seed: Hidden")) {
            lines.add(data.seed);
        }
        
        if (config.showPlayerHealth && data.playerHealth != null) {
            lines.add(data.playerHealth);
        }
        
        if (config.showPlayerFood && data.playerFood != null) {
            lines.add(data.playerFood);
        }
        
        if (config.showPlayerArmor && data.playerArmor != null) {
            lines.add(data.playerArmor);
        }
        
        if (config.showBlockUnderPlayer && data.blockUnderPlayer != null) {
            lines.add(data.blockUnderPlayer);
        }
        
        if (config.showTargetedBlock && data.targetedBlock != null) {
            lines.add(data.targetedBlock);
        }
        
        if (config.showMotion && data.motion != null) {
            lines.add(data.motion);
        }
        
        if (config.showRotation && data.rotation != null) {
            lines.add(data.rotation);
        }
        
        if (config.showWorldTime && data.worldTime != null) {
            lines.add(data.worldTime);
        }
        
        if (config.showRealTime && data.realTime != null) {
            lines.add(data.realTime);
        }
        
        if (config.showMobSpawning && data.mobSpawning != null) {
            lines.add(data.mobSpawning);
        }
        
        return lines;
    }
    
    private static int getMaxWidth(Font font, List<String> lines) {
        int maxWidth = 0;
        for (String line : lines) {
            maxWidth = Math.max(maxWidth, font.width(line));
        }
        return maxWidth;
    }
    
    private static void renderCompass(GuiGraphics graphics, CoordHUDConfig config, float yaw) {
        int compassX = mc.getWindow().getGuiScaledWidth() - config.compassSize - 10;
        int compassY = 10;
        int radius = config.compassSize / 2;
        int centerX = compassX + radius;
        int centerY = compassY + radius;
        
        // Background circle with gradient effect
        renderCircle(graphics, centerX, centerY, radius, 0x80000000);
        renderCircle(graphics, centerX, centerY, radius - 2, 0x40FFFFFF);
        
        // Draw cardinal directions
        if (config.showCardinalDirections) {
            Font font = mc.font;
            
            // North (Red)
            int northX = centerX - font.width("N") / 2;
            int northY = compassY + 2;
            graphics.drawString(font, "N", northX, northY, 0xFFFF0000, false);
            
            // South (Blue)
            int southX = centerX - font.width("S") / 2;
            int southY = compassY + config.compassSize - font.lineHeight - 2;
            graphics.drawString(font, "S", southX, southY, 0xFF0000FF, false);
            
            // East (Green)
            int eastX = compassX + config.compassSize - font.width("E") - 2;
            int eastY = centerY - font.lineHeight / 2;
            graphics.drawString(font, "E", eastX, eastY, 0xFF00FF00, false);
            
            // West (Yellow)
            int westX = compassX + 2;
            int westY = centerY - font.lineHeight / 2;
            graphics.drawString(font, "W", westX, westY, 0xFFFFFF00, false);
        }
        
        // Draw direction needle with better graphics
        float arrowLength = radius * 0.7f;
        float radians = (float) Math.toRadians(yaw + 180); // +180 to point north when yaw is 0
        
        // Main arrow
        int arrowEndX = centerX + (int)(Math.sin(radians) * arrowLength);
        int arrowEndY = centerY - (int)(Math.cos(radians) * arrowLength);
        
        // Arrow shaft
        drawThickLine(graphics, centerX, centerY, arrowEndX, arrowEndY, 0xFFFF0000, 2);
        
        // Arrow head
        float arrowHeadAngle = 0.5f;
        float arrowHeadLength = arrowLength * 0.3f;
        
        int headX1 = arrowEndX - (int)(Math.sin(radians + arrowHeadAngle) * arrowHeadLength);
        int headY1 = arrowEndY + (int)(Math.cos(radians + arrowHeadAngle) * arrowHeadLength);
        int headX2 = arrowEndX - (int)(Math.sin(radians - arrowHeadAngle) * arrowHeadLength);
        int headY2 = arrowEndY + (int)(Math.cos(radians - arrowHeadAngle) * arrowHeadLength);
        
        drawThickLine(graphics, arrowEndX, arrowEndY, headX1, headY1, 0xFFFF0000, 2);
        drawThickLine(graphics, arrowEndX, arrowEndY, headX2, headY2, 0xFFFF0000, 2);
        
        // Center dot
        renderCircle(graphics, centerX, centerY, 3, 0xFFFFFFFF);
        
        if (config.showDegrees) {
            String degreeText = String.format("%.0f°", Math.abs(yaw));
            int textX = centerX - font.width(degreeText) / 2;
            int textY = centerY + radius + 5;
            graphics.drawString(font, degreeText, textX, textY, 0xFFFFFF00, false);
        }
    }
    
    private static void renderCircle(GuiGraphics graphics, int centerX, int centerY, int radius, int color) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x * x + y * y <= radius * radius) {
                    graphics.fill(centerX + x, centerY + y, centerX + x + 1, centerY + y + 1, color);
                }
            }
        }
    }
    
    private static void drawThickLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color, int thickness) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int steps = Math.max(dx, dy);
        
        if (steps == 0) return;
        
        float xStep = (float)(x2 - x1) / steps;
        float yStep = (float)(y2 - y1) / steps;
        
        for (int i = 0; i <= steps; i++) {
            int x = x1 + (int)(xStep * i);
            int y = y1 + (int)(yStep * i);
            
            // Draw thick point
            for (int dx2 = -thickness/2; dx2 <= thickness/2; dx2++) {
                for (int dy2 = -thickness/2; dy2 <= thickness/2; dy2++) {
                    graphics.fill(x + dx2, y + dy2, x + dx2 + 1, y + dy2 + 1, color);
                }
            }
        }
    }
    
    private static int getThemeColor(CoordHUDConfig config, String line, int defaultColor) {
        if (!config.useCustomColors) {
            switch (config.theme) {
                case "dark":
                    return 0xFF888888;
                case "rainbow":
                    return getRainbowColor(System.currentTimeMillis());
                case "minimal":
                    return 0xFFAAAAAA;
                default:
                    return defaultColor;
            }
        }
        return config.textColor;
    }
    
    private static int getRainbowColor(long time) {
        float hue = (time / 50f) % 360f;
        return java.awt.Color.HSBtoRGB(hue / 360f, 1f, 1f) | 0xFF000000;
    }
    
    private static int getThemeBackgroundColor(CoordHUDConfig config) {
        if (!config.useCustomColors) {
            switch (config.theme) {
                case "dark":
                    return 0xE0000000;
                case "rainbow":
                    return 0x80000000;
                case "minimal":
                    return 0x40000000;
                default:
                    return config.backgroundColor;
            }
        }
        return config.backgroundColor;
    }
    
    private static int getThemeBorderColor(CoordHUDConfig config) {
        if (!config.useCustomColors) {
            switch (config.theme) {
                case "dark":
                    return 0xFF333333;
                case "rainbow":
                    return getRainbowColor(System.currentTimeMillis() + 500);
                case "minimal":
                    return 0xFF777777;
                default:
                    return config.borderColor;
            }
        }
        return config.borderColor;
    }
}
