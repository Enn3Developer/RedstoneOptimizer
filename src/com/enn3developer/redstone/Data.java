package com.enn3developer.redstone;

import java.util.HashMap;
import java.util.Map;

/** Class that handles all data
 * 
 * @author enn3
 * @since 0.1
 */
public final class Data {
	/** Represent a HashMap of sub-command and correlate help message
	 * 
	 */
	public Map<String, String> commandHelp = new HashMap<>();
	
	/** Initialize all data
	 * 
	 */
	public Data() {
		commandHelp.put("help", (String) MainRedstoneOptimizer.config.read("messages.command_help.help"));
	}
}
