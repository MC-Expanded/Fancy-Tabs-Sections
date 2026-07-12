package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FTSInternal;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

public class BannerRenderer
{

    public static int CURRENT_ROW = 0;

    public static ResourceLocation CURRENT_TAB = null;

    public static final int ROW_HEIGHT = 18;
    public static final int GRID_COLS = 9;
    public static final int GRID_X_OFFSET = 10;
    public static final int GRID_Y_OFFSET = 17;


    public static final int VISIBLE_ROWS = 5;
    public static final int BANNER_WIDTH = GRID_COLS * ROW_HEIGHT - 4;

    public static final ResourceLocation EXPANDED_BUTTON = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/collapse_button/expanded_button.png");
    public static final ResourceLocation EXPANDED_BUTTON_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/collapse_button/expanded_button_highlight.png");
    public static final ResourceLocation COLLAPSED_BUTTON = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/collapse_button/collapsed_button.png");
    public static final ResourceLocation COLLAPSED_BUTTON_HIGHLIGHT = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/collapse_button/collapsed_button_highlight.png");

    public static void render(CreativeModeInventoryScreen screen, GuiGraphics guiGraphics, List<Section<?>> sections, int mouseX, int mouseY)
    {
        int topLeftX = screen.getGuiLeft() + 8;
        int top = screen.getGuiTop() + 17;
        int w = BANNER_WIDTH;

        Font font = Minecraft.getInstance().font;

        for (Section<?> section : sections)
        {
            int sectionRow = FTSInternal.getRowForSection(section);
            if (sectionRow == -1) continue;

            int relativeRow = sectionRow - CURRENT_ROW;
            if (relativeRow < 0 || relativeRow >= 5) continue;

            int topLeftY = top + relativeRow * 18;

            section.render(guiGraphics, font, topLeftX, topLeftY);

            boolean isHoveringAny = sections.stream().anyMatch(o -> BannerRenderer.isInToggle(screen, o, mouseX, mouseY));
            section.renderToggle(screen, guiGraphics, section, topLeftX, topLeftY, w, 18, mouseX, mouseY, isHoveringAny);
        }
    }

    /**
     * @return true if the given screen-space coordinate falls within the (visible) toggle control
     * of a collapsible {@link Section}.
     */
    @ApiStatus.Internal
    public static boolean isInToggle(CreativeModeInventoryScreen screen, Section section, double mouseX, double mouseY)
    {
        if (!section.collapsible()) return false;

        int sectionRow = FTSInternal.getRowForSection(section);
        if (sectionRow == -1) return false;

        int relativeRow = sectionRow - CURRENT_ROW;
        if (relativeRow < 0 || relativeRow >= VISIBLE_ROWS) return false;

        int left = screen.getGuiLeft() + GRID_X_OFFSET;
        int top = screen.getGuiTop() + GRID_Y_OFFSET;
        int y = top + relativeRow * ROW_HEIGHT;
        int h = ROW_HEIGHT - 1;

        int tx1 = left + BANNER_WIDTH + 1;
        int tx0 = tx1 - ROW_HEIGHT;

        return mouseX >= tx0 && mouseX < tx1 && mouseY >= y && mouseY < y + h;
    }
}
