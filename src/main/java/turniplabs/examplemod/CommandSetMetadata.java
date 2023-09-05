package turniplabs.examplemod;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandError;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public class CommandSetMetadata extends Command {
    public CommandSetMetadata() {
        super("setMetaDataItem", new String[0]);
    }


    @Override
    public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
        EntityPlayer currentPLayer = this.getPlayer(commandHandler,strings[0]);
        currentPLayer.getCurrentEquippedItem().setMetadata(Integer.parseInt(strings[1]));
        NitroBlockMod.LOGGER.info("Metadata setted to item: " + strings[1]);

        return false;
    }

    public EntityPlayer getPlayer(CommandHandler handler, String name) {
        EntityPlayer player = handler.getPlayer(name);
        if (player == null) {
            throw new CommandError("Player not found: " + name);
        }
        return player;
    }

    @Override
    public boolean opRequired(String[] strings) {
        return false;
    }

    @Override
    public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {

    }
}
