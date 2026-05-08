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

    public static Identifier rl(String ns, String path)
    {
        return Identifier.fromNamespaceAndPath(ns, path);
    }

    public static Identifier rl(String path)
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

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

    @EventBusSubscriber(modid = MOD_ID)
    public static class Events
    {
        @SubscribeEvent
        private static void addCreative(BuildCreativeModeTabContentsEvent event)
        {
            addSection(rl("test"),
                    new SectionColored(
                            "core",
                            Component.translatable("itemGroup.livestreammod.core"),
                            0xFF1a1a2e,   // ARGB banner background
                            0xFFFFFFFF,
                            List.of(
                                    Items.ANDESITE,
                                    Items.APPLE
                            )
                    )
            );

            addSection(rl("test"),
                    new SectionColored(
                            "modules",
                            Component.translatable("itemGroup.livestreammod.dimensional_vortex"),
                            0xFF1a2e1a,
                            0xFFFFFFFF,
                            List.of(
                                    Items.COPPER_PICKAXE
                            )
                    )
            );

            addSection(rl("test"),
                    new SectionColored(
                            "copperbackport",
                            Component.translatable("itemGroup.livestreammod.copperbackport"),
                            0xFFcc6600,
                            0xFFFFFFFF,
                            List.of(
                                    Items.COPPER_CHESTPLATE,
                                    Items.LLAMA_SPAWN_EGG
                            )
                    )
            );

            addSection(rl("test"),
                    SectionTextured.of(
                            FancyTabSections.MOD_ID,
                            "beta_tools",
                            Component.literal("Beta Stage - Unfinished"),
                            0xFFFFFFFF,
                            List.of(
                                    Items.LLAMA_SPAWN_EGG,
                                    Blocks.LAPIS_ORE.asItem()
                            )
                    )
            );

            ModCreativeTabs.init();
        }
    }
}
