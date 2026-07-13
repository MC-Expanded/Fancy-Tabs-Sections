package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.Section.Section;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.mixin.CreativeModeTabAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.DefaultDataComponentsBoundEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Methods in this class might change name or be removed. It is highly recommended you do not call them.
 * You should only interact with FancyTabSections by using {@link FancyTabSections}
 *
 * @since 4.0
 */
@EventBusSubscriber(modid = FancyTabSections.MOD_ID)
@ApiStatus.Internal
public class FTSInternal
{
    /**
     * Retrieves the items stored in the sections and applies them to the given CreativeModeTab
     *
     * @since 4.0
     */
    public static void applyItems(CreativeModeTab tab)
    {
        List<ItemStack> stacksToDisplay = new ArrayList<>();

        List<Section<?>> sections = FancyTabSections.getSections(tab);

        //if tab doesn't contain any sections, end pipeline
        if (sections.isEmpty()) return;

        for (Section<?> section : sections)
        {
            //add empty row for banner
            for (int i = 0; i < 9; i++)
                stacksToDisplay.add(ItemStack.EMPTY);

            //if section is collapsed, don't add items
            if (isCollapsed(section))
                continue;

            stacksToDisplay.addAll(section.items().getStacks());

            //add empty stacks to fill the entire row before next banner
            int usedInLastRow = stacksToDisplay.size() % 9;
            if (usedInLastRow != 0)
                for (int i = 0; i < 9 - usedInLastRow; i++)
                    stacksToDisplay.add(ItemStack.EMPTY);
        }

        //set display items
        ((CreativeModeTabAccessor) tab).setDisplayItems(stacksToDisplay);

        //set searchable items set
        ((CreativeModeTabAccessor) tab).setDisplayItemsSearchTab(
                stacksToDisplay.stream()
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    /**
     * Triggered on world join, and /reload.
     * Used to store full RegistryAccess, considering ResourceListeners do not have tags bound
     *
     * @since 4.0
     */
    @SubscribeEvent
    public static void tagsUpdatedEvent(TagsUpdatedEvent event)
    {
        registryAccess = event.getRegistries();
        if(!hasDataComponentsBound) return;
        refreshAllItems(registryAccess);
        BannerRenderer.CURRENT_TAB = null;
    }

    static RegistryAccess registryAccess;
    /**
     * Only present in 26.2+, as the order these events are triggered changes only on client.
     */
    static boolean hasDataComponentsBound = false;

    /**
     * Used as a work-around for refreshing section items on world join, and /reload
     * @since 4.0
     */
    @SubscribeEvent
    public static void componentsBoundEvent(DefaultDataComponentsBoundEvent event)
    {
        hasDataComponentsBound = true;
        if(registryAccess == null) return;
        refreshAllItems(registryAccess);
        BannerRenderer.CURRENT_TAB = null;
        registryAccess = null;
    }

    /**
     * Refreshed all ConglomerateOfItems in registered sections
     *
     * @since 4.0
     */
    public static void refreshAllItems(RegistryAccess registryAccess)
    {
        //for each registered tab
        FancyTabSections.REGISTERED_TABS.forEach((rl, sections) ->
        {
            //for each section in that tab
            for (Section<?> section : sections)
            {
                //resolve stacks from conglomerate
                section.items().resolveStacks(registryAccess);
            }
        });
    }

    /**
     * @return The row number of the requested section, -1 if section was not found in any registered tab
     * @since 4.0
     */
    public static int getRowForSection(Section<?> section)
    {
        //for each list of sections in registered tabs
        for (List<Section<?>> list : FancyTabSections.REGISTERED_TABS.values())
        {
            if (list.contains(section))
            {
                int currentRow = 0;
                for (Section<?> sectionBeingChecked : list)
                {
                    //if sectionBeingChecked is the section requested, return the currentRow
                    if (sectionBeingChecked == section) return currentRow;
                    //int division smiley face :)
                    currentRow++;
                    if (!isCollapsed(sectionBeingChecked))
                        currentRow += (sectionBeingChecked.items().getStacks().size() - 1) / 9 + 1;
                }
            }
        }
        return -1;
    }


    /**
     * Contains a set of all collapsed sections
     *
     * @since 4.0
     */
    public static final Set<Section<?>> COLLAPSED = new HashSet<>();

    /**
     * @return Whether the requested section is collapsed or not
     * @since 4.0
     */
    public static boolean isCollapsed(Section<?> section)
    {
        return COLLAPSED.stream().anyMatch(o -> o.equals(section));
    }

    /**
     * Toggles the collapse requested section
     *
     * @since 4.0
     */
    public static void toggle(Section<?> section)
    {
        if (!FTSInternal.isCollapsed(section))
            collapse(section, true);
        else
            expand(section, true);
    }

    /**
     * Collapses the requested tab
     *
     * @since 4.0
     */
    public static void collapse(Section<?> section, boolean playSound)
    {
        FTSInternal.COLLAPSED.add(section);

        if (FMLEnvironment.getDist().isClient() && playSound)
            Client.playSound(!FTSInternal.isCollapsed(section));
    }

    /**
     * Expands the requested tab
     *
     * @since 4.0
     */
    public static void expand(Section<?> section, boolean playSound)
    {
        FTSInternal.COLLAPSED.removeIf(o -> o.equals(section));

        if (FMLEnvironment.getDist().isClient() && playSound)
            Client.playSound(!FTSInternal.isCollapsed(section));
    }

    /**
     * @return Whether the given row is the first row of a section, referred to as the banner
     * @since 4.0
     */
    public static boolean isBannerRow(Identifier tab, int row)
    {
        List<Section<?>> sections = FancyTabSections.REGISTERED_TABS.get(tab);
        if (sections == null) return false;

        for (Section<?> section : sections)
        {
            if (getRowForSection(section) == row) return true;
        }
        return false;
    }

    /**
     * Client-Only class to handle sounds, preventing crashes from third parties who might call unsafe methods directly.
     *
     * @since 4.0
     */
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

