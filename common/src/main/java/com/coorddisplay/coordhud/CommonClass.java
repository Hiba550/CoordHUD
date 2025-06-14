package com.coorddisplay.coordhud;

import com.coorddisplay.coordhud.platform.Services;
import com.coorddisplay.coordhud.config.CoordHUDConfig;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
        Constants.LOG.info("Hello from CoordHUD init on {}! we are currently in a {} environment!", 
                          Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        
        // Initialize configuration
        CoordHUDConfig.getInstance();
        Constants.LOG.info("CoordHUD configuration loaded successfully!");
        
        Constants.LOG.info("CoordHUD initialization complete! Ready to display coordinates.");
    }
    
    public static void initClient() {
        Constants.LOG.info("Initializing CoordHUD client-side features...");
        // Client-specific initialization will be handled by platform-specific code
    }
}
