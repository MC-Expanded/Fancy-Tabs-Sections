package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.Section;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;
import net.mcexpanded.fancytabsections.mixin.CreativeModeTabAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mod(FancyTabSections.MOD_ID)
public class FancyTabSections
{
    public static final String MOD_ID = "fancytabsections";

    /** An example of an implementation can be found on FTSExampleMod */
    public FancyTabSections(IEventBus modEventBus)
    {
        //load all itemStacks on event to make sure every mod has already added their items
        modEventBus.addListener(BuildCreativeModeTabContentsEvent.class, o -> TabLayout.build());
    }

    public static final Map<ResourceLocation, List<Section>> SECTIONS_MAP = new HashMap<>();
    public static final Map<ResourceLocation, List<ItemStack>> ITEMS_MAP = new HashMap<>();
    /** Full (collapse-agnostic) search-tab contents per tab id. */
    public static final Map<ResourceLocation, Set<ItemStack>> SEARCH_MAP = new HashMap<>();

    public static void applyItems(CreativeModeTab tab)
    {
        ResourceLocation rl = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab);
        List<ItemStack> display = ITEMS_MAP.get(rl);
        if (display == null) return;

        ((CreativeModeTabAccessor) tab).setDisplayItems(display);

        Set<ItemStack> search = SEARCH_MAP.get(rl);
        if (search != null)
        {
            ((CreativeModeTabAccessor) tab).setDisplayItemsSearchTab(new LinkedHashSet<>(search));
        }
        else
        {
            ((CreativeModeTabAccessor) tab).setDisplayItemsSearchTab(
                    display.stream()
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toCollection(LinkedHashSet::new))
            );
        }
    }

    /** Adds a Fancy Tab Section to the given CreativeModeTab Identifier */
    public static void addSection(ResourceLocation tab, Section section)
    {
        if (SECTIONS_MAP.containsKey(tab))
        {
            List<Section> list = new ArrayList<>(SECTIONS_MAP.get(tab));
            list.add(section);
            SECTIONS_MAP.put(tab, List.copyOf(list));
        }
        else
        {
            SECTIONS_MAP.put(tab, List.of(section));
        }
    }
}
