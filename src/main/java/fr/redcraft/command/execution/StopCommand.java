package fr.redcraft.command.execution;

import fr.redcraft.Main;
import fr.redcraft.command.AbstractCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;

public class StopCommand extends AbstractCommand
{
    public StopCommand() {
        super("stop", "Stop editing group","§8§l» §b/grouplight stop", "grouplight.stop", 1, Arrays.asList("stop"), false);
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        if(Main.getGroupLight().lightCreators.containsKey(sender)){
            Main.getGroupLight().lightCreators.remove(player);
            sender.sendMessage("§7You stopped adding lights");
            player.getInventory().remove(Material.LIGHT);
            return;
        }else {
            sender.sendMessage("§cYou are not creating a GroupLight");
            return;
        }
    }
}

