package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public record SectionColored(
        ResourceLocation id,
        Component title,
        int bannerColor,   // ARGB
        int textColor,
        List<Item> items
) implements Section {}