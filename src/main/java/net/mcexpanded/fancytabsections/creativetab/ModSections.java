package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class ModSections {

    public static List<Section> ALL = List.of();

    public static List<Section> build() {
        ALL = List.of(
                new SectionColored(
                        "core",
                        Component.translatable("itemGroup.livestreammod.core"),
                        0xFF1a1a2e,   // ARGB banner background
                        0xFFFFFFFF,
                        List.of(
                                Items.ANDESITE,
                                Items.APPLE
                        )
                ),
                new SectionColored(
                        "modules",
                        Component.translatable("itemGroup.livestreammod.dimensional_vortex"),
                        0xFF1a2e1a,
                        0xFFFFFFFF,
                        List.of(
                                Items.COPPER_PICKAXE
                        )
                ),
                new SectionColored(
                        "copperbackport",
                        Component.translatable("itemGroup.livestreammod.copperbackport"),
                        0xFFcc6600,
                        0xFFFFFFFF,
                        List.of(
                                Items.COPPER_CHESTPLATE,
                                Items.LLAMA_SPAWN_EGG
                        )
                ),
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
        return ALL;
    }
}