package com.coorddisplay.coordhud;

import com.coorddisplay.coordhud.Constants;
import com.coorddisplay.coordhud.CommonClass;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CoordHUD {

    public CoordHUD(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
    }
}