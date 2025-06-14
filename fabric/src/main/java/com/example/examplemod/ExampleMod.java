package com.coorddisplay.coordhud;

import com.coorddisplay.coordhud.Constants;
import com.coorddisplay.coordhud.CommonClass;
import net.fabricmc.api.ModInitializer;

public class CoordHUD implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
