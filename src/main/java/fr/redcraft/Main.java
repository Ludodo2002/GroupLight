package fr.redcraft;

import fr.redcraft.command.CommandHandle;
import fr.redcraft.command.TabComplete;
import fr.redcraft.command.execution.*;
import fr.redcraft.data.FileManager;
import fr.redcraft.event.PlayerListener;
import fr.redcraft.light.GroupLight;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Plugin instance;
    public static GroupLight groupLight;

    @Override
    public void onEnable() {
        instance = this;
        groupLight = new GroupLight();
        loadConfig();
        onRegisterCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
    }

    @Override
    public void onDisable() {
        saveConfigs();
    }

    public void loadConfig() {
        FileManager fileManager = new FileManager(this);
        fileManager.getConfig("data.yml").saveDefaultConfig();
    }

    public void saveConfigs() {
        FileManager fileManager = new FileManager(this);
        fileManager.saveConfig("data.yml");
    }

    private void onRegisterCommands(){
        getCommand("grouplight").setExecutor(new CommandHandle());
        getCommand("grouplight").setTabCompleter(new TabComplete());
        new CreateCommand();
        new StopCommand();
        new ToggleCommand();
        new FadeCommand();
        new EditCommand();
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static GroupLight getGroupLight() {
        return groupLight;
    }
}
