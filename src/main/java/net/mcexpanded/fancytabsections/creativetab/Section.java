package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * @since 1.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.Section}
 */
public interface Section extends net.mcexpanded.fancytabsections.Section.Section<Section>
{
    ResourceLocation id();

    ConglomerateOfItems items();

    void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY);
}