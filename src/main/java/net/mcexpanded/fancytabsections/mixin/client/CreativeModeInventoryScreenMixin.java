package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.creativetab.Section;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin
{

    @Shadow
    private static CreativeModeTab selectedTab;

    @Shadow
    protected abstract void selectTab(CreativeModeTab tab);

    @Inject(method = "renderBg", at = @At("TAIL"))
    private void msgwoft$renderBanners(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci)
    {
        ResourceLocation tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        BannerRenderer.CURRENT_TAB = tab;
        if (FancyTabSections.SECTIONS_MAP.containsKey(tab))
        {
            BannerRenderer.render((CreativeModeInventoryScreen) (Object) this, guiGraphics,
                    FancyTabSections.SECTIONS_MAP.get(tab), mouseX, mouseY);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void msgwoft$toggleSection(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir)
    {
        if (button != 0) return;

        ResourceLocation tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        if (!FancyTabSections.SECTIONS_MAP.containsKey(tab)) return;

        CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) (Object) this;

        for (Section section : FancyTabSections.SECTIONS_MAP.get(tab))
        {
            if (BannerRenderer.isInToggle(self, section, mouseX, mouseY))
            {
                TabLayout.toggle(section.id());
                TabLayout.build();
                FancyTabSections.applyItems(selectedTab);
                this.selectTab(selectedTab);

                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));

                cir.setReturnValue(true);
                return;
            }
        }
    }
}
