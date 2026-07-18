package net.mcexpanded.fancytabsections.Section;

import com.wdiscute.utils.ScreenUtils;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * @since 4.0
 */
public abstract class AbstractSectionWithTitle<T extends AbstractSectionWithTitle<T>> implements Section<T>, StickySection
{
    final ResourceLocation id;
    public Component title = Component.empty();
    public int titleOffsetX = 5;
    public int titleOffsetY = 5;
    public int textColor = 0xFFFFFFFF;
    public int textOutline = 0x00000000;
    public boolean textShadow = true;
    boolean centered = false;
    boolean collapsible = true;
    boolean sticky = true;
    Runnable onRender = () -> {};
    ConglomerateOfItems items = ConglomerateOfItems.create();

    public AbstractSectionWithTitle(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        onRender.run();
        renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }

    public void renderTitle(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        topLeftX += titleOffsetX;
        topLeftY += titleOffsetY;

        if (centered)
        {
            if (textOutline != 0x00000000)
            {
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY - 1, textOutline, false);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY + 1, textOutline, textShadow);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX + 1, topLeftX + 137 + 1, topLeftY, textOutline, textShadow);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX - 1, topLeftX + 137 - 1, topLeftY, textOutline, false);
            }

            ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY, textColor, textShadow);
        }
        else
        {
            if (textOutline != 0x00000000)
            {
                ScreenUtils.renderScrollingString(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY - 1, textOutline, false, 100);
                ScreenUtils.renderScrollingString(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY + 1, textOutline, textShadow, 100);
                ScreenUtils.renderScrollingString(guiGraphics, font, title, topLeftX + 1, topLeftX + 137 + 1, topLeftY, textOutline, textShadow, 100);
                ScreenUtils.renderScrollingString(guiGraphics, font, title, topLeftX - 1, topLeftX + 137 - 1, topLeftY, textOutline, false, 100);
            }

            ScreenUtils.renderScrollingString(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY, textColor, textShadow, 100);
        }
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
     *
     * @since 4.0
     */
    @SuppressWarnings("unchecked")
    public T setTitleOffset(int x, int y)
    {
        this.titleOffsetX = x;
        this.titleOffsetY = y;
        return (T) this;
    }

    /**
     * Makes the title of the section centered in the middle + offset
     *
     * @since 5.0
     */
    @SuppressWarnings("unchecked")
    public T setCentered(boolean centered)
    {
        this.centered = centered;
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

    /**
     * Adjusts the offset at which to render the Title.
     * Default is (5, 5)
     *
     * @since 5.0
     */
    @SuppressWarnings("unchecked")
    public T setSticky(boolean sticky)
    {
        this.sticky = sticky;
        return (T) this;
    }

    /**
     * Runs code each time {@link Section#render(GuiGraphics, Font, int, int)} is called for this Section.
     *
     * @since 5.0
     */
    @SuppressWarnings("unchecked")
    public T setOnRender(Runnable onRender)
    {
        this.onRender = onRender;
        return (T) this;
    }

    @Override
    public ResourceLocation id()
    {
        return id;
    }

    @Override
    public Component title()
    {
        return title;
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

    @Override
    public boolean isSticky()
    {
        return sticky;
    }
}
