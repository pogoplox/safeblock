package turniplabs.examplemod;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.fx.EntityFX;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFirestriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class NitroBlock extends Block implements INitroBlock {

    private float power = 0.0f;

    public NitroBlock(String key, int id, Material material, float _power) {
        super(key, id, material);
        power = _power;

    }


    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        SafeBlockMod.LOGGER.info("onBlockAdded");
        if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
            explode(world,i,j,k);
        }
    }

    @Override
    public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (dropCause == EnumDropCause.EXPLOSION) {
            return null;
        }
        return new ItemStack[]{new ItemStack(this)};
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        if (blockId > 0 && Block.blocksList[blockId].canProvidePower() && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            explode(world, x, y, z);
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z) {
        SafeBlockMod.LOGGER.info("onBlockDestroyedByExplosion");
        explode(world,x,y,z);
        super.onBlockDestroyedByExplosion(world, x, y, z);
    }


    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta, EntityPlayer player, Item item) {
        SafeBlockMod.LOGGER.info("onBlockRemoval");
        if (item != null) {
            if (item.id != Item.toolShears.id) {
                explode(world,x,y,z);
            }
        } else {
            explode(world,x,y,z);
        }

        super.onBlockDestroyedByPlayer(world, x, y, z, meta, player, item);
    }


    @Override
    public boolean collidesWithEntity(Entity entity) {
        if (!(entity instanceof EntityItem) && !(entity instanceof EntityFX)) {
            SafeBlockMod.LOGGER.info("collidesWithEntity");
            explode(entity.world,(int)entity.x,(int)entity.y,(int)entity.z);
        }
        return super.collidesWithEntity(entity);
    }

//    @Override
//    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
//        NitroBlockMod.LOGGER.info("onEntityWalking");
//        explode(world,x,y,z);
//        super.onEntityWalking(world, x, y, z, entity);
//    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        SafeBlockMod.LOGGER.info("blockActivated");
        if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemFirestriker) {
            explode(world,x,y,z);
            return true;
        }
        return super.blockActivated(world, x, y, z, player);
    }


    @Override
    public void explode(World world, int x, int y, int z){
            world.createExplosion(null,(float )x,(float ) y,(float )z,this.power);
        }


}


interface INitroBlock {
     void explode(World world, int x, int y, int z);

}