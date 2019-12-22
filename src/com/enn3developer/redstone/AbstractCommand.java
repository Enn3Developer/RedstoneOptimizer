package com.enn3developer.redstone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractCommand implements CommandExecutor{
	private final String commandName;
	private final String permission;
	private final boolean canConsoleUse;
	private static JavaPlugin plugin;
	
	public AbstractCommand(String commandName, String permission, boolean canConsoleUse) {
		this.commandName = commandName;
		this.permission = permission;
		this.canConsoleUse = canConsoleUse;
		plugin.getCommand(commandName).setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.getLabel().equalsIgnoreCase(commandName))
            return true;
        if(!sender.hasPermission(permission)){
            sender.sendMessage("You don't have permission for this.");
            return true;
        }
        if(!canConsoleUse && !(sender instanceof Player)){
            sender.sendMessage("Only players may use this command sorry!");
            return true;
        }
        execute(sender, args);
		return true;
	}
	
	public final static void registerCommands(JavaPlugin pl) {
		plugin = pl;
		new RedOptCommands(pl);
	}

	protected abstract void execute(CommandSender sender, String[] args);
}
