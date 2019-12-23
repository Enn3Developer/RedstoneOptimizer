package com.enn3developer.redstone;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/** Manager of a YAML file where you can read and write in it
 * 
 * @author enn3
 * @since 0.1
 */
public class ConfigManager {
	private File configFile;
	private FileConfiguration config;
	
	/** Constructor of the class
	 * 
	 * @param name Name of the file e.g. config.yml
	 * @param plugin The JavaPlugin creating the file
	*/
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
	
	/** Write in the file, then save it
	 * 
	 * @param what What to save
	 * @param where Where to save e.g. messages.hello
	 */
	public void write(Object what, String where) {
		config.set(where, what);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Read from the file
	 * 
	 * @param where Where to read e.g. messages.hello
	 * @return An Object that can be converted to what it was in when written e.g. (String) config.read("messages.hello");
	 */
	public Object read(String where) {
		return config.get(where);
	}
}
