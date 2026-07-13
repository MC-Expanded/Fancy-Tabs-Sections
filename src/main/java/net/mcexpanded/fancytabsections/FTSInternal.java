package net.mcexpanded.fancytabsections;

import net.mcexpanded.fancytabsections.Section.Section;
import net.mcexpanded.fancytabsections.Section.StickySection;
import net.mcexpanded.fancytabsections.creativetab.BannerRenderer;
import net.mcexpanded.fancytabsections.mixin.CreativeModeTabAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Methods in this class might change name or be removed. It is highly recommended you do not call them.
 * You should only interact with FancyTabSections by using {@link FancyTabSections}
 *
 * @since 4.0
 */
@EventBusSubscriber(modid = FancyTabSections.MOD_ID)
@ApiStatus.Internal
@Mod(FancyTabSections.MOD_ID)
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
     * Triggered on world join, and /reload
     * Used as a work-around for full RegistryAccess as ResourceListeners do not have access to tags
     *
     * @since 4.0
     */
    @SubscribeEvent
    public static void tagsUpdatedEvent(TagsUpdatedEvent event)
    {
        refreshAllItems(event.getRegistryAccess());
        BannerRenderer.CURRENT_TAB = null;
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
                    // if sectionBeingChecked is the section requested
                    if (sectionBeingChecked == section)
                    {
                        int contentRows = isCollapsed(sectionBeingChecked) ? 0 : (sectionBeingChecked.items().getStacks().size() - 1) / 9 + 1;

                        int sectionEnd = currentRow + contentRows;

                        if (
                                sectionBeingChecked instanceof StickySection sticky
                                && sticky.isSticky()
                                && BannerRenderer.CURRENT_ROW >= currentRow
                                && BannerRenderer.CURRENT_ROW <= sectionEnd
                        )
                            return BannerRenderer.CURRENT_ROW;

                        return currentRow;
                    }

                    // Banner row
                    currentRow++;

                    // Content rows
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

        if (FMLEnvironment.dist.isClient() && playSound)
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

        if (FMLEnvironment.dist.isClient() && playSound)
            Client.playSound(!FTSInternal.isCollapsed(section));
    }

    /**
     * @return Whether the given row is the first row of a section, referred to as the banner
     * @since 4.0
     */
    public static boolean isBannerRow(ResourceLocation tab, int row)
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

    public FTSInternal(IEventBus modEventBus, ModContainer modContainer)
    {
        new FTSExampleMod(modEventBus);
    }

    public static Supplier<CreativeModeTab> registerTab(IEventBus bus, ResourceLocation rl, Supplier<ItemStack> sup)
    {
        DeferredRegister<CreativeModeTab> deferredRegister =
                DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, rl.getNamespace());

        Supplier<CreativeModeTab> tab = deferredRegister.register(rl.getPath(), () -> CreativeModeTab.builder()
                .icon(sup)
                .title(Component.translatable("itemGroup." + rl.getNamespace() + "." + rl.getPath()))
                .displayItems((params, output) -> output.accept(Items.BARRIER))
                .build());

        deferredRegister.register(bus);
        return tab;
    }
}

