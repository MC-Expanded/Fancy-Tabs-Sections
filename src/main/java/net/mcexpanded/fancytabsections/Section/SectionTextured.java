package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record SectionTextured(
        ResourceLocation id,
        Component title,
        ResourceLocation texture,
        int textColor,
        boolean textShadow,
        boolean collapsible,
        ConglomerateOfItems items
) implements Section
{

    /**
     * The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder
     */
    public static SectionTextured of(ResourceLocation id, Component title, int textColor, boolean textShadow, boolean collapsible, ConglomerateOfItems items)
    {
        return new SectionTextured(
                id,
                title,
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/fancy_tab_section/" + id.getPath() + ".png"),
                textColor,
                textShadow,
                collapsible,
                items
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        guiGraphics.blit(texture, topLeftX, topLeftY, (int) (18 * System.currentTimeMillis()), 0, 162, 18, 162, 18, 162, 18);

        guiGraphics.drawString(font, title, topLeftX + 4, topLeftY + 5, textColor, textShadow);
    }
}