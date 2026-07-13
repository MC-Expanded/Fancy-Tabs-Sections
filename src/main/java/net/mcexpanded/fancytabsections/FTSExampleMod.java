package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.Section.SectionAnimatedTextured;
import net.mcexpanded.fancytabsections.Section.SectionColored;
import net.mcexpanded.fancytabsections.Section.SectionTextured;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

/**
 * @since 1.0
 */
@ApiStatus.Internal
public class FTSExampleMod
{
    /**
     * Helper method for creation of Identifiers under the fancytabsections namespace
     * @return A Identifier in the format fancytabsections:[path]
     */
    public static Identifier rl(String path)
    {
        return Identifier.fromNamespaceAndPath(FancyTabSections.MOD_ID, path);
    }

    /**
     * Example of an implementation of FancyTabSections
     */
    public FTSExampleMod(IEventBus modEventBus)
    {
        //prevents example mod from running in non-dev environment
        if (FMLLoader.getCurrent().isProduction()) return;

        //register a CreativeModeTab, either using FancyTabSection's helper, or doing our own setup as usual
        FancyTabSections.registerCreativeModeTab(modEventBus, rl("fancy_things"), Items.DIAMOND);
        //register a second CreativeModeTab
        FancyTabSections.registerCreativeModeTab(modEventBus, rl("dirty_tools"), Items.STONE_AXE);

        //register our custom items
        FTSExampleModItems.register(modEventBus);

        //this adds a solid coloured section to the CreativeModeTab registered under the ID "fancytabsections:dirty_tools"
        FancyTabSections.addSection(rl("dirty_tools"),
                //identifier of the section
                new SectionColored(rl("very_dirty_tools"))
                        //title to display in the "empty row" (banner) of the section
                        //by default the title will use the translation key `section.[namespace].[path]`, just as shown here
                        .setTitle(Component.translatable("section.fancytabsections.very_dirty_tools"))
                        //background color of the "empty row" - ARGB
                        .setBannerColor(0xFF1a1a2e)
                        //text color - ARGB
                        .setTextColor(0xFFBBAA66)
                        //text shadow
                        .setTextShadow(true)

                        //adds an item
                        .add(Items.BRUSH)
                        //adds a modded item, using the DeferredItem<Item>
                        .add(FTSExampleModItems.MISSINGNO)
                        //adds an ItemStack
                        .add(() ->
                        {
                            ItemStack exampleItemStack = Items.STONE_AXE.getDefaultInstance();
                            exampleItemStack.setDamageValue(20);
                            return exampleItemStack;
                        })
                        //adds a registry dependent item
                        .add((registry) ->
                        {
                            //in this example we use the registry to access the minecraft:pickaxes tag, and all items from it!
                            return registry.lookup(Registries.ITEM)
                                    .map(lookup -> lookup.get(ItemTags.PICKAXES)
                                            .map(named -> named.stream()
                                                    .map(holder -> holder.value().getDefaultInstance()).toList()
                                            ).orElse(List.of()))
                                    .orElseGet(List::of);
                        })
        );

        //this adds a second section to our dirty_tools CreativeModeTab, this time using a texture for the banner
        FancyTabSections.addSection(rl("dirty_tools"),
                //when using the default texture location, it must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png
                new SectionTextured(rl("decently_dirty_tools"))
                        .setTextColor(0xFFFFFFFF)
                        .setTextOutline(0xFF555500)
                        .add(Items.IRON_SWORD)
                        .add(Items.STONE_SHOVEL)
                        .add(Items.STONE_HOE)
        );

        //Add an animated banner section to our second creative mode tab "shiny_things"
        FancyTabSections.addSection(rl("fancy_things"),
                //when using the default texture location, it must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png
                new SectionAnimatedTextured(rl("shiny_things"))
                        //defines how many frames our animation file has
                        .setFrames(18)
                        //defines the time each frame will stay on the screen for, in MS
                        .setFrameTimeInMS(200)

                        //adds an item
                        .add(Items.ENCHANTED_GOLDEN_APPLE)
                        //adds a list of ItemStacks
                        .add((registry) ->
                        {
                            ItemStack is1 = Items.GOLD_BLOCK.getDefaultInstance();
                            ItemStack is2 = Items.GOLD_INGOT.getDefaultInstance();
                            ItemStack is3 = Items.GOLD_NUGGET.getDefaultInstance();
                            ItemStack is4 = Items.GOLDEN_CARROT.getDefaultInstance();
                            ItemStack is5 = Items.GOLDEN_APPLE.getDefaultInstance();

                            List<ItemStack> list = List.of(is1, is2, is3, is4, is5);

                            list.forEach(is -> is.enchant(registry.holderOrThrow(Enchantments.UNBREAKING), 3));

                            return list;
                        })
        );

        // This adds a new section to "shiny_things" consisting of a (hopefully) populated item tag, and one without any items
        FancyTabSections.addSection(rl("fancy_things"),
                new SectionColored(rl("pretty_things"))
                        //adds all items of this TagKey<Item>
                        .addItemTag(BlockItemTags.FLOWERS.item())
                        //if tag has no items, none are added
                        .addItemTag(TagKey.create(Registries.ITEM, rl("unavailable_item_tag")))
        );
    }

    /**
     * Example ModItems class
     */
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
