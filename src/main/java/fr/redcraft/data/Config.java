package fr.redcraft.data;

import fr.redcraft.Main;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Config {

    private String name;
    private File file;
    private YamlConfiguration config;

    public Config(String name){
        this.name = name;
    }

    public Config save(){
        if(this.config == null || this.file == null){
            return this;
        }
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public YamlConfiguration getConfig(){
        if(this.config == null)
            reload();
        return this.config;
    }

    public Config saveDefaultConfig() {
        this.file = new File(Main.getInstance().getDataFolder(), this.name);
        Main.getInstance().saveResource(this.name, false);
        return this;
    }

    public Config reload() {
        if (this.file == null)
            this.file = new File(Main.getInstance().getDataFolder(), this.name);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        try {
            Reader defConfigStream = new InputStreamReader(Main.getInstance().getResource(this.name), "UTF8");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.config.setDefaults((Configuration)defConfig);
            }
        } catch (UnsupportedEncodingException | NullPointerException unsupportedEncodingException) {}
        return this;
    }

    public Config copyDefaults(boolean force) {
        getConfig().options().copyDefaults(force);
        return this;
    }

    public Config set(String key, Object value) {
        getConfig().set(key, value);
        return this;
    }

    public Object get(String key) {
        return getConfig().get(key);
    }
}
