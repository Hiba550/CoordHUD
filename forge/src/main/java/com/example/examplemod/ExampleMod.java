package com.coorddisplay.coordhud;

import com.coorddisplay.coordhud.Constants;
import com.coorddisplay.coordhud.CommonClass;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class CoordHUD {

    public CoordHUD() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
    }
}