package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin
{

    @Shadow
    private static CreativeModeTab selectedTab;

    @Inject(method = "renderBg", at = @At("TAIL"))
    private void msgwoft$renderBanners(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci)
    {
        ResourceLocation tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        if (FancyTabSections.SECTIONS_MAP.containsKey(tab))
        {
            BannerRenderer.render((CreativeModeInventoryScreen) (Object) this, guiGraphics, FancyTabSections.SECTIONS_MAP.get(tab));
        }
    }
}