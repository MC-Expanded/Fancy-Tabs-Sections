package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public interface Section {
    Identifier id();
    Component title();
    int textColor();
    ConglomerateOfItems items();
}