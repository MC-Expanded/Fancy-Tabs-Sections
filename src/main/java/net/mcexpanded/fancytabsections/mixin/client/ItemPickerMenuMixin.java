package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.ItemPickerMenu.class)
public abstract class ItemPickerMenuMixin
{

    @Shadow
    protected abstract int getRowIndexForScroll(float f);

    @Inject(method = "scrollTo", at = @At("HEAD"))
    private void fts$scrollTo(float f, CallbackInfo ci)
    {
        BannerRenderer.CURRENT_ROW = this.getRowIndexForScroll(f);
    }
}