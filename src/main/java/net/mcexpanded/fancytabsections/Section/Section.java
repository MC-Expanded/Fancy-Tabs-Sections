package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.FTSInternal;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

/**
 * @since 3.0
 */
public interface Section<T extends Section<T>> {
    ResourceLocation id();
    default boolean collapsible() { return true; }
    ConglomerateOfItems items();

    void render(GuiGraphics guiGraphics, Font font, int topLeftX, int topLeftY);

    /**
     * ConglomerateOfItems redirect helper methods
     * @since 4.0
     */
    @SuppressWarnings("unchecked")
    default T add(Item item)
    {
        items().add(item);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(ItemStack stack)
    {
        items().add(stack);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(DeferredItem<Item> deferredItem)
    {
        items().add(deferredItem);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(ItemLike itemLike)
    {
        items().add(itemLike);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(DeferredRegister.Items deferredRegister)
    {
        items().add(deferredRegister);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(Supplier<ItemStack> itemStackSupplier)
    {
        items().add(itemStackSupplier);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(List<ItemStack> listOfStacks)
    {
        items().add(listOfStacks);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T add(ConglomerateOfItems.RegistryDependentEntry entry)
    {
        items().add(entry);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    default T addItemTag(TagKey<Item> tag)
    {
        add((registry) ->
                registry.lookup(Registries.ITEM)
                        .map(lookup -> lookup.get(tag)
                                .map(named -> named.stream()
                                        .map(holder -> holder.value().getDefaultInstance()).toList()
                                ).orElse(List.of()))
                        .orElseGet(List::of));
        return (T) this;
    }

    /**
     * Renders the toggle button on the right side of the banner of a given section.
     * If isHoveringAny is passed as true, highlights hover texture if shift is being held down.
     *
     * @since 4.0
     */
    default void renderToggle(CreativeModeInventoryScreen screen, GuiGraphics graphics, Section<?> section, int x, int y, int w, int h, int mouseX, int mouseY, boolean isHoveringAny)
    {
        // Top Left of Right-most slot of the banner row.
        int tx1 = x + w + 3;
        int tx0 = tx1 - BannerRenderer.ROW_HEIGHT;
        int ty0 = y - 1;
        int ty1 = y + h;

        // Centre the button texture within the slot (+1 on each axis to align with the grid cell).
        int bx = tx0 + (BannerRenderer.ROW_HEIGHT - 16) / 2 + 1;
        int by = ty0 + (h - 16) / 2 + 1;

        //render button texture with highlight if hovered
        if (((isHoveringAny && Screen.hasShiftDown()) || mouseX >= tx0 && mouseX < tx1 && mouseY >= ty0 && mouseY < ty1) && screen.getMenu().getCarried().isEmpty())
            graphics.blit(FTSInternal.isCollapsed(section) ? BannerRenderer.COLLAPSED_BUTTON_HIGHLIGHT : BannerRenderer.EXPANDED_BUTTON_HIGHLIGHT, bx, by, 0, 0, 16, 16, 16, 16);
        else
            graphics.blit(FTSInternal.isCollapsed(section) ? BannerRenderer.COLLAPSED_BUTTON : BannerRenderer.EXPANDED_BUTTON, bx, by, 0, 0, 16, 16, 16, 16);
    }
}