package fr.redcraft.command.execution;

import fr.redcraft.Main;
import fr.redcraft.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class FadeCommand extends AbstractCommand
{
    public FadeCommand() {
        super("fade", "Change fade of light","§8§l» §b/grouplight fade §7<name> <level> <fade>", "grouplight.fade", 4, Arrays.asList("toggle"),  new Class[] { String.class,Integer.class,Integer.class });
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String[] args) {
        String name = args[1];
        Integer level = 0;
        Integer fade = 0;
        try {
            level = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(args[0] + " n'est pas un chiffre");
        }
        try {
            fade = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(args[1] + " n'est pas un chiffre");
        }
        if(!Main.getGroupLight().isExist(name)){
            sender.sendMessage("§cThis GroupLight no exists");
            return;
        }
        Main.getGroupLight().fadeLamp(name,level,fade);
        return;
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}


