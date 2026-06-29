package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public interface Section {
    ResourceLocation id();
    Component title();
    int textColor();
    default boolean collapsible() { return false; }
    ConglomerateOfItems items();
}