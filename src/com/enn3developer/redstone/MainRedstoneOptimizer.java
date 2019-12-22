package com.enn3developer.redstone;

import org.bukkit.plugin.java.JavaPlugin;

public class MainRedstoneOptimizer extends JavaPlugin{
	public static ConfigManager config;
	public static Data data;
	
	@Override
	public void onEnable() {
		config = new ConfigManager("config.yml", this);
		AbstractCommand.registerCommands(this);
		data = new Data();
	}
	
	@Override
	public void onDisable() {}
}
