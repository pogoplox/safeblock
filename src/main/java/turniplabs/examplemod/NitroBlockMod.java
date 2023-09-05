package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.sound.block.BlockSound;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.Item;
import net.minecraft.core.net.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;

import java.util.ArrayList;
import java.util.List;


public class NitroBlockMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Block lockBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTextures("lock_block.png")
            .build(new LockBlock("lock.block", 1004, Material.explosive));

    public static final Block safeBlock = new BlockBuilder(MOD_ID)
            .setBlockSound(new BlockSound("step.wood", "step.wood", 1.0f, 1.0f))
            .setHardness(2.0f)
            .setResistance(2.0f)
            .setTopTexture("safe_block_side.png")
            .setBottomTexture("safe_block_bottom.png")
            .setSouthTexture("safe_block_front.png")
            .setNorthTexture("safe_block_side.png")
            .setEastTexture("safe_block_side.png")
            .setWestTexture("safe_block_side.png")
            .build(new SafeBlock("safeBlock", 1009, Material.explosive));


    public static final Item keyGold = ItemHelper.createItem(MOD_ID, new Item(17003), "key.gold", "goldkey.png").setMaxStackSize(1);


    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
         ///List<Command> commands = new ArrayList<Command>();


        CommandHelper.createCommand(new CommandSetMetadata());

    }
}
