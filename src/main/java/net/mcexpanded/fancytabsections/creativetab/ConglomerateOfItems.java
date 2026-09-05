package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Stores several objects which represent ItemStacks in some way
 *
 * @since 2.0
 */
public class ConglomerateOfItems
{
    public final List<Object> conglomerate = new ArrayList<>();
    private final List<ItemStack> stacks = new ArrayList<>();

    public static ConglomerateOfItems create()
    {
        return new ConglomerateOfItems();
    }

    /**
     * @since 4.0
     */
    public List<ItemStack> getStacks()
    {
        return stacks;
    }

    /**
     * @since 4.0
     */
    public void resolveStacks(RegistryAccess registryAccess)
    {
        stacks.clear();

        for (Object o : conglomerate)
        {
            if (o instanceof ItemStack i)
            {
                stacks.add(i);
                continue;
            }

            if (o instanceof Item i)
            {
                stacks.add(i.getDefaultInstance());
                continue;
            }

            if (o instanceof RegistryObject<?> i)
            {
                if (i.get() instanceof Item item)
                    stacks.add(item.getDefaultInstance());
                continue;
            }

            if (o instanceof ItemLike i)
            {
                stacks.add(i.asItem().getDefaultInstance());
                continue;
            }

//            if (o instanceof DeferredRegister.Items i)
//            {
//                stacks.addAll(i.getEntries().stream().map(
//                        holder -> holder.getDelegate().value().getDefaultInstance()).toList());
//            }

            if (o instanceof Supplier<?> supplier)
            {
                if (supplier.get() instanceof ItemStack is)
                {
                    stacks.add(is);
                }
            }

            if (o instanceof List<?> list)
            {
                if (list.stream().allMatch(ItemStack.class::isInstance))
                {
                    @SuppressWarnings("unchecked")
                    List<ItemStack> itemStacks = (List<ItemStack>) list;
                    stacks.addAll(itemStacks);
                }
            }

            if (o instanceof RegistryDependentEntry entry)
            {
                stacks.addAll(entry.add(registryAccess));
            }
        }
    }

    /**
     * Add an Item to the conglomerate.
     * The item is later resolved into an ItemStack using {@link Item#getDefaultInstance()}}
     *
     * @since 2.0
     */
    public ConglomerateOfItems add(Item item)
    {
        conglomerate.add(item);
        return this;
    }

    /**
     * Add an ItemStack to the conglomerate.
     * The ItemStack is not resolved and is directly passed.
     *
     * @since 2.0
     */
    public ConglomerateOfItems add(ItemStack stack)
    {
        conglomerate.add(stack);
        return this;
    }

    /**
     * Add a {@link RegistryObject} to the conglomerate.
     * You may call this even before registration happens.
     * The RegistryObject is later resolved into an ItemStack using {@link RegistryObject#get()}
     *
     * @since 2.0
     */
    public ConglomerateOfItems add(RegistryObject<Item> registryObjectOfItem)
    {
        conglomerate.add(registryObjectOfItem);
        return this;
    }

    /**
     * Add a {@link ItemLike} to the conglomerate.
     * The ItemLike is later resolved into an ItemStack using {@link ItemLike#asItem()} and {@link Item#getDefaultInstance()}
     *
     * @since 2.0
     */
    public ConglomerateOfItems add(ItemLike itemLike)
    {
        conglomerate.add(itemLike);
        return this;
    }

//    /**
//     * Add a {@link DeferredRegister.Items} to the conglomerate.
//     * The DeferredRegister is later resolved, adding every entry as an Itemstack using {@link Item#getDefaultInstance()}
//     *
//     * @since 2.0
//     */
//    public ConglomerateOfItems add(DeferredRegister.Items deferredRegister)
//    {
//        conglomerate.add(deferredRegister);
//        return this;
//    }

    /**
     * Add a Supplier of ItemStack to the conglomerate.
     * The Supplier is later resolved, adding the ItemStack
     *
     * @since 2.0
     */
    public ConglomerateOfItems add(Supplier<ItemStack> itemStackSupplier)
    {
        conglomerate.add(itemStackSupplier);
        return this;
    }

    /**
     * Add a List of ItemStacks to the conglomerate.
     * @since 4.0
     */
    public ConglomerateOfItems add(List<ItemStack> listofStacks)
    {
        conglomerate.add(listofStacks);
        return this;
    }

    /**
     * Add a RegistryDependentEntry to the conglomerate.
     * This entry has {@link RegistryAccess}
     * The entry is later resolved, adding all the ItemStacks returned
     *
     * @since 4.0
     */
    public ConglomerateOfItems add(RegistryDependentEntry entry)
    {
        conglomerate.add(entry);
        return this;
    }

    /**
     * @since 4.0
     */
    public interface RegistryDependentEntry
    {
        List<ItemStack> add(RegistryAccess registryAccess);
    }
}
