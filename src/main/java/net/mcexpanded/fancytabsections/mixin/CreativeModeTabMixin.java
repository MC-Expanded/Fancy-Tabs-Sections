package net.mcexpanded.fancytabsections.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.mcexpanded.fancytabsections.FTSInternal;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin
{
    @WrapMethod(method = "buildContents")
    private void fts$buildContents(CreativeModeTab.ItemDisplayParameters parameters, Operation<Void> original)
    {
        CreativeModeTab self = (CreativeModeTab) (Object) this;

        ResourceLocation rl = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(self);

        if (FancyTabSections.REGISTERED_TABS.containsKey(rl))
        {
            ((CreativeModeTabAccessor) self).setDisplayItems(List.of(Items.DIAMOND.getDefaultInstance()));

            FTSInternal.applyItems(self);
        }
        else
            original.call(parameters);
    }
}