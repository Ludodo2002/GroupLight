package fr.redcraft.command;

import fr.redcraft.command.execution.HelpCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandle implements CommandExecutor {
    private static AbstractCommand helpCommand = new HelpCommand();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        AbstractCommand abstractCommand = null;

        for (AbstractCommand command : AbstractCommand.getCommands()) {
            if (args.length == command.getArgsNumber()) {
                String arg = args[0].toLowerCase();
                if (command.getCommand().equals(arg)) {
                    abstractCommand = command;
                } else {
                    for (String commandAliase : command.getCommandsAliases()) {
                        if (commandAliase.equals(arg)) {
                            abstractCommand = command;
                        }
                    }
                }
            }
        }
        if (abstractCommand == null) abstractCommand = CommandHandle.helpCommand;

        if (!(sender instanceof Player) && !abstractCommand.isConsoleAllowed()) {
            sender.sendMessage("Erreur sender");
            return false;
        }
        if (abstractCommand.hasPermission(sender)) {
            abstractCommand.execute(sender, cmd, args);
        } else {
            sender.sendMessage("§cYou dont have permission");
        }
        return true;
    }
}
