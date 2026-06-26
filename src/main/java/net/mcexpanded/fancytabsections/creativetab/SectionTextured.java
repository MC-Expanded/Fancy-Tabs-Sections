package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record SectionTextured(
        ResourceLocation id,
        Component title,
        ResourceLocation texture,
        int textColor,
        boolean collapsible,
        ConglomerateOfItems items
) implements Section {

    /** The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder */
    public static SectionTextured of(ResourceLocation id, Component title, int textColor, boolean collapsible, ConglomerateOfItems items) {
        return of(id, title, textColor, collapsible, items);
    }

    /** As {@link #of(ResourceLocation, Component, int, ConglomerateOfItems)}, with collapsible opt-in. */
    public static SectionTextured of(ResourceLocation id, Component title, int textColor, ConglomerateOfItems items, boolean collapsible) {
        return new SectionTextured(
                id,
                title,
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/fancy_tab_section/" + id.getPath() + ".png"),
                textColor,
                collapsible,
                items
        );
    }
}