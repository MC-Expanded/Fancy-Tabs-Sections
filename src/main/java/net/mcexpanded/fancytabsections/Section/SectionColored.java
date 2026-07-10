package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @since 3.0
 */
public record SectionColored(
        ResourceLocation id,
        Component title,
        int bannerColor,   // ARGB
        int textColor,
        boolean textShadow,
        boolean collapsible,
        ConglomerateOfItems items
) implements Section
{
    public SectionColored(ResourceLocation id, Component title, int bannerColor, int textColor, ConglomerateOfItems items)
    {
        this(id, title, bannerColor, textColor, true, true, items);
    }

    private static int brighten(int argb, float amount)
    {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;
        r = (int) (r + (255 - r) * amount);
        g = (int) (g + (255 - g) * amount);
        b = (int) (b + (255 - b) * amount);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        //render border
        guiGraphics.fill(topLeftX, topLeftY, topLeftX + 162, topLeftY + 18, brighten(bannerColor, 0.2f));

        //render flat color background
        guiGraphics.fill(topLeftX + 1, topLeftY + 1, topLeftX + 161, topLeftY + 17, bannerColor);

        guiGraphics.drawString(font, title, topLeftX + 4, topLeftY + 5, textColor, textShadow);
    }
}