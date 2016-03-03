package com.rident.farmprotect;
 
import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class FarmProtect extends JavaPlugin implements Listener {
	public static FarmProtect plugin;
	public Logger log;
	
	SettingsManager settings = SettingsManager.getInstance();
  
	public void onEnable() {
		settings.setup(this);
	    setLogger();
		createConfig();
		PluginManager pm = getServer().getPluginManager();
    	pm.registerEvents(this, this);
    	
    	PluginDescriptionFile pdfFile = this.getDescription();
    	getLogger().info(pdfFile.getName() + " has been enabled!");
	}
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info(pdfFile.getName() + " has been disabled.");
	}
	
	private void createConfig()
	{
	  File file = new File(getDataFolder() + File.separator + "config.yml");
	  if (!file.exists())
	  {
		this.log.info("Could not find config.yml, Generating one now...");
		saveDefaultConfig();
		this.log.info("Config generated!");
	  }
	}
	
	  private void setLogger()
	  {
	    this.log = getLogger();
	  }
	
		@EventHandler
		public void onPlayerInteract(PlayerInteractEvent event) {
			if ((event.getAction() == org.bukkit.event.block.Action.PHYSICAL) && (event.getClickedBlock().getType() == Material.SOIL && (settings.getConfig().getBoolean("FarmProtect") == true))) {
				event.setCancelled(true);
			}
		}
		
		@EventHandler
		public void onEntityInteract(EntityInteractEvent event) {
			if ((event.getEntityType() != org.bukkit.entity.EntityType.PLAYER) && (event.getBlock().getType() == Material.SOIL && (settings.getConfig().getBoolean("FarmProtect") == true))) {
				event.setCancelled(true);
			}
		}

	}