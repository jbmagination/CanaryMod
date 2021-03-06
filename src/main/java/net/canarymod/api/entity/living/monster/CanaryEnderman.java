package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.minecraft.entity.monster.EntityEnderman;

/**
 * Enderman wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryEnderman extends CanaryEntityMob implements Enderman {

    /**
     * Constructs a new wrapper for EntityEnderman
     *
     * @param entity
     *         the EntityEnderman to wrap
     */
    public CanaryEnderman(EntityEnderman entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ENDERMAN;
    }

    @Override
    public String getFqName() {
        return "Enderman";
    }

    @Override
    public Block getCarriedBlock() {
        return CanaryBlock.getPooledBlock(getHandle().ck(), new BlockPosition(this.getPosition()).asNative(), this.entity.e());
    }

    @Override
    public void setCarriedBlock(Block block) {
        getHandle().a(((CanaryBlock)block).getNativeState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getCarriedBlockID() {
        return (short)net.minecraft.block.Block.a(getHandle().ck().c());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCarriedBlockID(short blockId) {
        getHandle().a(net.minecraft.block.Block.d(blockId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getCarriedBlockMetaData() {
        return (short) getHandle().ck().c().c(getHandle().ck());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCarriedBlockMetaData(short metadata) {
        //getHandle().a(metadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean randomTeleport() {
        return getHandle().n();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isScreaming() {
        return getHandle().cm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreaming(boolean screaming) {
        getHandle().a(screaming);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityEnderman getHandle() {
        return (EntityEnderman) entity;
    }
}
