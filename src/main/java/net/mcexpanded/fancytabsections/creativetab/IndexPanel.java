package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FTSConfig;
import net.mcexpanded.fancytabsections.FTSInternal;
import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * A collapsible jump-list along the left edge of the creative-mode inventory screen,
 * showing one icon per registered {@link Section} of the currently selected tab. Clicking
 * an icon smooth-scrolls the grid so that section's banner lands at the top.
 *
 * @since 6.0
 */
public final class IndexPanel
{
    private IndexPanel()
    {
    }

    private static final int ICON = 18;
    private static final int GAP = 2;
    private static final int PANEL_W = 24;

    private static final Identifier TOGGLE_COLLAPSED =
            Identifier.fromNamespaceAndPath("fancytabsections", "textures/gui/index_panel/toggle_collapsed.png");
    private static final Identifier TOGGLE_EXPANDED =
            Identifier.fromNamespaceAndPath("fancytabsections", "textures/gui/index_panel/toggle_expanded.png");

    private static final Identifier INDEX_SLOT = Identifier.fromNamespaceAndPath("fancytabsections", "textures/gui/index_panel/slot_border.png");
    private static final Identifier INDEX_SLOT_BOTTOM = Identifier.fromNamespaceAndPath("fancytabsections", "textures/gui/index_panel/slot_border_bottom.png");
    private static final Identifier INDEX_SLOT_TOP = Identifier.fromNamespaceAndPath("fancytabsections", "textures/gui/index_panel/slot_border_top.png");

    private static final int TOGGLE_LEFT = 6;
    private static final int TOGGLE_SIZE = 8;
    private static final int TOGGLE_TEXT_GAP = 3;
    private static final int TOGGLE_HIT_PAD = 3;

    private static final int HOVER = 0x60FFFFFF;

    private static final long ANIM_MS = 250;

    private static float panelScroll = 0f;

    private static long animStart = -1;
    private static float animFrom;
    private static float animTo;

    public static boolean active(List<Section<?>> sections)
    {
        return sections != null && !sections.isEmpty();
    }

    private static int toggleX(CreativeModeInventoryScreen screen)
    {
        return screen.getLeftPos() + TOGGLE_LEFT;
    }

    private static int toggleY(CreativeModeInventoryScreen screen)
    {
        return screen.getTopPos() + 6;
    }

    /**
     * @return the X offset (relative to guiLeft) the tab title should shift to when this panel's
     * toggle is showing, so the two don't overlap. Applied via a mixin hook into renderLabels.
     */
    public static int titleX()
    {
        return TOGGLE_LEFT + TOGGLE_SIZE + TOGGLE_TEXT_GAP;
    }

    private static int panelX(CreativeModeInventoryScreen screen)
    {
        return screen.getLeftPos() - 2 - PANEL_W;
    }

    private static int panelY(CreativeModeInventoryScreen screen)
    {
        return screen.getTopPos() + 4;
    }

    private static int visibleRows(CreativeModeInventoryScreen screen, int count)
    {
        int maxH = screen.getImageHeight() - 8;
        return Math.clamp((maxH - 6 + GAP) / (ICON + GAP), 0, count);
    }

    private static int panelHeight(CreativeModeInventoryScreen screen, int count)
    {
        int rows = visibleRows(screen, count);
        return rows <= 0 ? 0 : rows * (ICON + GAP) - GAP + 6;
    }

    private static float maxScroll(CreativeModeInventoryScreen screen, int count)
    {
        int contentH = count * (ICON + GAP) - GAP + 6;
        return Math.max(0, contentH - panelHeight(screen, count));
    }

    /**
     * Progresses any in-flight jump-to-section animation.
     *
     * @return the scroll fraction (0..1) to apply this frame, or -1 if there's nothing to apply
     */
    public static float tick()
    {
        if (animStart < 0) return -1f;

        long elapsed = System.currentTimeMillis() - animStart;
        float t = Mth.clamp(elapsed / (float) ANIM_MS, 0f, 1f);
        float inv = 1f - t;
        float eased = 1f - inv * inv * inv;
        float value = Mth.lerp(eased, animFrom, animTo);

        if (t >= 1f) animStart = -1;
        return value;
    }

    private static void cancelAnim()
    {
        animStart = -1;
    }

    private static void jumpTo(CreativeModeInventoryScreen screen, Section<?> section, float currentScrollOffs)
    {
        int row = FTSInternal.getRowForSection(section);
        if (row == -1) return;

        int itemCount = screen.getMenu().items.size();
        int rows = Mth.positiveCeilDiv(itemCount, BannerRenderer.GRID_COLS) - BannerRenderer.VISIBLE_ROWS;
        if (rows <= 0) return;

        animFrom = currentScrollOffs;
        animTo = Mth.clamp(row / (float) rows, 0f, 1f);
        animStart = System.currentTimeMillis();
    }

    public static void render(CreativeModeInventoryScreen screen, GuiGraphicsExtractor g, List<Section<?>> sections, int mouseX, int mouseY)
    {
        drawToggle(screen, g, mouseX, mouseY);

        if (!FTSConfig.INDEX_EXPANDED.get() || sections.isEmpty()) return;

        int count = sections.size();
        panelScroll = Mth.clamp(panelScroll, 0f, maxScroll(screen, count));

        int px = panelX(screen);
        int py = panelY(screen);
        int h = panelHeight(screen, count);
        if (h <= 0) return;
        int pBottom = py + h;

        //drawRaisedBox(g, px, py, PANEL_W, h);

        Component hoveredTitle = null;

        int slotHeight = ICON + GAP;
        int start = Math.max(0, (int) panelScroll / slotHeight);
        int visible = Math.min(6, count - start);

        for (int i = 0; i < visible; i++)
        {
            int sectionIndex = start + i;

            int ix = px + (PANEL_W - ICON) / 2;

            // Keep the 6 slots fixed in place
            int iy = py + 3 + i * slotHeight;

            boolean hovered = mouseX >= ix && mouseX < ix + ICON && mouseY >= iy && mouseY < iy + ICON && mouseY >= py && mouseY < pBottom;

            Identifier rl;

            if (i == 0)
                rl = INDEX_SLOT_TOP;
            else if (i == visible - 1)
                rl = INDEX_SLOT_BOTTOM;
            else
                rl = INDEX_SLOT;

            g.blit(RenderPipelines.GUI_TEXTURED, rl, ix - 3, iy - 3, 24, 24, 24, 24, 24, 24, 24, 24);

            if (hovered)
                g.fill(
                        ix + 1,
                        iy + 1,
                        ix + ICON - 1,
                        iy + ICON - 1,
                        HOVER
                );

            Section<?> section = sections.get(sectionIndex);
            ItemStack icon = section.icon();

            g.item(icon, ix + 1, iy + 1);
            g.itemDecorations(Minecraft.getInstance().font, icon, ix + 1, iy + 1);

            Component title = section.title();

            if (hovered)
                hoveredTitle = title == null
                        ? Component.literal(section.id().toString())
                        : title;
        }

        if (hoveredTitle != null)
        {
            g.setTooltipForNextFrame(hoveredTitle, mouseX, mouseY);
        }
    }

    public static boolean mouseClicked(CreativeModeInventoryScreen screen, List<Section<?>> sections, float scrollOffs, double mouseX, double mouseY, int button)
    {
        if (button != 0) return false;

        int tx = toggleX(screen);
        int ty = toggleY(screen);
        if (mouseX >= tx - TOGGLE_HIT_PAD && mouseX < tx + TOGGLE_SIZE + TOGGLE_HIT_PAD
            && mouseY >= ty - TOGGLE_HIT_PAD && mouseY < ty + TOGGLE_SIZE + TOGGLE_HIT_PAD)
        {
            FTSConfig.INDEX_EXPANDED.set(!FTSConfig.INDEX_EXPANDED.get());
            FTSConfig.INDEX_EXPANDED.save();
            panelScroll = 0f;
            return true;
        }

        if (!FTSConfig.INDEX_EXPANDED.get() || sections.isEmpty())
        {
            cancelAnim();
            return false;
        }

        int count = sections.size();
        int px = panelX(screen);
        int py = panelY(screen);
        int h = panelHeight(screen, count);
        if (h <= 0 || mouseX < px || mouseX >= px + PANEL_W || mouseY < py || mouseY >= py + h)
        {
            cancelAnim();
            return false;
        }

        int i = (int) ((mouseY - (py + 3) + panelScroll) / (ICON + GAP));
        if (i < 0 || i >= count) return true;

        int ix = px + (PANEL_W - ICON) / 2;
        int iy = py + 3 + i * (ICON + GAP) - (int) panelScroll;
        if (mouseX < ix || mouseX >= ix + ICON || mouseY < iy || mouseY >= iy + ICON) return true;

        jumpTo(screen, sections.get(i), scrollOffs);
        return true;
    }

    public static boolean mouseScrolled(CreativeModeInventoryScreen screen, List<Section<?>> sections, double mouseX, double mouseY, double scrollY)
    {
        if (!FTSConfig.INDEX_EXPANDED.get() || sections.isEmpty())
        {
            cancelAnim();
            return false;
        }

        int count = sections.size();
        int px = panelX(screen);
        int py = panelY(screen);
        int h = panelHeight(screen, count);
        if (h <= 0 || mouseX < px || mouseX >= px + PANEL_W || mouseY < py || mouseY >= py + h)
        {
            cancelAnim();
            return false;
        }

        panelScroll = Mth.clamp((float) (panelScroll - scrollY * 20f), 0f, maxScroll(screen, count));
        return true;
    }

    private static void drawToggle(CreativeModeInventoryScreen screen, GuiGraphicsExtractor g, int mouseX, int mouseY)
    {
        int tx = toggleX(screen);
        int ty = toggleY(screen);
        boolean hovered = mouseX >= tx && mouseX < tx + TOGGLE_SIZE && mouseY >= ty && mouseY < ty + TOGGLE_SIZE;

        Identifier texture = FTSConfig.INDEX_EXPANDED.get() ? TOGGLE_EXPANDED : TOGGLE_COLLAPSED;
        g.blit(RenderPipelines.GUI_TEXTURED, texture, tx, ty, 0, 0, TOGGLE_SIZE, TOGGLE_SIZE, TOGGLE_SIZE, TOGGLE_SIZE);
        if (hovered)
        {
            g.fill(tx, ty, tx + TOGGLE_SIZE, ty + TOGGLE_SIZE, HOVER);
        }
    }
}
