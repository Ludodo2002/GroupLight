package fr.redcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand
{
    private static List<AbstractCommand> commands = new ArrayList<>();

    private final String COMMAND;
    private final String COMMAND_DESCRIPTION;
    private final String PERMISSION;
    private final String EXAMPLE;
    private int argsNumber;
    private Class<?>[] parametersType;
    private List<String> aliases = new ArrayList<>();
    private boolean allowConsole; // If true -> Allow console command


    public AbstractCommand(String command,String commandDescription,String example, String permission, int argsNumber, Class<?>... parametersType) {
        this.COMMAND = command;
        this.COMMAND_DESCRIPTION = commandDescription;
        this.EXAMPLE = example;
        this.PERMISSION = permission;
        this.argsNumber = argsNumber;
        this.allowConsole = false;
        this.parametersType = parametersType;
        commands.add(this);
    }

    public AbstractCommand(String command,String commandDescription,String example,String permission, int argsNumber,List<String> aliases, Class<?>... parametersType) {
        this(command, commandDescription,example, permission, argsNumber, parametersType);
        this.aliases = aliases;
    }

    public AbstractCommand(String command, String commandDescription,String example,String permission, int argsNumber, boolean allowConsole, Class<?>... parametersType) {
        this(command, commandDescription,example, permission, argsNumber, parametersType);
        this.allowConsole = allowConsole;
    }

    public AbstractCommand(String command,String commandDescription, String example,String permission, int argsNumber,List<String> aliases, boolean allowConsole, Class<?>... parametersType) {
        this(command, commandDescription,example, permission, argsNumber, aliases, parametersType);
        this.allowConsole = allowConsole;
    }

    public static List<AbstractCommand> getCommands() {
        return commands;
    }

    public String getCommand() {
        return COMMAND;
    }

    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

    public String getPermission() {
        return PERMISSION;
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(this.PERMISSION) || sender.isOp() || sender.hasPermission("light.admin");
    }
    public List<String> getCommandsAliases() {
        return aliases;
    }

    public void addCommandAliase(String command) {
        this.aliases.add(command);
    }

    public int getArgsNumber() {
        return argsNumber;
    }

    public Class<?>[] getParametersType() {
        return parametersType;
    }

    public boolean isConsoleAllowed() {
        return allowConsole;
    }

    public String getEXAMPLE() {
        return EXAMPLE;
    }

    public abstract void execute(CommandSender sender, Command cmd, String[] args);
}

