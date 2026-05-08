package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.creativetab.ModCreativeTabs;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

    @Inject(method = "extractBackground", at = @At("TAIL"))
    private void msgwoft$renderBanners(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        Identifier tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        if (FancyTabSections.map.containsKey(tab))
        {
            BannerRenderer.render((CreativeModeInventoryScreen) (Object) this, graphics, FancyTabSections.map.get(tab));
        }
    }
}