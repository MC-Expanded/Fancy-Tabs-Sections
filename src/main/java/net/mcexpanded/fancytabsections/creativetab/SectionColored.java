package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public record SectionColored(
        Identifier id,
        Component title,
        int bannerColor,   // ARGB
        int textColor,
        List<Item> items
) implements Section {}