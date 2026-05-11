package net.mcexpanded.fancytabsections.creativetab;

import net.mcexpanded.fancytabsections.FancyTabSections;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabLayout
{

    public static final Map<ResourceLocation, Integer> SECTION_ROW = new HashMap<>();

    public static void build()
    {
        SECTION_ROW.clear();
        List<ItemStack> result = new ArrayList<>();

        FancyTabSections.SECTIONS_MAP.forEach((rl, sections) ->
        {
            int row = 0;
            for (Section section : sections)
            {
                // Blank banner row
                SECTION_ROW.put(section.id(), row);
                for (int i = 0; i < 9; i++)
                {
                    result.add(ItemStack.EMPTY);
                }
                row++;

                List<ItemStack> stacks = section.items().toStacks();
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
            result.clear();
        });
    }
}