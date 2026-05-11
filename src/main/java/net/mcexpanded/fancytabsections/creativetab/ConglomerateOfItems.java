package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ConglomerateOfItems
{
    public final List<Object> conglomerate = new ArrayList<>();
    List<ItemStack> cached = null;

    public static ConglomerateOfItems create()
    {
        return new ConglomerateOfItems();
    }

    /** Converts the list of added objects to ItemStacks and caches it */
    public List<ItemStack> toStacks()
    {
        if (cached != null) return cached;
        List<ItemStack> stacks = new ArrayList<>();

        for (Object o : conglomerate)
        {
            if (o instanceof Item i)
            {
                stacks.add(i.getDefaultInstance());
                continue;
            }

            if (o instanceof DeferredItem<?> i)
            {
                stacks.add(i.toStack());
                continue;
            }

            if (o instanceof Supplier<?> i)
            {
                stacks.add((ItemStack) i.get());
                continue;
            }

            if (o instanceof ItemLike i)
            {
                stacks.add(i.asItem().getDefaultInstance());
                continue;
            }

            if (o instanceof DeferredRegister.Items i)
            {
                stacks.addAll(i.getEntries().stream().map(
                        holder -> holder.getDelegate().value().getDefaultInstance()).toList());
            }
        }
        conglomerate.clear();
        cached = stacks;
        return stacks;
    }

    /** Add an Item to the conglomerate */
    public ConglomerateOfItems add(Item item)
    {
        conglomerate.add(item);
        return this;
    }

    /** Add an ItemStack to the conglomerate */
    public ConglomerateOfItems add(Supplier<ItemStack> stack)
    {
        conglomerate.add(stack);
        return this;
    }

    /** Add a DeferredItem to the conglomerate. You may call this even before registration happens */
    public ConglomerateOfItems add(DeferredItem<Item> deferredItem)
    {
        conglomerate.add(deferredItem);
        return this;
    }

    public ConglomerateOfItems add(ItemLike itemLike)
    {
        conglomerate.add(itemLike);
        return this;
    }

    public ConglomerateOfItems add(DeferredRegister.Items deferredRegister)
    {
        conglomerate.add(deferredRegister);
        return this;
    }
}
