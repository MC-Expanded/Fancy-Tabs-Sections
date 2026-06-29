package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record SectionColored(
        ResourceLocation id,
        Component title,
        int bannerColor,   // ARGB
        int textColor,
        boolean collapsible,
        ConglomerateOfItems items
) implements Section
{
    public SectionColored(ResourceLocation id, Component title, int bannerColor, int textColor, ConglomerateOfItems items) {
        this(id, title, bannerColor, textColor, true, items);
    }
}