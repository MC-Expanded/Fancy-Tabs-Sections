package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

/**
 * @since 4.0
 */
public class SectionColored extends AbstractSectionWithTitle<SectionColored>
{
    boolean collapsible = true;
    int verticalSize = 162;
    int horizontalSize = 18;
    ConglomerateOfItems items = ConglomerateOfItems.create();
    public int bannerColor = 0xFF45BB77;

    public SectionColored create(ResourceLocation id)
    {
        return new SectionColored(id);
    }

    public SectionColored(ResourceLocation id)
    {
        super(id);
        this.title = Component.translatable("section." + id.getNamespace() + "." + id.getPath());
    }



    public SectionColored(ResourceLocation id,
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
            ResourceLocation id,
            Component title,
            int bannerColor,
            int textColor,
            boolean textShadow,
            boolean collapsible,
            ConglomerateOfItems items)
    {
        this(id, title, bannerColor, textColor, 0x00ffffff, textShadow, collapsible, 162, 18, items);
    }

    /**
     * @since 1.0
     */
    public SectionColored(ResourceLocation id, Component title, int bannerColor, int textColor, ConglomerateOfItems items)
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
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        //render border
        guiGraphics.fill(topLeftX, topLeftY, topLeftX + 162, topLeftY + 18, brighten(bannerColor, 0.2f));

        //render flat color background
        guiGraphics.fill(topLeftX + 1, topLeftY + 1, topLeftX + 161, topLeftY + 17, bannerColor);

        super.render(guiGraphics, font, topLeftX, topLeftY);
    }

    @Override
    public ResourceLocation id()
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
}