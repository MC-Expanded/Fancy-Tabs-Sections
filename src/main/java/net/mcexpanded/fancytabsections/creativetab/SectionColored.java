package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

/**
 * @since 2.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionColored}
 */
@Deprecated(forRemoval = true, since = "4.0")
public class SectionColored implements Section
{
    private final net.mcexpanded.fancytabsections.Section.SectionColored delegated;

    /**
     * v2.0 constructor for backwards compatibility
     *
     * @since 2.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionColored}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public SectionColored(ResourceLocation id, Component title, int bannerColor, int textColor, ConglomerateOfItems items)
    {
        delegated = new net.mcexpanded.fancytabsections.Section.SectionColored(
                id, title, bannerColor, textColor, false, true, items
        );
    }

    /**
     * v1.0 constructor for backwards compatibility
     *
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionColored}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public SectionColored(ResourceLocation id, Component title, int bannerColor, int textColor, List<Item> items)
    {
        ConglomerateOfItems con = ConglomerateOfItems.create();
        items.forEach(con::add);

        delegated = new net.mcexpanded.fancytabsections.Section.SectionColored(id, title, bannerColor, textColor, con);
    }


    @Override
    public boolean equals(Object o)
    {
        return (o instanceof SectionColored other) && delegated.equals(other.delegated);
    }

    @Override
    public int hashCode()
    {
        return delegated.hashCode();
    }

    @Override
    public String toString()
    {
        return delegated.toString();
    }

    /**
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Override
    public ResourceLocation id()
    {
        return delegated.id();
    }

    /**
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Override
    public ConglomerateOfItems items()
    {
        return delegated.items();
    }

    /**
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {

    }
}


