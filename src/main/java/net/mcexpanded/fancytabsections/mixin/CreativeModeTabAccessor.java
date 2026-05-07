package net.mcexpanded.fancytabsections.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Collection;

@Mixin(CreativeModeTab.class)
public interface CreativeModeTabAccessor {
    @Accessor("displayItems")
    void setDisplayItems(Collection<ItemStack> items);

    @Accessor("displayItemsSearchTab")
    void setDisplayItemsSearchTab(java.util.Set<ItemStack> items);
}