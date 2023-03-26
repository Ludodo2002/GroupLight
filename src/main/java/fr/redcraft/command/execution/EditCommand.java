package fr.redcraft.command.execution;

import fr.redcraft.Main;
import fr.redcraft.command.AbstractCommand;
import fr.redcraft.data.FileManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class EditCommand extends AbstractCommand
{
    public EditCommand() {
        super("edit", "Edit a group", "§8§l» §b/grouplight edit §7<name>","grouplight.edit", 2, Arrays.asList("edit"),  new Class[] { String.class });
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        String name = args[1];
        if(!Main.getGroupLight().isExist(name)){
            sender.sendMessage("§cThis GroupLight no exists");
            return;
        }
        if(Main.getGroupLight().lightCreators.containsKey(sender)){
            sender.sendMessage("§cYou are already creating a GroupLight");
            return;
        }
        Main.getGroupLight().lightCreators.put((Player) sender,name);
        FileManager fileManager = new FileManager((JavaPlugin) Main.getInstance());
        fileManager.getConfig("data.yml").getConfig().set("groups." + name + ".enabled",false);
        fileManager.saveConfig("data.yml");
        player.getInventory().addItem(new ItemStack(Material.LIGHT));
        sender.sendMessage("§7Left click every light block to add a light to the group\n§7Use §e/grouplights stop §7to stop adding lights");
        return;
    }
}


