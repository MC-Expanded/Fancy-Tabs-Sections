package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @since 4.0
 */
public abstract class AbstractSectionWithTitle<T extends AbstractSectionWithTitle<T>> implements Section<T>, StickySection
{
    final ResourceLocation id;
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
    Runnable onRender = () ->
    {
    };
    Consumer<T> onRenderConsumer = (section) ->
    {
    };
    ConglomerateOfItems items = ConglomerateOfItems.create();

    public AbstractSectionWithTitle(ResourceLocation id)
    {
        this.id = id;
        this.title = Component.translatable("section." + id.getNamespace() + "." + id.getPath());
    }

    @Override
    public void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        onRender.run();
        onRenderConsumer.accept((T) this);
        renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }

    public void renderTitle(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        if (!renderTitle) return;
        topLeftX += titleOffsetX;
        topLeftY += titleOffsetY;

        if (centered)
        {
            if (textOutline != 0x00000000)
            {
                centeredScrollingText(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY - 1, textOutline, false);
                centeredScrollingText(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY + 1, textOutline, textShadow);
                centeredScrollingText(guiGraphics, font, title, topLeftX + 78 + 1, topLeftX + 1, topLeftX + 137 + 1, topLeftY, textOutline, textShadow);
                centeredScrollingText(guiGraphics, font, title, topLeftX + 78 - 1, topLeftX - 1, topLeftX + 137 - 1, topLeftY, textOutline, false);
            }

            centeredScrollingText(guiGraphics, font, title, topLeftX + 78, topLeftX, topLeftX + 137, topLeftY, textColor, textShadow);
        }
        else
        {
            if (textOutline != 0x00000000)
            {
                scrollingText(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY - 1, textOutline, false, 100);
                scrollingText(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY + 1, textOutline, textShadow, 100);
                scrollingText(guiGraphics, font, title, topLeftX + 1, topLeftX + 137 + 1, topLeftY, textOutline, textShadow, 100);
                scrollingText(guiGraphics, font, title, topLeftX - 1, topLeftX + 137 - 1, topLeftY, textOutline, false, 100);
            }

            scrollingText(guiGraphics, font, title, topLeftX, topLeftX + 137, topLeftY, textColor, textShadow, 100);
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

    /**
     * From wdUtils
     *
     * @since 6.2
     */
    public static void centeredScrollingText(GuiGraphics guiGraphics, Font font, Component text, int centerX, int minX, int maxX, int y, int color, boolean shadow)
    {
        int i = font.width(text);
        int k = maxX - minX;
        if (i > k)
        {
            int l = i - k;
            double d0 = (double) Util.getMillis() / (double) 300.0F;
            double d1 = Math.max((double) l * (double) 0.5F, 3.0F);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / (double) 2.0F + (double) 0.5F;
            double d3 = Mth.lerp(d2, 0.0F, l);
            guiGraphics.enableScissor(minX, y - 10, maxX, y + 10);
            int x = minX - (int) d3;
            guiGraphics.drawString(font, text, x, y, color, shadow);
            guiGraphics.disableScissor();
        }
        else
        {
            int i1 = Mth.clamp(centerX, minX + i / 2, maxX - i / 2);
            guiGraphics.drawString(font, text.getVisualOrderText(), i1 - font.width(text.getVisualOrderText()) / 2, y, color, shadow);
        }
    }

    /**
     * From wdUtils
     *
     * @since 6.2
     */
    public static void scrollingText(GuiGraphics guiGraphics, Font font, Component text, int minX, int maxX, int y, int color, boolean shadow, int scrollingSpeed)
    {
        int i = font.width(text);
        int k = maxX - minX;
        if (i > k)
        {
            int l = i - k;
            double d0 = (double) Util.getMillis() / (double) scrollingSpeed;
            double d1 = Math.max((double) l * (double) 0.5F, 3.0F);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / (double) 2.0F + (double) 0.5F;
            double d3 = Mth.lerp(d2, 0.0F, l);
            guiGraphics.enableScissor(minX, y - 20, maxX, y + 20);
            int x = minX - (int) d3;
            guiGraphics.drawString(font, text, x, y, color, shadow);
            guiGraphics.disableScissor();
        }
        else
        {
            int i1 = Mth.clamp(minX, minX + i / 2, maxX - i / 2);
            guiGraphics.drawString(font, text.getVisualOrderText(), i1 - font.width(text.getVisualOrderText()) / 2, y, color, shadow);
        }
    }
}
