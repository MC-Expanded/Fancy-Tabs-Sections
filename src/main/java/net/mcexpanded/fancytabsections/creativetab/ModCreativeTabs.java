package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, FancyTabSections.MOD_ID);

    public static final Supplier<CreativeModeTab> CORE = CREATIVE_MODE_TABS.register("msgwoft", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(Items.DIAMOND))
            .title(Component.translatable("itemGroup.msgwoft"))
            .displayItems((params, output) ->
            {
                // Intentionally empty — CreativeModeTabMixin overrides buildContents
            })
            .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void init()
    {
        List<Section> sections = List.of();
        TabLayout.build(sections); // populates CACHED_ITEMS and SECTION_ROW
    }
}