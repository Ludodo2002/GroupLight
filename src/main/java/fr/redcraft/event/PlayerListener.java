package fr.redcraft.event;

import fr.redcraft.Main;
import fr.redcraft.data.FileManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener {

    @EventHandler
    public void onInteract(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(Main.getGroupLight().lightCreators.containsKey(player)){
            if(event.getHand().equals(EquipmentSlot.HAND)){
                if(event.getBlockPlaced().getType().equals(Material.LIGHT)){
                    Location location = event.getBlockPlaced().getLocation();
                    if(!Main.getGroupLight().lampExists(Main.getGroupLight().lightCreators.get(player),location)){
                        Main.getGroupLight().addLocation((String)Main.getGroupLight().lightCreators.get(event.getPlayer()), location);
                        player.sendMessage("§cLamp add !");
                    }else {
                        player.sendMessage("§cLamp aldeary exist");
                        return;
                    }
                }
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.LIGHT)) {
            if(Main.getGroupLight().lightCreators.containsKey(event.getPlayer())){
                for (String group : Main.getGroupLight().getGroups()) {
                    for (Location loc : Main.getGroupLight().getLampLocations(group)) {
                        if (loc.equals(event.getBlock().getLocation())) {
                            FileManager fileManager = new FileManager((JavaPlugin) Main.getInstance());
                            fileManager.getConfig("data.yml").getConfig().set("groups." + group + "." + Main.getGroupLight().getIDFromLocation(group, event.getBlock().getLocation()), null);
                            fileManager.saveConfig("data.yml");
                            event.getPlayer().sendMessage("§7GroupLight removed");
                            if (Main.getGroupLight().getLampLocations(group).size() == 0) {
                                Main.getGroupLight().removeGroup(group);
                                event.getPlayer().sendMessage("§cGroup removed");
                                event.getPlayer().performCommand("grouplight stop");
                            }
                            return;
                        }
                    }
                }
            }
        }
    }
}
