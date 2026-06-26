package net.mcexpanded.fancytabsections.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;

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
            FancyTabSections.applyItems(self);
        }
        else
        {
            original.call(parameters);
        }
    }
}