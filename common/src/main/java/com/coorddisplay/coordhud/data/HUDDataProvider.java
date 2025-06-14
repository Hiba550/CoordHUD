package com.coorddisplay.coordhud.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.text.DecimalFormat;

public class HUDDataProvider {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
    private static final Minecraft mc = Minecraft.getInstance();
    
    public static class HUDData {
        public final String coordinates;
        public final String biome;
        public final String direction;
        public final String time;
        public final String fps;
        public final String chunkInfo;
        public final String dimension;
        public final String speed;
        public final String compass;
        public final String netherCoords;
        public final String lightLevel;
        public final String slimeChunk;
        public final String weather;
        public final String seed;
        public final String playerHealth;
        public final String playerFood;
        public final String playerArmor;
        public final String blockUnderPlayer;
        public final String targetedBlock;
        public final String motion;
        public final String rotation;
        public final String worldTime;
        public final String realTime;
        public final String mobSpawning;
        public final float yaw;
        
        public HUDData(String coordinates, String biome, String direction, String time, 
                      String fps, String chunkInfo, String dimension, String speed, 
                      String compass, String netherCoords, String lightLevel, 
                      String slimeChunk, String weather, String seed, String playerHealth,
                      String playerFood, String playerArmor, String blockUnderPlayer, 
                      String targetedBlock, String motion, String rotation, 
                      String worldTime, String realTime, String mobSpawning, float yaw) {
            this.coordinates = coordinates;
            this.biome = biome;
            this.direction = direction;
            this.time = time;
            this.fps = fps;
            this.chunkInfo = chunkInfo;
            this.dimension = dimension;
            this.speed = speed;
            this.compass = compass;
            this.netherCoords = netherCoords;
            this.lightLevel = lightLevel;
            this.slimeChunk = slimeChunk;
            this.weather = weather;
            this.seed = seed;
            this.playerHealth = playerHealth;
            this.playerFood = playerFood;
            this.playerArmor = playerArmor;
            this.blockUnderPlayer = blockUnderPlayer;
            this.targetedBlock = targetedBlock;
            this.motion = motion;
            this.rotation = rotation;
            this.worldTime = worldTime;
            this.realTime = realTime;
            this.mobSpawning = mobSpawning;
            this.yaw = yaw;
        }
    }
    
    public static HUDData gatherData() {
        if (mc.level == null || mc.player == null) {
            return createEmptyData();
        }
        
        LocalPlayer player = mc.player;
        Level level = mc.level;
        BlockPos playerPos = player.blockPosition();
        
        // Coordinates
        String coordinates = String.format("XYZ: %d / %d / %d", 
            playerPos.getX(), playerPos.getY(), playerPos.getZ());
        
        // Biome
        String biome = getBiomeName(level, playerPos);
        
        // Direction and Yaw
        float yaw = Mth.wrapDegrees(player.getYRot());
        String direction = getCardinalDirection(yaw);
        
        // Time
        String time = getTimeString(level.getDayTime());
        
        // FPS
        String fps = mc.getFps() + " fps";
        
        // Chunk Info
        String chunkInfo = String.format("Chunk: %d / %d", 
            playerPos.getX() >> 4, playerPos.getZ() >> 4);
        
        // Dimension
        String dimension = getDimensionName(level.dimension());
        
        // Speed
        String speed = getSpeedString(player);
        
        // Compass
        String compass = String.format("%s (%.1f°)", direction, Math.abs(yaw));
        
        // Nether Coordinates
        String netherCoords = getNetherCoords(level, playerPos);
        
        // Light Level
        String lightLevel = getLightLevel(level, playerPos);
        
        // Slime Chunk
        String slimeChunk = isSlimeChunk(playerPos) ? "Slime Chunk: Yes" : "Slime Chunk: No";
        
        // Weather
        String weather = getWeatherString(level);
        
        // Seed (only in dev environment)
        String seed = getSeedString(level);
        
        // Player Health
        String playerHealth = String.format("Health: %.1f/%.1f", player.getHealth(), player.getMaxHealth());
        
        // Player Food
        String playerFood = String.format("Food: %d/20", player.getFoodData().getFoodLevel());
        
        // Player Armor
        String playerArmor = String.format("Armor: %d", player.getArmorValue());
        
        // Block Under Player
        String blockUnderPlayer = getBlockUnderPlayer(level, playerPos);
        
        // Targeted Block
        String targetedBlock = getTargetedBlock();
        
        // Motion
        String motion = getMotionString(player);
        
        // Rotation
        String rotation = getRotationString(player);
        
        // World Time
        String worldTime = getWorldTimeString(level.getDayTime());
        
        // Real Time
        String realTime = getRealTimeString();
        
        // Mob Spawning
        String mobSpawning = getMobSpawningInfo(level, playerPos);
        
        return new HUDData(coordinates, biome, direction, time, fps, chunkInfo, 
            dimension, speed, compass, netherCoords, lightLevel, slimeChunk,
            weather, seed, playerHealth, playerFood, playerArmor, blockUnderPlayer,
            targetedBlock, motion, rotation, worldTime, realTime, mobSpawning, yaw);
    }
    
    private static HUDData createEmptyData() {
        return new HUDData("XYZ: - / - / -", "Biome: -", "Direction: -", 
                          "Time: -", "FPS: -", "Chunk: - / -", "Dimension: -", 
                          "Speed: -", "Compass: -", "", "", "", "", "", "", "", "", 
                          "", "", "", "", "", "", "", 0f);
    }
    
    private static String getBiomeName(Level level, BlockPos pos) {
        try {
            Biome biome = level.getBiome(pos).value();
            String biomeName = level.registryAccess()
                .registryOrThrow(net.minecraft.core.registries.Registries.BIOME)
                .getKey(biome).toString();
            return "Biome: " + formatBiomeName(biomeName);
        } catch (Exception e) {
            return "Biome: Unknown";
        }
    }
    
    private static String formatBiomeName(String biomeName) {
        if (biomeName.contains(":")) {
            biomeName = biomeName.split(":")[1];
        }
        return biomeName.replace("_", " ")
                       .toLowerCase()
                       .replaceAll("\\b\\w", m -> m.group().toUpperCase());
    }
    
    private static String getCardinalDirection(float yaw) {
        yaw = Mth.wrapDegrees(yaw);
        if (yaw < 0) yaw += 360;
        
        if (yaw >= 337.5 || yaw < 22.5) return "South";
        else if (yaw >= 22.5 && yaw < 67.5) return "Southwest";
        else if (yaw >= 67.5 && yaw < 112.5) return "West";
        else if (yaw >= 112.5 && yaw < 157.5) return "Northwest";
        else if (yaw >= 157.5 && yaw < 202.5) return "North";
        else if (yaw >= 202.5 && yaw < 247.5) return "Northeast";
        else if (yaw >= 247.5 && yaw < 292.5) return "East";
        else return "Southeast";
    }
    
    private static String getTimeString(long worldTime) {
        long time = worldTime % 24000;
        int hours = (int) ((time / 1000 + 6) % 24);
        int minutes = (int) ((time % 1000) * 60 / 1000);
        return String.format("Time: %02d:%02d", hours, minutes);
    }
    
    private static String getDimensionName(ResourceKey<Level> dimension) {
        String dimName = dimension.location().getPath();
        return "Dimension: " + formatBiomeName(dimName);
    }
    
    private static String getSpeedString(LocalPlayer player) {
        double deltaX = player.getX() - player.xOld;
        double deltaZ = player.getZ() - player.zOld;
        double speed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 20; // Convert to blocks per second
        return "Speed: " + DECIMAL_FORMAT.format(speed) + " b/s";
    }
    
    private static String getNetherCoords(Level level, BlockPos pos) {
        if (level.dimension() == Level.NETHER) {
            // In Nether, show Overworld equivalent
            return String.format("Overworld: %d / %d", pos.getX() * 8, pos.getZ() * 8);
        } else if (level.dimension() == Level.OVERWORLD) {
            // In Overworld, show Nether equivalent
            return String.format("Nether: %d / %d", pos.getX() / 8, pos.getZ() / 8);
        }
        return "";
    }
    
    private static String getLightLevel(Level level, BlockPos pos) {
        try {
            int blockLight = level.getBrightness(net.minecraft.world.level.LightLayer.BLOCK, pos);
            int skyLight = level.getBrightness(net.minecraft.world.level.LightLayer.SKY, pos);
            return String.format("Light: %d (Block: %d, Sky: %d)", 
                Math.max(blockLight, skyLight), blockLight, skyLight);
        } catch (Exception e) {
            return "Light: Unknown";
        }
    }
    
    private static String getWeatherString(Level level) {
        if (level.isRaining()) {
            if (level.isThundering()) {
                return "Weather: Thunderstorm";
            } else {
                return "Weather: Rain";
            }
        } else {
            return "Weather: Clear";
        }
    }
    
    private static String getSeedString(Level level) {
        try {
            if (mc.isLocalServer() && mc.getSingleplayerServer() != null) {
                long seed = mc.getSingleplayerServer().getWorldData().worldGenOptions().seed();
                return "Seed: " + seed;
            }
        } catch (Exception e) {
            // Ignore, seed not available
        }
        return "Seed: Hidden";
    }
    
    private static String getBlockUnderPlayer(Level level, BlockPos pos) {
        try {
            BlockPos underPos = pos.below();
            BlockState state = level.getBlockState(underPos);
            String blockName = state.getBlock().getName().getString();
            return "Block: " + blockName;
        } catch (Exception e) {
            return "Block: Unknown";
        }
    }
    
    private static String getTargetedBlock() {
        try {
            if (mc.hitResult != null && mc.hitResult.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
                net.minecraft.world.phys.BlockHitResult blockHit = (net.minecraft.world.phys.BlockHitResult) mc.hitResult;
                BlockState state = mc.level.getBlockState(blockHit.getBlockPos());
                String blockName = state.getBlock().getName().getString();
                return "Looking at: " + blockName;
            }
        } catch (Exception e) {
            // Ignore
        }
        return "Looking at: Air";
    }
    
    private static String getMotionString(LocalPlayer player) {
        double motionX = player.getDeltaMovement().x;
        double motionY = player.getDeltaMovement().y;
        double motionZ = player.getDeltaMovement().z;
        return String.format("Motion: %.3f, %.3f, %.3f", motionX, motionY, motionZ);
    }
    
    private static String getRotationString(LocalPlayer player) {
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        return String.format("Rotation: %.1f° / %.1f°", pitch, yaw);
    }
    
    private static String getWorldTimeString(long worldTime) {
        long day = worldTime / 24000;
        return String.format("Day: %d", day + 1);
    }
    
    private static String getRealTimeString() {
        java.time.LocalTime now = java.time.LocalTime.now();
        return String.format("Real Time: %02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond());
    }
    
    private static String getMobSpawningInfo(Level level, BlockPos pos) {
        try {
            int lightLevel = level.getMaxLocalRawBrightness(pos);
            boolean canSpawn = lightLevel <= 7;
            return canSpawn ? "Mobs: Can spawn" : "Mobs: Safe";
        } catch (Exception e) {
            return "Mobs: Unknown";
        }
    }
}
