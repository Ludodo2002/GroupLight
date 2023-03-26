package fr.redcraft.light;

import fr.redcraft.Main;
import fr.redcraft.data.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupLight {

    public HashMap<Player, String> lightCreators = new HashMap<>();

    public boolean getGroupStatus(String name){
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        return fileManager.getConfig("data.yml").getConfig().getBoolean("groups." + name + ".enabled");
    }

    public String getIDFromLocation(String name, Location location){
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        for (String locs : fileManager.getConfig("data.yml").getConfig().getConfigurationSection("groups." + name).getKeys(false)) {
            if (!locs.equalsIgnoreCase("enabled")) {
                String world = fileManager.getConfig("data.yml").getConfig().getString("groups." + name + "." + locs + ".world");
                int x = fileManager.getConfig("data.yml").getConfig().getInt("groups." + name + "." + locs + ".x");
                int y = fileManager.getConfig("data.yml").getConfig().getInt("groups." + name + "." + locs + ".y");
                int z = fileManager.getConfig("data.yml").getConfig().getInt("groups." + name + "." + locs + ".z");
                Location loc2 = new Location(Bukkit.getWorld(world), x, y, z);
                if (loc2.equals(location))
                    return locs;
            }
        }
        return null;
    }

    public List<String> getGroups(){
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        List<String> groups = new ArrayList<>();
        groups.addAll(fileManager.getConfig("data.yml").getConfig().getConfigurationSection("groups.").getKeys(false));
        return groups;
    }

    public boolean isExist(String name){
        FileManager fileManager = new FileManager((JavaPlugin) Main.getInstance());
        if (fileManager.getConfig("data.yml").getConfig().contains("groups." + name))
            return true;
        return false;
    }

    public void removeGroup(String name){
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        fileManager.getConfig("data.yml").getConfig().set("groups." + name, null);
        fileManager.saveConfig("data.yml");
    }

    public List<Location> getLampLocations(String groupname) {
        List<Location> locs = new ArrayList<>();
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        ConfigurationSection section = fileManager.getConfig("data.yml").getConfig().getConfigurationSection("groups." + groupname);
        try {
            for (String location : section.getKeys(false)) {
                if (!location.equalsIgnoreCase("enabled")) {
                    String world = fileManager.getConfig("data.yml").getConfig().getString("groups." + groupname + "." + location + ".world");
                    int x = fileManager.getConfig("data.yml").getConfig().getInt("groups." + groupname + "." + location + ".x");
                    int y = fileManager.getConfig("data.yml").getConfig().getInt("groups." + groupname + "." + location + ".y");
                    int z = fileManager.getConfig("data.yml").getConfig().getInt("groups." + groupname + "." + location + ".z");
                    Location loc = new Location(Bukkit.getWorld(world), x, y, z);
                    locs.add(loc);
                }
            }
            return locs;
        } catch (Exception ex) {
            return locs;
        }
    }
    public void addLocation(String group,Location loc){
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        int size = getLampLocations(group).size();
        fileManager.getConfig("data.yml").getConfig().set("groups." + group + "." + (size + 1) + ".world", loc.getWorld().getName());
        fileManager.getConfig("data.yml").getConfig().set("groups." + group + "." + (size + 1) + ".x", Integer.valueOf(loc.getBlockX()));
        fileManager.getConfig("data.yml").getConfig().set("groups." + group + "." + (size + 1) + ".y", Integer.valueOf(loc.getBlockY()));
        fileManager.getConfig("data.yml").getConfig().set("groups." + group + "." + (size + 1) + ".z", Integer.valueOf(loc.getBlockZ()));
        fileManager.saveConfig("data.yml");
    }

    public boolean lampExists(String group, Location loc) {
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        try {
            for (String locs : fileManager.getConfig("data.yml").getConfig().getConfigurationSection("groups." + group).getKeys(false)) {
                if (!locs.equalsIgnoreCase("enabled")) {
                    String world = fileManager.getConfig("data.yml").getConfig().getString("groups." + group + "." + locs + ".world");
                    int x = fileManager.getConfig("data.yml").getConfig().getInt("groups." + group + "." + locs + ".x");
                    int y = fileManager.getConfig("data.yml").getConfig().getInt("groups." + group + "." + locs + ".y");
                    int z = fileManager.getConfig("data.yml").getConfig().getInt("groups." + group + "." + locs + ".z");
                    Location loc2 = new Location(Bukkit.getWorld(world), x, y, z);
                    if (loc2.equals(loc))
                        return true;
                }
            }
            return false;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean toggleGroup(String name) {
        if (getGroupStatus(name)) {
            toggle_lamp(false,name);
            return false;
        }
        toggle_lamp(true,name);
        return true;
    }

    public void fadeLamp(String name,int level,int fade){
        for(Location location : getLampLocations(name)){
            Block block = location.getBlock();
            Levelled lightLevel = (Levelled)block.getBlockData();
            new BukkitRunnable() {
                int block_level = lightLevel.getLevel();
                @Override
                public void run() {
                    this.block_level = lightLevel.getLevel();
                    if (this.block_level > level) {
                        this.block_level--;
                        lightLevel.setLevel(this.block_level);
                    } else if (this.block_level < level) {
                        this.block_level++;
                        lightLevel.setLevel(this.block_level);
                    } else {
                        cancel();
                    }
                    block.setBlockData(lightLevel, true);
                }
            }.runTaskTimer(Main.getInstance(),0L,fade);
        }
    }

    public void toggle_lamp(boolean value,String name){
        int level = 0;
        if(value){
            level = 15;
        }else {
            level = 0;
        }
        List<BlockState> states = new ArrayList<>();
        for (Location loc : getLampLocations(name)) {
            Levelled data = (Levelled) loc.getBlock().getBlockData();
            BlockState state = loc.getBlock().getState();
            data.setLevel(level);
            state.setBlockData(data);
            states.add(state);
        }
        states.stream().forEach(state -> state.update());
        FileManager fileManager = new FileManager((JavaPlugin)Main.getInstance());
        fileManager.getConfig("data.yml").getConfig().set("groups." + name + ".enabled", value);
        fileManager.saveConfig("data.yml");
    }
}
