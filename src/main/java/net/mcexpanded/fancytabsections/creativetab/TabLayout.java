package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TabLayout
{

    // Grid row at which each section's banner sits, keyed by section id
    public static final Map<ResourceLocation, Integer> SECTION_ROW = new HashMap<>();

    // Collapsed state per section id. Absent == expanded.
    public static final Map<ResourceLocation, Boolean> COLLAPSED = new HashMap<>();

    public static boolean isCollapsed(ResourceLocation sectionId)
    {
        return COLLAPSED.getOrDefault(sectionId, false);
    }

    // True if row is a banner row belonging to a section.
    public static boolean isBannerRow(ResourceLocation tab, int row)
    {
        List<Section> sections = FancyTabSections.SECTIONS_MAP.get(tab);
        if (sections == null) return false;

        for (Section section : sections)
        {
            Integer sectionRow = SECTION_ROW.get(section.id());
            if (sectionRow != null && sectionRow == row) return true;
        }
        return false;
    }

    // Flips the collapsed state of a section and returns the new value
    public static void toggle(ResourceLocation sectionId)
    {
        boolean next = !isCollapsed(sectionId);

        //play sound with client only check for some weird mods who do weird things (computer craft)
        if (FMLEnvironment.dist.isClient())
            Client.playSound(!isCollapsed(sectionId));

        COLLAPSED.put(sectionId, next);
    }

    public static void build()
    {
        SECTION_ROW.clear();

        FancyTabSections.SECTIONS_MAP.forEach((rl, sections) ->
        {
            List<ItemStack> result = new ArrayList<>();
            // Search results ignore collapsed state so hidden items remain searchable.
            Set<ItemStack> searchResults = new LinkedHashSet<>();

            int row = 0;
            for (Section section : sections)
            {
                // Blank banner row (always present, even when collapsed)
                SECTION_ROW.put(section.id(), row);
                for (int i = 0; i < 9; i++)
                {
                    result.add(ItemStack.EMPTY);
                }
                row++;

                List<ItemStack> stacks = section.items().toStacks();

                for (ItemStack stack : stacks)
                {
                    if (!stack.isEmpty()) searchResults.add(stack);
                }

                if (isCollapsed(section.id()))
                {
                    // Collapsed: the section occupies only its banner row.
                    continue;
                }

                result.addAll(stacks);

                int itemCount = stacks.size();
                int usedInLastRow = itemCount % 9;
                if (usedInLastRow != 0)
                {
                    int padding = 9 - usedInLastRow;
                    for (int i = 0; i < padding; i++)
                    {
                        result.add(ItemStack.EMPTY);
                    }
                    row += (itemCount / 9) + 1;
                }
                else
                {
                    row += itemCount / 9;
                }
            }

            FancyTabSections.ITEMS_MAP.put(rl, List.copyOf(result));
            FancyTabSections.SEARCH_MAP.put(rl, new LinkedHashSet<>(searchResults));
        });
    }

    public static class Client
    {
        public static void playSound(boolean b)
        {
            if (b)
                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, 1f, 1F));
            else
                Minecraft.getInstance().getSoundManager().play(
                        SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, 1f, 1F));
        }
    }
}
