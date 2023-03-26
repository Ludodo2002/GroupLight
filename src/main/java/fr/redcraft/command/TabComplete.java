package fr.redcraft.command;

import java.util.ArrayList;
import java.util.List;

import fr.redcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        final int argsNumber = args.length;
        List<String> completions = new ArrayList<>();

        for (AbstractCommand command : AbstractCommand.getCommands()) {
            String commandName = command.getCommand();

            // Multiple args
            if (argsNumber > 1) {
                if (commandName.equals(args[0].toLowerCase())) {
                    Class<?>[] paramsTypeArray = command.getParametersType();

                    if (paramsTypeArray != null && paramsTypeArray.length > 0) {
                        if (paramsTypeArray.length > argsNumber - 2) {
                            Class<?> clazz = paramsTypeArray[argsNumber - 2];

                            if (clazz == Player.class) {
                                for (Player player : Bukkit.getOnlinePlayers()) completions.add(player.getName());
                            } else if (clazz == String.class) {
                                for (String name : Main.getGroupLight().getGroups()) completions.add(name);
                            } else if (clazz == Integer.class) {
                                for (int i = 0; i < 9999; i++) completions.add(String.valueOf(i));
                            }
                        }
                    }
                }
            } else {
                //if (argsNumber == command.getArgsNumber()) {
                if (command.hasPermission(sender)) {
                    if (commandName.startsWith(args[0].toLowerCase())) {
                        completions.add(commandName);
                    }
                }
                //}
            }
        }
        return completions;
    }
}
