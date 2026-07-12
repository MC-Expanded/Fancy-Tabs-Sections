package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.Section.Section;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod(FancyTabSections.MOD_ID)
public class FancyTabSections
{
    public static final String MOD_ID = "fancytabsections";

    /**
     * An example of an implementation can be found on FTSExampleMod.
     * Check the wiki on GitHub or directly contact me on discord for any questions that the wiki could not answer.
     */
    public FancyTabSections()
    {
    }

    public static final Map<ResourceLocation, List<Section<?>>> REGISTERED_TABS = new HashMap<>();

    /**
     * Adds a new Section to the given CreativeModeTab Identifier
     * @since 1.0
     */
    public static void addSection(ResourceLocation tab, Section section)
    {
        REGISTERED_TABS.computeIfAbsent(tab, k -> new ArrayList<>()).add(section);
    }

    /**
     * @return A registered section, or null if none were found
     * @since 4.0
     */
    public static Section<?> getSection(ResourceLocation id)
    {
        for (List<Section<?>> entry : REGISTERED_TABS.values())
            if (entry != null)
                for (Section<?> section : entry)
                    if (section.id().equals(id))
                        return section;
        return null;
    }

    /**
     * @return The list of sections registered, or an empty list if none are found for the requested CreativeModeTab
     * @since 4.0
     */
    public static List<Section<?>> getSections(CreativeModeTab tab)
    {
        ResourceLocation rl = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab);

        return FancyTabSections.REGISTERED_TABS.getOrDefault(rl, new ArrayList<>());
    }
}
