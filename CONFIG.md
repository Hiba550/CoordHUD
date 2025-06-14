# CoordHUD Configuration Guide

CoordHUD offers extensive customization options through both in-game GUI and JSON configuration.

## In-Game Configuration

Press **Home** key to open the configuration menu where you can:

### Toggle Display Elements
- HUD On/Off
- Coordinates display
- Biome information
- Direction/compass
- Time display
- FPS counter
- Weather information
- And many more...

### Theme Selection
- **Default**: Classic white text with dark background
- **Dark**: Subdued gray colors for minimal distraction
- **Rainbow**: Animated rainbow colors for a fun experience
- **Minimal**: Clean minimal appearance

## Keybinds

| Key | Action |
|-----|--------|
| **F3** | Toggle HUD on/off |
| **C** | Toggle compass display |
| **Home** | Open configuration menu |

## JSON Configuration

The configuration is automatically saved to `coordhud-config.json` in your Minecraft directory.

### Main Configuration Options

```json
{
  "enableHUD": true,
  "hudX": 5,
  "hudY": 5,
  "showCoordinates": true,
  "showBiome": true,
  "showDirection": true,
  "showTime": true,
  "showFPS": true,
  "showCompass": true,
  "theme": "default",
  "textScale": 1.0,
  "textColor": -1,
  "backgroundColor": -2147483648,
  "borderColor": -11184811
}
```

### Display Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `enableHUD` | boolean | `true` | Master toggle for the HUD |
| `hudX` | integer | `5` | X position of the HUD |
| `hudY` | integer | `5` | Y position of the HUD |
| `showCoordinates` | boolean | `true` | Display XYZ coordinates |
| `showBiome` | boolean | `true` | Show current biome |
| `showDirection` | boolean | `true` | Show facing direction |
| `showTime` | boolean | `true` | Display in-game time |
| `showFPS` | boolean | `true` | Show frames per second |
| `showCompass` | boolean | `true` | Display compass widget |
| `showWeather` | boolean | `true` | Show weather conditions |
| `showNetherCoords` | boolean | `true` | Show coordinate conversion |
| `showLightLevel` | boolean | `false` | Display light levels |
| `showSlimeChunk` | boolean | `false` | Show slime chunk status |
| `showPlayerHealth` | boolean | `false` | Display player health |
| `showPlayerFood` | boolean | `false` | Show food level |
| `showPlayerArmor` | boolean | `false` | Display armor value |

### Visual Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `theme` | string | `"default"` | Theme name (default, dark, rainbow, minimal) |
| `textScale` | float | `1.0` | Text scaling factor |
| `textColor` | integer | `-1` | Text color in ARGB format |
| `backgroundColor` | integer | `-2147483648` | Background color in ARGB format |
| `borderColor` | integer | `-11184811` | Border color in ARGB format |
| `showBackground` | boolean | `true` | Show background panel |
| `showBorder` | boolean | `true` | Show border around HUD |
| `backgroundPadding` | integer | `3` | Padding around text |

### Performance Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `updateFrequency` | integer | `1` | Update frequency (ticks between updates) |

### Compass Options

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `compassSize` | integer | `50` | Size of the compass widget |
| `showCardinalDirections` | boolean | `true` | Show N/S/E/W on compass |
| `showDegrees` | boolean | `true` | Show degree numbers |

## Color Format

Colors are stored as integers in ARGB format:
- Alpha (transparency): 0-255 (0 = transparent, 255 = opaque)
- Red: 0-255
- Green: 0-255  
- Blue: 0-255

Example: `-1` = white, `-2147483648` = semi-transparent black

## Tips

1. **Performance**: Increase `updateFrequency` if you experience lag
2. **Visibility**: Use "dark" theme for better visibility during the day
3. **Minimal Setup**: Disable unused features to reduce clutter
4. **Custom Colors**: Use the `useCustomColors` option for full color control
5. **Positioning**: Adjust `hudX` and `hudY` to move the HUD anywhere on screen

## Troubleshooting

- **HUD not showing**: Check that `enableHUD` is `true`
- **Config not saving**: Make sure Minecraft has write permissions to the game directory
- **Performance issues**: Increase `updateFrequency` or disable some display options
- **Colors look wrong**: Verify color values are in correct ARGB format
