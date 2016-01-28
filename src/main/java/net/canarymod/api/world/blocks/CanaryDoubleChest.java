package net.canarymod.api.world.blocks;

import net.canarymod.ToolBox;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

import java.util.Arrays;

/**
 * DoubleChest implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryDoubleChest extends CanaryChest implements DoubleChest {
    private InventoryLargeChest largechest;

    /**
     * Constructs a new wrapper for InventoryLargeChest
     *
     * @param largechest
     *         the InventoryLargeChest to be wrapped
     */
    public CanaryDoubleChest(InventoryLargeChest largechest) {
        super((TileEntityChest) largechest.b);
        this.largechest = largechest;
        inventory = largechest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        getHandleA().getCanaryChest().clearContents();
        getHandleB().getCanaryChest().clearContents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        Item[] itemsA = Arrays.copyOf(getHandleA().getCanaryChest().getContents(), getHandleA().getCanaryChest().getSize());
        Item[] itemsB = Arrays.copyOf(getHandleB().getCanaryChest().getContents(), getHandleB().getCanaryChest().getSize());

        clearContents();
        Item[] toRet = ToolBox.arrayMerge(itemsA, itemsB);

        return toRet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return ToolBox.arrayMerge(getHandleA().getCanaryChest().getContents(), getHandleB().getCanaryChest().getContents());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        int aSize = getHandleA().getCanaryChest().getSize();
        int bSize = getHandleB().getCanaryChest().getSize();
        ItemStack[] stacks = CanaryItem.itemArrayToStackArray(items);
        ItemStack[] stacksA = new ItemStack[aSize];
        ItemStack[] stacksB = new ItemStack[bSize];

        for (int index = 0; index < getSize(); index++) {
            if (index < aSize) {
                stacksA[index] = stacks[index];
            }
            else {
                stacksB[index - aSize] = stacks[index];
            }
        }
    }
    
    @Override
    public int getSize() { // Override so both inventories are read
        return largechest.a();
    }

    @Override
    public Item getSlot(int index) {  // Override so both inventories are read
        ItemStack stack = largechest.a(index);
        if (stack != null) {
            Item slot_item = stack.getCanaryItem();
            slot_item.setSlot(index);
            return slot_item;
        }
        return null;
    }

    @Override
    public void setSlot(int index, Item value) {  // Override so both inventories are read
        this.largechest.a(index, value == null ? null : ((CanaryItem) value).getHandle());
    }

    /**
     * Gets the TileEntityChest part A
     *
     * @return part A
     */
    public TileEntityChest getHandleA() {
        return (TileEntityChest) largechest.b;
    }

    /**
     * Gets the TileEntityChest part B
     *
     * @return part B
     */
    public TileEntityChest getHandleB() {
        return (TileEntityChest) largechest.c;
    }
}
