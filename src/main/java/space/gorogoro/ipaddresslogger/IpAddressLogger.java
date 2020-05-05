package space.gorogoro.ipaddresslogger;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class IpAddressLogger extends JavaPlugin implements Listener{

  @Override
  public void onEnable(){
    try{
      Bukkit.getLogger().info("The Plugin Has Been Enabled!");
      getServer().getPluginManager().registerEvents(this, this);

      // If there is no setting file, it is created
      if(!getDataFolder().exists()){
        getDataFolder().mkdir();
      }
      File configFile = new File(getDataFolder() + "/config.yml");
      if(!configFile.exists()){
        saveDefaultConfig();
      }

    } catch (Exception e) {
      Bukkit.getLogger().warning(e.getMessage());
    }
  }

  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent event){
    try {
      String ipAddress = event.getPlayer().getAddress().getAddress().toString().replace("/", "");
      String UUID = event.getPlayer().getUniqueId().toString();
      String playerName = event.getPlayer().getName();

      FileConfiguration config = getConfig();
      List<String> list = config.getStringList("list");
      String line = String.format("%s %s %s", ipAddress, UUID, playerName);
      if(list.contains(line) == false) {
        list.add(line);
      }
      Collections.sort(list);
      config.set("list", list);
      saveConfig();

    } catch (Exception e) {
      Bukkit.getLogger().warning(e.getMessage());
    }
  }

  @Override
  public void onDisable(){
    Bukkit.getLogger().info("The Plugin Has Been Disabled!");
  }
}
