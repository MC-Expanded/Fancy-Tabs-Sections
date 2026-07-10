package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * @since 3.0
 */
public interface Section {
    ResourceLocation id();
    default boolean collapsible() { return false; }
    ConglomerateOfItems items();

    void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY);
}