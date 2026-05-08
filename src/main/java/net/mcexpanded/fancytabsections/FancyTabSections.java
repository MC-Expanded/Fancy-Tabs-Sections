package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.ModCreativeTabs;
import net.mcexpanded.fancytabsections.creativetab.Section;
import net.mcexpanded.fancytabsections.creativetab.SectionColored;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
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

    public FancyTabSections(IEventBus modEventBus, ModContainer modContainer)
    {
        ModCreativeTabs.register(modEventBus);
    }

    public static final Map<Identifier, List<Section>> map = new HashMap<>();



    public static void addSection(Identifier tab, Section section)
    {
        if (map.containsKey(tab))
        {
            List<Section> list = new ArrayList<>(map.get(tab));
            list.add(section);
            map.put(tab, List.copyOf(list));
        }
        else
        {
            map.put(tab, List.of(section));
        }
    }
}
