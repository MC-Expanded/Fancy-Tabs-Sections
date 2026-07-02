package net.mcexpanded.fancytabsections.mixin.client;

import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen$CustomCreativeSlot")
public abstract class CustomCreativeSlotMixin extends Slot
{
    public CustomCreativeSlotMixin() { super(null, 0, 0, 0); }

    @Override
    public boolean isHighlightable()
    {
        int absoluteRow = BannerRenderer.CURRENT_ROW + this.index / 9;
        if (BannerRenderer.CURRENT_TAB != null && TabLayout.isBannerRow(BannerRenderer.CURRENT_TAB, absoluteRow))
        {
            return false;
        }
        return true;
    }
}
