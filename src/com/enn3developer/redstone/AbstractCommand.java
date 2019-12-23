package com.enn3developer.redstone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/** Listen for commands, check permissions and etc, pass it to sub-classes
 * 
 * @author Enn3DevPlayer
 * @since 0.1
*/
public abstract class AbstractCommand implements CommandExecutor{
	private String commandName;
	private String permission;
	private boolean canConsoleUse;
	private static JavaPlugin plugin;
	
	/** Initialize the listener
	 * 
	 * @param commandName The command that will listen
	 * @param permission The permission to check
	 * @param canConsoleUse Check if console can use this command
	*/
	public void _init(String commandName, String permission, boolean canConsoleUse) {
		this.commandName = commandName;
		this.permission = permission;
		this.canConsoleUse = canConsoleUse;
		plugin.getCommand(commandName).setExecutor(this);
	}
	
	/** Listen commands, check everything, send to execute to the right class
	 * 
	 * @param sender The sender of the command
	 * @param cmd The command itself
	 * @param label The first value when you type in chat /command arg0
	 * @param args Array of arguments which are /command arg0 arg1 arg2 etc...
	 * @return A boolean if the command is checked
	*/
	@Override
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
	
	/** Register all commands
	 * 
	 * @param pl The JavaPlugin registering the commands
	 * @param commands Array of all commands to register
	*/
	public final static void registerCommands(JavaPlugin pl, AbstractCommand[] commands) {
		plugin = pl;
		for (AbstractCommand command: commands)
			command.init(pl);
	}

	/** Method to be overridden by sub-classes to be initialized
	 * 
	 * @param pl The JavaPlugin that registered this command
	*/
	protected abstract void init(JavaPlugin pl);
	
	/** Method to be overridden by sub-classes to execute commands
	 * 
	 * @param sender The sender of the command
	 * @param args Array of arguments which are /command arg0 arg1 arg2 etc...
	*/
	protected abstract void execute(CommandSender sender, String[] args);
}
