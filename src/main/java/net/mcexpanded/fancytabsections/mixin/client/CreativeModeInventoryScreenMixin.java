package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.FTSInternal;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin
{
    @Shadow
    private static CreativeModeTab selectedTab;

    @Shadow
    protected abstract void refreshCurrentTabContents(Collection<ItemStack> items);

    @Inject(method = "renderBg", at = @At("TAIL"))
    private void fts$renderBanners(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci)
    {
        ResourceLocation tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);

        if (BannerRenderer.CURRENT_TAB == null || !BannerRenderer.CURRENT_TAB.equals(tab))
        {
            BannerRenderer.CURRENT_TAB = tab;
            FTSInternal.applyItems(selectedTab);
            this.refreshCurrentTabContents(selectedTab.getDisplayItems());
        }

        if (FancyTabSections.REGISTERED_TABS.containsKey(tab))
        {
            BannerRenderer.render((CreativeModeInventoryScreen) (Object) this, guiGraphics,
                    FancyTabSections.REGISTERED_TABS.get(tab), mouseX, mouseY);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void fts$slotClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir)
    {

    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void fts$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir)
    {
        if (button != 0) return;

        CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) (Object) this;

        //only run for tabs registered in FTS
        ResourceLocation tab = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(selectedTab);
        if (!FancyTabSections.REGISTERED_TABS.containsKey(tab)) return;

        if (BannerRenderer.isInBanner(self, mouseX, mouseY))
            if (!self.getMenu().getCarried().isEmpty())
            {
                self.getMenu().setCarried(ItemStack.EMPTY);
                cir.cancel();
                return;
            }

        if (!self.getMenu().getCarried().isEmpty()) return;

        List<Section<?>> sections = FancyTabSections.REGISTERED_TABS.get(tab);
        for (Section<?> section : sections)
        {
            if (section.collapsible() && BannerRenderer.isInToggle(self, section, mouseX, mouseY))
            {
                //toggle all
                if (Screen.hasShiftDown())
                {
                    if (FTSInternal.isCollapsed(section))
                        sections.stream().filter(Section::collapsible).forEach(o -> FTSInternal.expand(o, o.equals(section)));
                    else
                        sections.stream().filter(Section::collapsible).forEach(o -> FTSInternal.collapse(o, o.equals(section)));
                }
                //toggle clicked
                else
                {
                    FTSInternal.toggle(section);
                }

                //refresh tab
                FTSInternal.applyItems(selectedTab);
                this.refreshCurrentTabContents(selectedTab.getDisplayItems());

                cir.setReturnValue(true);
                return;
            }
        }
    }
}
