package com.enn3developer.redstone;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	private File configFile;
	private FileConfiguration config;
	
	public ConfigManager(String name, JavaPlugin plugin) {
		configFile = new File(plugin.getDataFolder(), name);
		if (! configFile.exists()) {
			configFile.getParentFile().mkdirs();
			plugin.saveResource(name, false);
		}
		config = new YamlConfiguration();
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(Object what, String where) {
		config.set(where, what);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object read(String where) {
		return config.get(where);
	}
}
