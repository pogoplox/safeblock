package turniplabs.examplemod;


import net.java.games.input.Keyboard;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import static turniplabs.examplemod.LockBlock.BLOCK_ACTIVATED;

public class SafeBlock extends BlockTileEntity {
    public SafeBlock(String key, int id,  Material material) {
        super(key, id, material);
    }

    protected TileEntity getNewBlockEntity() {
        return new TileEntitySafe();
    }

    @Override
    public int getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        return super.getBlockTexture(blockAccess, x, y, z, side);
    }


    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        if (world.isClientSide) {
            return true;
        }

        LockLogic.setOnLogicListener(world,x,y,z,player,new LockLogic() {
            @Override
            public void onSuccess(World world, int x, int y, int z, EntityPlayer player) {
                world.setBlockMetadataWithNotify(x,y,z,BLOCK_ACTIVATED);
                IInventory inventory = (IInventory)((Object)world.getBlockTileEntity(x, y, z));
                player.displayGUIChest(inventory);

            }

            @Override
            public void onOnFailure(World world, int x, int y, int z, EntityPlayer player) {
                world.setBlockMetadataWithNotify(x,y,z,player.id);
                IInventory inventory = (IInventory)((Object)world.getBlockTileEntity(x, y, z));
                player.displayGUIChest(inventory);
            }
        });

        return true;
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side placeSide, EntityLiving entity, double sideHeight) {
        super.onBlockPlaced(world, x, y, z, placeSide, entity, sideHeight);
    }

    @Override
    public boolean isInAll(Tag<Block>... tags) {
        return super.isInAll(tags);
    }
}
