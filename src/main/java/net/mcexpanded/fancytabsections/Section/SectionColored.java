package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

/**
 * @since 4.0
 */
public class SectionColored extends AbstractSectionWithTitle<SectionColored>
{
    boolean collapsible = true;
    int verticalSize = 162;
    int horizontalSize = 18;
    int offsetX = 0;
    int offsetY = 0;
    ConglomerateOfItems items = ConglomerateOfItems.create();
    public int bannerColor = 0xFF45BB77;
    public int bannerBorderColor = brighten(bannerColor, 0.2f);

    public SectionColored create(Identifier id)
    {
        return new SectionColored(id);
    }

    public SectionColored(Identifier id)
    {
        super(id);
        this.title = Component.translatable("section." + id.getNamespace() + "." + id.getPath());
    }

    public SectionColored(Identifier id,
                          Component title,
                          int bannerColor,
                          int textColor,
                          int textOutline,
                          boolean textShadow,
                          boolean collapsible,
                          int verticalSize,
                          int horizontalSize,
                          ConglomerateOfItems items)
    {
        super(id);
        this.title = title;
        this.bannerColor = bannerColor;
        this.textColor = textColor;
        this.textOutline = textOutline;
        this.textShadow = textShadow;
        this.collapsible = collapsible;
        this.verticalSize = verticalSize;
        this.horizontalSize = horizontalSize;
        this.items = items;
    }

    /**
     * @since 1.0
     */
    public SectionColored(
            Identifier id,
            Component title,
            int bannerColor,
            int textColor,
            boolean textShadow,
            boolean collapsible,
            ConglomerateOfItems items)
    {
        this(id, title, bannerColor, textColor, 0x00000000, textShadow, collapsible, 162, 18, items);
    }

    /**
     * @since 1.0
     */
    public SectionColored(Identifier id, Component title, int bannerColor, int textColor, ConglomerateOfItems items)
    {
        this(id, title, bannerColor, textColor, true, true, items);
    }

    @ApiStatus.Internal
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
    @ApiStatus.Internal
    public void render(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        //render border
        guiGraphics.fill(topLeftX + offsetX, topLeftY + offsetY, topLeftX + 162 - offsetX, topLeftY + 18 - offsetY, bannerBorderColor);

        //render flat color background
        guiGraphics.fill(topLeftX + offsetX + 1, topLeftY + offsetY + 1, topLeftX + 161 - offsetX, topLeftY + 17 - offsetY, bannerColor);

        super.render(guiGraphics, font, topLeftX, topLeftY);
    }

    @Override
    public Identifier id()
    {
        return id;
    }

    @Override
    public ConglomerateOfItems items()
    {
        return items;
    }

    public SectionColored setCollapsible(boolean collapsible)
    {
        this.collapsible = collapsible;
        return this;
    }

    public SectionColored setBannerColor(int bannerColor)
    {
        this.bannerColor = bannerColor;
        this.bannerBorderColor = brighten(bannerColor, 0.2f);
        return this;
    }

    public SectionColored setVerticalSize(int verticalSize)
    {
        this.verticalSize = verticalSize;
        return this;
    }

    public SectionColored setHorizontalSize(int horizontalSize)
    {
        this.horizontalSize = horizontalSize;
        return this;
    }

    public SectionColored setItems(ConglomerateOfItems items)
    {
        this.items = items;
        return this;
    }

    /**
     * @since 5.0
     */
    public SectionColored setBannerBorderColor(int bannerBorderColor)
    {
        this.bannerBorderColor = bannerBorderColor;
        return this;
    }

    /**
     * @since 5.0
     */
    public SectionColored setOffsetX(int offsetX)
    {
        this.offsetX = offsetX;
        return this;
    }

    /**
     * @since 5.0
     */
    public SectionColored setOffsetY(int offsety)
    {
        this.offsetY = offsety;
        return this;
    }

    /**
     * @since 5.0
     */
    public SectionColored setTextureInsideRow()
    {
        horizontalSize = 160;
        verticalSize = 16;
        offsetX = 1;
        offsetY = 1;
        return this;
    }
}