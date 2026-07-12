package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

/**
 * @since 1.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.Section}
 */
public interface Section extends net.mcexpanded.fancytabsections.Section.Section<Section>
{
    Identifier id();

    ConglomerateOfItems items();

    void render(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY);
}