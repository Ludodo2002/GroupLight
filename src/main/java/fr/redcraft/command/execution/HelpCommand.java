package fr.redcraft.command.execution;

import fr.redcraft.Helper;
import fr.redcraft.command.AbstractCommand;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HelpCommand extends AbstractCommand
{
    public HelpCommand() {
        super("help", "Shows the the available commands","§8§l» §b/grouplight help", "grouplight.help", 1, Arrays.asList("aide"), false);
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        for (AbstractCommand command : AbstractCommand.getCommands()) {
            ComponentBuilder builder = new ComponentBuilder("§8§l» §b/grouplight " + command.getCommand() + " §3" + command.getCommandDescription());
            Helper.hoverMessage(builder,Arrays.asList(command.getEXAMPLE(),"\n§f" + command.getPermission()));
            player.spigot().sendMessage(builder.create());
        }
    }
}

