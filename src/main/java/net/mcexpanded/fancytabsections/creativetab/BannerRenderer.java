package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BannerRenderer
{

    public static int CURRENT_ROW = 0;

    private static final int ROW_HEIGHT    = 18;
    private static final int GRID_COLS     = 9;
    private static final int GRID_X_OFFSET = 10;
    private static final int GRID_Y_OFFSET = 17;


    private static final int VISIBLE_ROWS  = 5;
    private static final int BANNER_WIDTH  = GRID_COLS * ROW_HEIGHT - 4;

    /**
     * Toggle button textures. Expected to be 16x16 PNGs.
     */
    private static final int TOGGLE_SIZE = 16;
    private static final ResourceLocation EXPANDED_BUTTON = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/expanded_button.png");
    private static final ResourceLocation COLLAPSED_BUTTON = ResourceLocation.fromNamespaceAndPath(FancyTabSections.MOD_ID, "textures/gui/fancy_tab_section/collapsed_button.png");

    public static void render(CreativeModeInventoryScreen screen, GuiGraphics guiGraphics, List<Section> sections, int mouseX, int mouseY)
    {
        int topLeftX = screen.getGuiLeft() + 8;
        int top = screen.getGuiTop() + 17;
        int w = BANNER_WIDTH;

        Font font = Minecraft.getInstance().font;

        for (Section section : sections)
        {
            Integer sectionRow = TabLayout.SECTION_ROW.get(section.id());
            if (sectionRow == null) continue;

            int relativeRow = sectionRow - CURRENT_ROW;
            if (relativeRow < 0 || relativeRow >= 5) continue;

            int topLeftY = top + relativeRow * 18;

            section.render(guiGraphics, font, topLeftX, topLeftY);

            if (section.collapsible())
            {
                renderToggle(guiGraphics, section, topLeftX, topLeftY, w, 18, mouseX, mouseY);
            }
        }
    }

    private static void renderToggle(GuiGraphics graphics, Section section, int x, int y, int w, int h, int mouseX, int mouseY)
    {
        // Right-most slot of the banner row.
        int tx1 = x + w + 3;
        int tx0 = tx1 - ROW_HEIGHT;
        int ty0 = y - 1;
        int ty1 = y + h;

        boolean hovered = mouseX >= tx0 && mouseX < tx1 && mouseY >= ty0 && mouseY < ty1;
        //todo enable this when I figure out how to stop the hover highlight on sections
        if (hovered && !hovered)
        {
            graphics.fill(tx0, ty0, tx1, ty1, 0x33FFFFFF);
        }

        // Centre the button texture within the slot (+1 on each axis to align with the grid cell).
        int bx = tx0 + (ROW_HEIGHT - TOGGLE_SIZE) / 2 + 1;
        int by = ty0 + (h - TOGGLE_SIZE) / 2 + 1;

        ResourceLocation texture = TabLayout.isCollapsed(section.id()) ? COLLAPSED_BUTTON : EXPANDED_BUTTON;
        graphics.blit(texture, bx, by, 0, 0, TOGGLE_SIZE, TOGGLE_SIZE, TOGGLE_SIZE, TOGGLE_SIZE);
    }

    /**
     * @return true if the given screen-space coordinate falls within the (visible) toggle control
     * of a collapsible {@code section}.
     */
    public static boolean isInToggle(CreativeModeInventoryScreen screen, Section section, double mouseX, double mouseY)
    {
        if (!section.collapsible()) return false;

        Integer sectionRow = TabLayout.SECTION_ROW.get(section.id());
        if (sectionRow == null) return false;

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
