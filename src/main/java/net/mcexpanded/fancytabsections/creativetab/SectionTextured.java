package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.ModLoadingIssue;

import java.util.List;

/**
 * @since 2.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
 */
@Deprecated(forRemoval = true, since = "4.0")
public class SectionTextured
{
    private final net.mcexpanded.fancytabsections.Section.SectionTextured delegated;

    /**
     * v2.0 constructor for backwards compatibility
     *
     * @since 2.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public SectionTextured(ResourceLocation id, Component title, ResourceLocation texture, int textColor, ConglomerateOfItems items)
    {
        delegated = new net.mcexpanded.fancytabsections.Section.SectionTextured(
                id, title, texture, textColor, false, true, items
        );
    }

    /**
     * v2.0 constructor for backwards compatibility
     *
     * @since 2.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public static SectionTextured of(ResourceLocation id, Component title, int textColor, ConglomerateOfItems items)
    {
        return new SectionTextured(id, title, ResourceLocation.fromNamespaceAndPath(id.getNamespace(),
                "textures/gui/fancy_tab_section/" + id.getPath() + ".png"), textColor, items);
    }


    /**
     * v1.0 constructor for backwards compatibility
     *
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public SectionTextured(ResourceLocation id, Component title, ResourceLocation texture, int textColor, List<Item> items)
    {
        ConglomerateOfItems con = ConglomerateOfItems.create();
        items.forEach(con::add);

        delegated = new net.mcexpanded.fancytabsections.Section.SectionTextured(
                id, title, texture, textColor, false, true, con
        );
    }

    /**
     * v1.0 constructor for backwards compatibility
     *
     * @since 1.0
     * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
     */
    @Deprecated(forRemoval = true, since = "4.0")
    public static SectionTextured of(ResourceLocation id, Component title, int textColor, List<Item> items)
    {
        return new SectionTextured(id, title, ResourceLocation.fromNamespaceAndPath(id.getNamespace(),
                "textures/gui/fancy_tab_section/" + id.getPath() + ".png"), textColor, items);
    }







    @Override
    public boolean equals(Object o)
    {
        return (o instanceof SectionTextured other) && delegated.equals(other.delegated);
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
}


