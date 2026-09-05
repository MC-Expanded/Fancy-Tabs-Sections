package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * @since 3.0
 */
public class SectionTextured extends AbstractSectionWithTitle<SectionTextured>
{
    ResourceLocation texture;
    int horizontalSize = 162;
    int verticalSize = 18;
    int textureOffsetX = 0;
    int textureOffsetY = 0;

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        guiGraphics.blit(texture,
                topLeftX + textureOffsetX, topLeftY + textureOffsetY,
                0, 0,
                horizontalSize, verticalSize,
                horizontalSize, verticalSize);

        super.render(guiGraphics, font, topLeftX, topLeftY);
    }

    public SectionTextured create(ResourceLocation id)
    {
        return new SectionTextured(id);
    }

    public SectionTextured(ResourceLocation id)
    {
        super(id);
        this.texture = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/fancy_tab_section/" + id.getPath() + ".png");
    }

    /**
     * Backwards compatibility constructor for v3.0
     *
     * @since 3.0
     */
    public SectionTextured(ResourceLocation id, Component title, ResourceLocation texture,
                           int textColor, boolean textShadow, boolean collapsible, ConglomerateOfItems items)
    {
        super(id);
        this.title = title;
        this.texture = texture;
        this.textColor = textColor;
        this.textShadow = textShadow;
        this.collapsible = collapsible;
        this.items = items;
    }

    /**
     * The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder
     *
     * @since 3.0
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

    //setters

    /**
     * The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder
     *
     * @since 3.0
     */
    public SectionTextured setTexture(ResourceLocation texture)
    {
        this.texture = ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), "textures/gui/fancy_tab_section/" + texture.getPath() + ".png");
        return this;
    }

    public SectionTextured setTextureRaw(ResourceLocation texture)
    {
        this.texture = texture;
        return this;
    }

    public SectionTextured setTitle(Component title)
    {
        this.title = title;
        return this;
    }

    public SectionTextured setHorizontalSize(int horizontalSize)
    {
        this.horizontalSize = horizontalSize;
        return this;
    }

    public SectionTextured setVerticalSize(int verticalSize)
    {
        this.verticalSize = verticalSize;
        return this;
    }

    public SectionTextured setTextureOffset(int x, int y)
    {
        this.textureOffsetX = x;
        this.textureOffsetY = y;
        return this;
    }

    public SectionTextured setTextureOffsetX(int textureOffsetX)
    {
        this.textureOffsetX = textureOffsetX;
        return this;
    }

    public SectionTextured setTextureOffsetY(int textureOffsetY)
    {
        this.textureOffsetY = textureOffsetY;
        return this;
    }

    public SectionTextured setTextureInsideRow()
    {
        horizontalSize = 160;
        verticalSize = 16;
        textureOffsetX = 1;
        textureOffsetY = 1;
        return this;
    }
}