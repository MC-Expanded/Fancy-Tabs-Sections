package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;

import java.util.List;

public class BannerRenderer {

    public static int CURRENT_ROW = 0;

    private static final int ROW_HEIGHT    = 18;
    private static final int GRID_COLS     = 9;
    private static final int GRID_X_OFFSET = 10;
    private static final int GRID_Y_OFFSET = 17;

    public static void render(CreativeModeInventoryScreen screen, GuiGraphicsExtractor graphics, List<Section> sections) {
        int left = screen.getLeftPos() + GRID_X_OFFSET;
        int top  = screen.getTopPos()  + GRID_Y_OFFSET;
        int w    = GRID_COLS * ROW_HEIGHT - 4;

        Font font = Minecraft.getInstance().font;

        for (Section section : sections) {
            Integer sectionRow = TabLayout.SECTION_ROW.get(section.id());
            if (sectionRow == null) continue;

            int relativeRow = sectionRow - CURRENT_ROW;
            if (relativeRow < 0 || relativeRow >= 5) continue;

            int x = left;
            int y = top + relativeRow * ROW_HEIGHT;
            int h = ROW_HEIGHT - 1;

            if (section instanceof SectionColored colored) {
                graphics.fill(x - 1, y, x + w + 1, y + h, colored.bannerColor());
                graphics.fill(x - 1, y, x + w + 1, y + 1, brighten(colored.bannerColor(), 0.4f));
            } else if (section instanceof SectionTextured textured) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, textured.texture(), x - 1, y, 0, 0, w + 2, h, w + 2, h);
            }

            int textX = x + 4;
            int textY = y + (h - font.lineHeight) / 2;
            graphics.text(font, section.title(), textX, textY, section.textColor(), true);
        }
    }

    private static int brighten(int argb, float amount) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >>  8) & 0xFF;
        int b =  argb        & 0xFF;
        r = (int)(r + (255 - r) * amount);
        g = (int)(g + (255 - g) * amount);
        b = (int)(b + (255 - b) * amount);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}