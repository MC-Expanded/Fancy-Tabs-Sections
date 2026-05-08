package net.mcexpanded.fancytabsections.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.LinkedHashSet;
import java.util.List;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin
{

    @WrapMethod(method = "buildContents")
    private void buildContents(CreativeModeTab.ItemDisplayParameters parameters, Operation<Void> original)
    {
        CreativeModeTab self = (CreativeModeTab) (Object) this;

        ResourceLocation rl = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(self);

        if (FancyTabSections.SECTIONS_MAP.containsKey(rl))
        {
            // Bypass vanilla's output validation — set fields directly
            List<ItemStack> display = FancyTabSections.ITEMS_MAP.get(rl);

            ((CreativeModeTabAccessor) self).setDisplayItems(display);
            ((CreativeModeTabAccessor) self).setDisplayItemsSearchTab(
                    display.stream()
                            .filter(s -> !s.isEmpty())
                            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new))
            );
        }
        else
        {
            original.call(parameters);
        }
    }
}