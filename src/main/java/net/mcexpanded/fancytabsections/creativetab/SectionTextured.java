package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public record SectionTextured(
        String id,
        Component title,
        Identifier texture,
        int textColor,
        List<Item> items
) implements Section {

    /** Builds the texture path automatically: [modId]:textures/gui/tab_overlay/[id].png */
    public static SectionTextured of(String modId, String id, Component title, int textColor, List<Item> items) {
        return new SectionTextured(
                id,
                title,
                Identifier.fromNamespaceAndPath(modId, "textures/gui/tab_overlay/" + id + ".png"),
                textColor,
                items
        );
    }
}