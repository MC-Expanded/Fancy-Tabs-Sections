package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * @since 4.0
 */
public abstract class AbstractSectionWithTitle<T extends AbstractSectionWithTitle<T>> implements Section<T>
{
    final Identifier id;
    public Component title = Component.empty();
    public int titleOffsetX = 5;
    public int titleOffsetY = 5;
    public int textColor = 0xFFFFFFFF;
    public int textOutline = 0x00000000;
    public boolean textShadow = true;
    boolean collapsible = true;
    ConglomerateOfItems items = ConglomerateOfItems.create();

    public AbstractSectionWithTitle(Identifier id)
    {
        this.id = id;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }

    public void renderTitle(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        topLeftX += titleOffsetX;
        topLeftY += titleOffsetY;
        if (textOutline != 0x00000000)
        {
            guiGraphics.text(font, title, topLeftX + 1, topLeftY, textOutline, textShadow);
            guiGraphics.text(font, title, topLeftX - 1, topLeftY, textOutline, textShadow);
            guiGraphics.text(font, title, topLeftX, topLeftY + 1, textOutline, textShadow);
            guiGraphics.text(font, title, topLeftX, topLeftY - 1, textOutline, textShadow);
        }
        guiGraphics.text(font, title, topLeftX, topLeftY, textColor, textShadow);
    }



    @SuppressWarnings("unchecked")
    public T setTitle(Component component)
    {
        this.title = component;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTitle(String title)
    {
        this.title = Component.literal(title);
        return (T) this;
    }

    /**
     * Adjusts the offset at which to render the Title.
     * Default is (5, 5)
     * @since 4.0
     */
    @SuppressWarnings("unchecked")
    public T setTitleOffset(int x, int y)
    {
        this.titleOffsetX = x;
        this.titleOffsetY = y;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTextColor(int textColor)
    {
        this.textColor = textColor;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTextOutline(int textOutline)
    {
        this.textOutline = textOutline;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTextShadow(boolean textShadow)
    {
        this.textShadow = textShadow;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setCollapsible(boolean collapsible)
    {
        this.collapsible = collapsible;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setItems(ConglomerateOfItems items)
    {
        this.items = items;
        return (T) this;
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

    @Override
    public boolean collapsible()
    {
        return collapsible;
    }
}
