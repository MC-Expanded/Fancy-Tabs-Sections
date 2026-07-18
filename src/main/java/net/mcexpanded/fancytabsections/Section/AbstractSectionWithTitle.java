package net.mcexpanded.fancytabsections.Section;

import com.wdiscute.utils.ScreenUtils;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @since 4.0
 */
public abstract class AbstractSectionWithTitle<T extends AbstractSectionWithTitle<T>> implements Section<T>, StickySection
{
    final Identifier id;
    public Component title; //Component.translatable("section." + id.getNamespace() + "." + id.getPath())
    public boolean renderTitle = true;
    public int titleOffsetX = 5;
    public int titleOffsetY = 5;
    public int textColor = 0xFFFFFFFF;
    public int textOutline = 0x00000000;
    public boolean textShadow = true;
    boolean centered = false;
    boolean collapsible = true;
    boolean sticky = true;
    Function<RegistryAccess, ItemStack> displayItemFunction = null;
    ItemStack displayItem = null;
    Runnable onRender = () -> {};
    Consumer<T> onRenderConsumer = (section) -> {};
    ConglomerateOfItems items = ConglomerateOfItems.create();

    public AbstractSectionWithTitle(Identifier id)
    {
        this.id = id;
        this.title = Component.translatable("section." + id.getNamespace() + "." + id.getPath());
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        onRender.run();
        onRenderConsumer.accept((T) this);
        renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }

    public void renderTitle(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        if (!renderTitle) return;
        topLeftX += titleOffsetX;
        topLeftY += titleOffsetY;

        if (centered)
        {
            if (textOutline != 0x00000000)
            {
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY - 1, textOutline, false);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY + 1, textOutline, textShadow);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78 + 1, topLeftX + 1, topLeftX + 137 + 1, topLeftY, textOutline, textShadow);
                ScreenUtils.renderCenteredScrollingString(guiGraphics, font, title, topLeftX + 78 - 1, topLeftX - 1, topLeftX + 137 - 1, topLeftY, textOutline, false);
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
     * Sets whether the title should render.
     *
     * @since 6.0
     */
    @SuppressWarnings("unchecked")
    public T setRenderTitle(boolean renderTitle)
    {
        this.renderTitle = renderTitle;
        return (T) this;
    }

    /**
     * Sets the display ItemStack.
     *
     * @since 6.0
     */
    @SuppressWarnings("unchecked")
    public T setDisplayItem(Function<RegistryAccess, ItemStack> item)
    {
        this.displayItemFunction = item;
        return (T) this;
    }

    /**
     * Triggers the displayItemFunction to set the ItemStack from RegistryAccess
     *
     * @since 6.0
     */
    @Override
    public void onReload(RegistryAccess registryAccess)
    {
        if(displayItemFunction != null) displayItem = displayItemFunction.apply(registryAccess);
    }

    /**
     * If a display item has been set, use that, otherwise pass to super
     *
     * @since 6.0
     */
    @Override
    public ItemStack icon()
    {
        return displayItem == null ? Section.super.icon() : displayItem;
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
     * Runs code each time {@link Section#render(GuiGraphicsExtractor, Font, int, int)} is called for this Section.
     *
     * @since 5.0
     */
    @SuppressWarnings("unchecked")
    public T setOnRender(Runnable onRender)
    {
        this.onRender = onRender;
        return (T) this;
    }

    /**
     * Sets a consumer to run at the start of {@link Section#render(GuiGraphics, Font, int, int)} is called for this Section.
     *
     * @since 6.0
     */
    @SuppressWarnings("unchecked")
    public T setOnRenderConsumer(Consumer<T> onRenderSection)
    {
        this.onRenderConsumer = onRenderSection;
        return (T) this;
    }

    @Override
    public Identifier id()
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
