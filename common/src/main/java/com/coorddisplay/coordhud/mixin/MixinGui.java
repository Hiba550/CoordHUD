package com.coorddisplay.coordhud.mixin;

import com.coorddisplay.coordhud.client.HUDRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    
    @Inject(method = "render", at = @At("TAIL"))
    private void renderCoordHUD(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        HUDRenderer.renderHUD(guiGraphics);
    }
}
