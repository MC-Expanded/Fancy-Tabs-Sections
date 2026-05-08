package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.SectionColored;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

@Mod(FancyTabSections.MOD_ID)
public class FTSExampleMod
{
    public static ResourceLocation rl(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, path);
    }

    /** Example of an implementation of FancyTabSections */
    public FTSExampleMod(IEventBus modEventBus)
    {
        //if(true) return; //prevents lib from actually adding stuff

        //register our creative mode tabs as usual, leaving them empty
        FTSExampleCreativeModeTabs.register(modEventBus);

        /* This adds a solid coloured section to the creative mode tab registered under the ID "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                new SectionColored(
                        rl("apples"),
                        Component.translatable("itemGroup.fancytabsections.apples"),
                        0xFF1a1a2e,
                        0xFFFFFFFF,
                        List.of(
                                Items.APPLE,
                                Items.GOLDEN_APPLE,
                                Items.ENCHANTED_GOLDEN_APPLE
                        )
                )
        );

        /* This adds a textured coloured section to the creative mode tab registered under the ID "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                SectionTextured.of(
                        rl("shiny"),
                        Component.literal(""),
                        0xFFFFFFFF,
                        List.of(
                                Items.DIAMOND,
                                Items.GOLDEN_HOE,
                                Items.AMETHYST_SHARD
                        )
                )
        );

        /* This adds a third section to "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                new SectionColored(
                        rl("even_more_shiny"),
                        Component.translatable("itemGroup.fancytabsections.even_more_shiny"),
                        0xFF1a2e1a,
                        0xFFFFFFFF,
                        List.of(
                                Items.EMERALD,
                                Items.GLISTERING_MELON_SLICE,
                                Items.IRON_INGOT,
                                Items.GOLDEN_CARROT,
                                Items.HONEY_BOTTLE,
                                Items.RAW_COPPER,
                                Items.RAW_GOLD,
                                Items.NETHERITE_SCRAP,
                                Items.SNOWBALL,
                                Items.PRISMARINE_CRYSTALS,
                                Items.PRISMARINE_SHARD,
                                Items.TOTEM_OF_UNDYING
                        )
                )
        );

        /* Add a  section to our second creative mode tab "dirt_tools"*/
        FancyTabSections.addSection(rl("dirty_tools"),
                new SectionColored(
                        rl("very_dirty_tools"),
                        Component.translatable("itemGroup.livestreammod.very_dirty_tools"),
                        0xFF1a1a2e,
                        0xFFFFFFFF,
                        List.of(
                                Items.WOODEN_AXE,
                                Items.WOODEN_HOE
                        )
                )
        );

        /* Add a  */
        FancyTabSections.addSection(rl("dirty_tools"),
                new SectionColored(
                        rl("decently_dirty_tools"),
                        Component.translatable("itemGroup.livestreammod.decently_dirty_tools"),
                        0xFF1a1a2e,
                        0xFFFFFFFF,
                        List.of(
                                Items.IRON_SWORD,
                                Items.STONE_SHOVEL,
                                Items.STONE_HOE
                        )
                )
        );
    }

    public static class FTSExampleCreativeModeTabs
    {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, FancyTabSections.MOD_ID);

        public static final Supplier<CreativeModeTab> SHINY_THINGS = CREATIVE_MODE_TABS.register("shiny_things", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(Items.DIAMOND))
                .title(Component.translatable("itemGroup.fts.shiny_things"))
                .displayItems((params, output) ->
                {
                    // Intentionally empty as we add the items through FancyTabsSections#addSection
                })
                .build());

        public static final Supplier<CreativeModeTab> TOOLS = CREATIVE_MODE_TABS.register("dirty_tools", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(Items.WOODEN_PICKAXE))
                .title(Component.translatable("itemGroup.fts.dirty_tools"))
                .displayItems((params, output) ->
                {
                    // Intentionally empty as we add the items through FancyTabsSections#addSection
                })
                .build());

        public static void register(IEventBus eventBus)
        {
            CREATIVE_MODE_TABS.register(eventBus);
        }
    }


}
