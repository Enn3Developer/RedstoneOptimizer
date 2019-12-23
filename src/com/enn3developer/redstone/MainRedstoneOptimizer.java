package com.enn3developer.redstone;

import org.bukkit.plugin.java.JavaPlugin;

/** Main class of the plugin
 * 
 * @author enn3
 * @since 0.1
 */
public class MainRedstoneOptimizer extends JavaPlugin{
	public static ConfigManager config;
	public static Data data;
	
	/** Used to register everything when the server starts
	 * 
	 */
	@Override
	public void onEnable() {
		AbstractCommand[] commands = new AbstractCommand[1];
		commands[0] = new RedOptCommands();
		config = new ConfigManager("config.yml", this);
		AbstractCommand.registerCommands(this, commands);
		data = new Data();
	}
	
	/** Used when server is stopping or the plugin isn't working
	 * 
	 */
	@Override
	public void onDisable() {}
}
