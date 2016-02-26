package net.canarymod.api.world.blocks;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.InventoryType;
import net.canarymod.api.inventory.Item;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.ILockableContainer;

import java.util.Arrays;

/**
 * Chest wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryChest extends CanaryLockableTileEntity implements Chest {

    /**
     * Constructs a new wrapper for TileEntityChest
     *
     * @param tileentity
     *         the TileEntityChest to be wrapped
     */
    public CanaryChest(TileEntityChest tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }

    @Override
    public boolean hasAttachedChest() {
        getTileEntity().m(); // SAFETY CHECK
        return getTileEntity().f != null && getTileEntity().g != null &&
                getTileEntity().h != null && getTileEntity().i != null;
    }

    @Override
    public DoubleChest getDoubleChest() {
        if(!hasAttachedChest()) {
            return null;
        }
        if(getTileEntity().f != null) {
            return new CanaryDoubleChest(new InventoryLargeChest("container.chestDouble", getTileEntity().f, getTileEntity()));
        }
        if(getTileEntity().h != null) {
            return new CanaryDoubleChest(new InventoryLargeChest("container.chestDouble", getTileEntity().h, getTileEntity()));
        }
        if(getTileEntity().g != null) {
            return new CanaryDoubleChest(new InventoryLargeChest("container.chestDouble", getTileEntity(), getTileEntity().g));
        }
        return new CanaryDoubleChest(new InventoryLargeChest("container.chestDouble", getTileEntity(), getTileEntity().i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        Arrays.fill(getTileEntity().m, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getTileEntity().m, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getTileEntity().m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, getTileEntity().m, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryName(String value) {
        getTileEntity().a(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntityChest getTileEntity() {
        return (TileEntityChest)tileentity;
    }
}
