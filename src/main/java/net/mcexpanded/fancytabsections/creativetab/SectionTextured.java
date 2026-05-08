package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public record SectionTextured(
        Identifier id,
        Component title,
        Identifier texture,
        int textColor,
        List<Item> items
) implements Section {

    /** The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png */
    public static SectionTextured of(Identifier id, Component title, int textColor, List<Item> items) {
        return new SectionTextured(
                id,
                title,
                Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/gui/fancy_tab_section/" + id.getPath() + ".png"),
                textColor,
                items
        );
    }
}