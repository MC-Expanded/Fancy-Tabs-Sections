package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabLayout {

    public static final Map<String, Integer> SECTION_ROW = new HashMap<>();

    // Stored at init time, read by the mixin at buildContents time
    public static List<ItemStack> CACHED_ITEMS = List.of();

    public static List<ItemStack> build(List<Section> sections) {
        SECTION_ROW.clear();
        List<ItemStack> result = new ArrayList<>();
        int row = 0;

        for (Section section : sections) {
            // Blank banner row
            SECTION_ROW.put(section.id(), row);
            for (int i = 0; i < 9; i++) {
                result.add(ItemStack.EMPTY);
            }
            row++;

            List<ItemStack> stacks = section.items().stream()
                    .map(ItemStack::new)
                    .toList();
            result.addAll(stacks);

            int itemCount = stacks.size();
            int usedInLastRow = itemCount % 9;
            if (usedInLastRow != 0) {
                int padding = 9 - usedInLastRow;
                for (int i = 0; i < padding; i++) {
                    result.add(ItemStack.EMPTY);
                }
                row += (itemCount / 9) + 1;
            } else {
                row += itemCount / 9;
            }
        }

        CACHED_ITEMS = result; // result.stream().map(ItemStackTemplate::fromNonEmptyStack).toList();
        return result;
    }
}