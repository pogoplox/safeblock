package turniplabs.examplemod;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import static turniplabs.examplemod.LockBlock.BLOCK_ACTIVATED;
import static turniplabs.examplemod.LockBlock.BLOCK_DEACTIVATED;

public class LockLogic implements ILockLogic {


    public static void setOnLogicListener(World world, int x, int y, int z, EntityPlayer player , ILockLogic listener){
        ItemStack currentItem = player.inventory.getCurrentItem();

        if (currentItem != null && player.inventory.getCurrentItem().getItem().id == 17003) {
            Integer itemMetadataInt = currentItem.getMetadata();

            Integer defaultMetadata = world.getBlockMetadata(x,y,z);
            byte byteMetadata = defaultMetadata.byteValue();

            Integer activatedInt = player.id;
            byte byteActivated = activatedInt.byteValue();

            if (byteMetadata == BLOCK_DEACTIVATED && itemMetadataInt.byteValue() == BLOCK_DEACTIVATED){
                currentItem.setMetadata(player.id);
                world.setBlockMetadata(x,y,z,player.id);
                player.addChatMessage("Key paired with block " + "Player ID:" + String.valueOf(player.id));
                return;
            }

            if (itemMetadataInt.byteValue() == byteActivated) {
                if (byteMetadata == byteActivated ) {
                    listener.onSuccess(world,x,y,z,player);
                } else if (byteMetadata == 2 )  {
                    listener.onOnFailure(world,x,y,z,player);
                }
            }

        }

    }

    @Override
    public void onSuccess(World world, int x, int y, int z, EntityPlayer player) {

    }

    @Override
    public void onOnFailure(World world, int x, int y, int z, EntityPlayer player) {

    }
}

interface ILockLogic {

    void onSuccess(World world, int x, int y, int z, EntityPlayer player);
    void onOnFailure(World world, int x, int y, int z, EntityPlayer player);
}
