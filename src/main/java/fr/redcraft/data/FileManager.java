package fr.redcraft.data;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class FileManager {

    private JavaPlugin plugin;
    private HashMap<String,Config> configs = new HashMap<>();

    public FileManager(JavaPlugin plugin){
        this.plugin = plugin;
    }
    public Config getConfig(String name) {
        if (!this.configs.containsKey(name))
            this.configs.put(name, new Config(name));
        return this.configs.get(name);
    }

    public Config saveConfig(String name) {
        return getConfig(name).save();
    }

    public Config reloadConfig(String name) {
        return getConfig(name).reload();
    }
}
