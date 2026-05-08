package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.creativetab.ModCreativeTabs;
import net.mcexpanded.fancytabsections.creativetab.SectionColored;
import net.mcexpanded.fancytabsections.creativetab.SectionTextured;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;

@EventBusSubscriber(modid = FancyTabSections.MOD_ID)
public class TestMod
{
    public static Identifier rl(String ns, String path)
    {
        return Identifier.fromNamespaceAndPath(ns, path);
    }

    public static Identifier rl(String path)
    {
        return Identifier.fromNamespaceAndPath(FancyTabSections.MOD_ID, path);
    }

    public static class Events
    {
        @SubscribeEvent
        private static void addCreative(BuildCreativeModeTabContentsEvent event)
        {
            FancyTabSections.addSection(rl("test"),
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

            FancyTabSections.addSection(rl("test"),
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

            FancyTabSections.addSection(rl("test"),
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

            FancyTabSections.addSection(rl("test"),
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
