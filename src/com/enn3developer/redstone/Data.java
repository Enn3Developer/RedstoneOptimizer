package com.enn3developer.redstone;

import java.util.HashMap;
import java.util.Map;

public final class Data {
	public String help;
	public Map<String, String> commandHelp = new HashMap<>();
	
	public Data() {
		help = "Help";
		commandHelp.put("help", (String) MainRedstoneOptimizer.config.read("messages.command_help.help"));
	}
}
