package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public record SectionAnimatedTextured(
        ResourceLocation id,
        Component title,
        ResourceLocation texture,
        int frames,
        float frameTimeInMS,
        int textColor,
        boolean textShadow,
        boolean collapsible,
        ConglomerateOfItems items
) implements Section
{

    /**
     * The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder
     */
    public static SectionAnimatedTextured of(ResourceLocation id, Component title, int frames, float frameTimeInMS, int textColor, boolean textShadow, boolean collapsible, ConglomerateOfItems items)
    {
        return new SectionAnimatedTextured(
                id,
                title,
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/fancy_tab_section/" + id.getPath() + ".png"),
                frames,
                frameTimeInMS,
                textColor,
                textShadow,
                collapsible,
                items
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        int time = (int) ((int) System.currentTimeMillis() / frameTimeInMS);

        int numberOfFrame = time % frames;

        float vOffset = numberOfFrame * 18;

        guiGraphics.blit(
                texture, topLeftX, topLeftY,
                162, 18,
                0, vOffset,
                162, 18,
                162, 18 * frames
        );

        guiGraphics.drawString(font, title, topLeftX + 4, topLeftY + 5, textColor, textShadow);
    }
}