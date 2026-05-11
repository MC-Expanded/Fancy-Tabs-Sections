package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public record SectionColored(
        Identifier id,
        Component title,
        int bannerColor,   // ARGB
        int textColor,
        ConglomerateOfItems items
) implements Section {}