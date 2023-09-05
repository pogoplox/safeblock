package turniplabs.examplemod;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

public class LockBlock extends Block {
    public LockBlock(String key, int id,  Material material) {
        super(key, id, material);
    }

    public static int BLOCK_ACTIVATED = 2;
    public static int BLOCK_DEACTIVATED = 0;

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
        super.onBlockPlaced(world, x, y, z, side, entity, sideHeight);
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x,y,z,0);
        super.onBlockRemoval(world, x, y, z);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {

        LockLogic.setOnLogicListener(world,x,y,z,player,new LockLogic() {
            @Override
            public void onSuccess(World world, int x, int y, int z, EntityPlayer player) {
                world.setBlockMetadataWithNotify(x,y,z,BLOCK_ACTIVATED);

            }

            @Override
            public void onOnFailure(World world, int x, int y, int z, EntityPlayer player) {
                world.setBlockMetadataWithNotify(x,y,z,player.id);

            }
        });

        return super.blockActivated(world, x, y, z, player);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean isPoweringTo(WorldSource blockAccess, int x, int y, int z, int side) {
        if (blockAccess.getBlockMetadata(x,y,z) == BLOCK_DEACTIVATED || blockAccess.getBlockMetadata(x,y,z) == BLOCK_ACTIVATED) {
            return false;
        } else {
            return true;
        }

    }

}
