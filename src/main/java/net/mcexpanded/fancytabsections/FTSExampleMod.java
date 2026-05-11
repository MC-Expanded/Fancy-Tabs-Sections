package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.mcexpanded.fancytabsections.creativetab.SectionColored;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(FancyTabSections.MOD_ID)
public class FTSExampleMod
{
    public static Identifier rl(String path)
    {
        return Identifier.fromNamespaceAndPath(FancyTabSections.MOD_ID, path);
    }

    /**
     * Example of an implementation of FancyTabSections
     */
    public FTSExampleMod(IEventBus modEventBus)
    {
        //prevents example mod from running adding stuff
        if (true) return;

        //register our creative mode tabs as usual, leaving them empty
        FTSExampleCreativeModeTabs.register(modEventBus);

        //register our custom items
        FTSExampleModItems.register(modEventBus);

        //create ItemStack to be added below
        ItemStackTemplate exampleItemStack = new ItemStackTemplate(Items.STONE_AXE);
        //exampleItemStack.apply(DataComponentPatch.builder().set(DataComponents.DAMAGE, 20).build());

        /* This adds a solid coloured section to the creative mode tab registered under the ID "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                new SectionColored(
                        //identifier of the section
                        rl("apples"),
                        //Title to display in the "empty row" of the section
                        Component.translatable("itemGroup.fancytabsections.apples"),
                        //background color of the "empty row" - ARGB
                        0xFF1a1a2e,
                        //text color - ARGB
                        0xFFFFFFFF,
                        //creates a new conglomerate - items are listed in the order they are added
                        ConglomerateOfItems.create()
                                //adds a modded item
                                .add(FTSExampleModItems.MISSINGNO)
                                //adds an item
                                .add(Items.GOLDEN_APPLE)
                                //adds an ItemStack
                                .add(() ->
                                {
                                    ItemStack itemStack = new ItemStack(Items.STONE_AXE);
                                    itemStack.setDamageValue(20);
                                    return itemStack;
                                })
                                //adds every item in the DeferredRegister
                                .add(FTSExampleModItems.ITEMS)
                )
        );

        /* This adds a textured coloured section to the creative mode tab registered under the ID "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                SectionTextured.of(
                        rl("shiny"),
                        Component.literal(""),
                        0xFFFFFFFF,
                        ConglomerateOfItems.create()
                                .add(Items.DIAMOND)
                                .add(Items.GOLDEN_HOE)
                                .add(Items.AMETHYST_SHARD)
                )
        );

        /* This adds a third section to "shiny_things" */
        FancyTabSections.addSection(rl("shiny_things"),
                new SectionColored(
                        rl("even_more_shiny"),
                        Component.translatable("itemGroup.fancytabsections.even_more_shiny"),
                        0xFF1a2e1a,
                        0xFFFFFFFF,
                        ConglomerateOfItems.create()
                                .add(Items.EMERALD)
                                .add(Items.GLISTERING_MELON_SLICE)
                                .add(Items.IRON_INGOT)
                                .add(Items.GOLDEN_CARROT)
                                .add(Items.HONEY_BOTTLE)
                                .add(Items.RAW_COPPER)
                                .add(Items.RAW_GOLD)
                                .add(Items.NETHERITE_SCRAP)
                                .add(Items.SNOWBALL)
                                .add(Items.PRISMARINE_CRYSTALS)
                                .add(Items.PRISMARINE_SHARD)
                                .add(Items.TOTEM_OF_UNDYING)
                )
        );

        /* Add a  section to our second creative mode tab "dirt_tools"*/
        FancyTabSections.addSection(rl("dirty_tools"),
                new SectionColored(
                        rl("very_dirty_tools"),
                        Component.translatable("itemGroup.livestreammod.very_dirty_tools"),
                        0xFF1a1a2e,
                        0xFFFFFFFF,
                        ConglomerateOfItems.create()
                                .add(Items.WOODEN_AXE)
                                .add(Items.WOODEN_HOE)
                )
        );

        /* Add another section to "dirty_tools" */
        FancyTabSections.addSection(rl("dirty_tools"),
                new SectionColored(
                        rl("decently_dirty_tools"),
                        Component.translatable("itemGroup.livestreammod.decently_dirty_tools"),
                        0xFF1a1a2e,
                        0xFFFFFFFF,
                        ConglomerateOfItems.create()
                                .add(Items.IRON_SWORD)
                                .add(Items.STONE_SHOVEL)
                                .add(Items.STONE_HOE)
                )
        );
    }

    public interface FTSExampleCreativeModeTabs
    {
        DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
                DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, FancyTabSections.MOD_ID);

        Supplier<CreativeModeTab> SHINY_THINGS = CREATIVE_MODE_TABS.register("shiny_things", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(Items.DIAMOND))
                .title(Component.translatable("itemGroup.fts.shiny_things"))
                .displayItems((params, output) ->
                {
                    // Intentionally empty as we add the items through FancyTabsSections#addSection
                })
                .build());

        Supplier<CreativeModeTab> TOOLS = CREATIVE_MODE_TABS.register("dirty_tools", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(Items.WOODEN_PICKAXE))
                .title(Component.translatable("itemGroup.fts.dirty_tools"))
                .displayItems((params, output) ->
                {
                    // Intentionally empty as we add the items through FancyTabsSections#addSection
                })
                .build());

        static void register(IEventBus eventBus)
        {
            CREATIVE_MODE_TABS.register(eventBus);
        }
    }

    public interface FTSExampleModItems
    {
        DeferredRegister.Items ITEMS = DeferredRegister.createItems(FancyTabSections.MOD_ID);

        DeferredItem<Item> MISSINGNO = ITEMS.registerItem("missingno", Item::new);
        DeferredItem<Item> UNKNOWN = ITEMS.registerItem("unknown", Item::new);

        static void register(IEventBus eventBus)
        {
            ITEMS.register(eventBus);
        }
    }


}
