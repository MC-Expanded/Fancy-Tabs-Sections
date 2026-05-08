package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.Section;
import net.mcexpanded.fancytabsections.creativetab.TabLayout;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final Map<Identifier, List<Section>> SECTIONS_MAP = new HashMap<>();
    public static final Map<Identifier, List<ItemStack>> ITEMS_MAP = new HashMap<>();

    /** Adds a Fancy Tab Section to the given CreativeModeTab Identifier */
    public static void addSection(Identifier tab, Section section)
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
