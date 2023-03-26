package fr.redcraft.command.execution;

import fr.redcraft.Main;
import fr.redcraft.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;

public class ToggleCommand extends AbstractCommand
{
    public ToggleCommand() {
        super("toggle", "Toggle on/off group","§8§l» §b/grouplight toggle §7<name>", "grouplight.toggle", 2, Arrays.asList("toggle"),  new Class[] { String.class});
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        String name = args[1];
        if(!Main.getGroupLight().isExist(name)){
            sender.sendMessage("§cThis GroupLight no exists");
            return;
        }
        if(Main.getGroupLight().toggleGroup(name)){
            player.sendMessage("§aGroupLight turned on");
            return;
        }
        player.sendMessage("§cGroupLight turned off");
        return;
    }
}


