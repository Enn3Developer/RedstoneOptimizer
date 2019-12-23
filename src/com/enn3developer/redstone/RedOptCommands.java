package com.enn3developer.redstone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/** Class that handles /redopt command
 * 
 * @author enn3
 * @since 0.1
 */
public class RedOptCommands extends AbstractCommand{
	JavaPlugin pl;
	String interchange;
	
	/** Useful only when creating the array of commands to register
	 * 
	 */
	public RedOptCommands() {}
	
	/** Initialize method overridden by AbstractCommand
	 * 
	 * @param pl The JavaPlugin registered this command. Can be useful sometimes
	 */
	@Override
	public void init(JavaPlugin pl) {
		super._init("redopt", "redopt.use", false);
		this.pl = pl;
		interchange = (String) MainRedstoneOptimizer.config.read("messages.command_help.interchange");
	}
	
	/** Execute the command
	 * 
	 * @param sender The sender of the command
	 * @param args Array of arguments which are /command arg0 arg1 arg2 etc...
	 */
	@Override
	public void execute(CommandSender sender, String[] args) {
		switch (args.length) {
		case 1:
			commandsOneLen(sender, args[0]);
			break;
		case 2:
			commandsTwoLen(sender, args);
			break;
		case 3:
			commandsThreeLen(sender, args);
		default:
			sendWrongCommand(sender);
			break;
		}
	}
	
	/** Send help message to the sender
	 * 
	 * @param sender The sender of the command
	 */
	private void sendHelp(CommandSender sender) {
		MainRedstoneOptimizer.data.commandHelp.forEach((key, value) -> sender.sendMessage(String.format("/%s %s %s", key, interchange, value)));
	}
	/** Send help message for a single command
	 * 
	 * @param sender The sender of the command
	 * @param commandName The sub-command e.g. set
	 */
	private void sendHelpForCommand(CommandSender sender, String commandName) {
		sender.sendMessage(String.format("/%s %s %s", commandName, interchange, MainRedstoneOptimizer.data.commandHelp.get(commandName.toLowerCase())));
	}
	/** Send to the sender the message he wrote something wrong in the command excluded redopt
	 * 
	 * @param sender The sender of the command
	 */
	private void sendWrongCommand(CommandSender sender) {
		sender.sendMessage((String) MainRedstoneOptimizer.config.read("messages.wrong_command"));
	}
	
	/** Save in a file where to spawn redstone
	 * 
	 * @param player Player who want to set a redstone there
	 * @param name The name of the place where the redstone should spawn/despawn
	 */
	private void setRedstone(Player player, String name) {
		Location loc = player.getLocation();
		double x = loc.getX(); double y = loc.getY(); double z = loc.getZ();
		World world = loc.getWorld();
		MainRedstoneOptimizer.config.write(x, String.format("redstone.%s.x", name));
		MainRedstoneOptimizer.config.write(y, String.format("redstone.%s.y", name));
		MainRedstoneOptimizer.config.write(z, String.format("redstone.%s.z", name));
		MainRedstoneOptimizer.config.write(world.getName(), String.format("redstone.%s.world", name));
	}
	
	/** Spawn redstone at place configured before
	 * 
	 * @param sender The sender of the command
	 * @param name The name of the place where the redstone should spawn/despawn
	 */
	private void activateRedstone(CommandSender sender, String name) {
		Location loc = new Location(Bukkit.getWorld((String) MainRedstoneOptimizer.config.read(String.format("redstone.%s.world", name))), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.x", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.y", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.z", name)));
		loc.getBlock().setType(Material.REDSTONE_BLOCK);
	}
	/** Despawn redstone at place configured before
	 * 
	 * @param sender The sender of the command
	 * @param name The name of the place where the redstone should spawn/despawn
	 */
	private void deactivateRedstone(CommandSender sender, String name) {
		Location loc = new Location(Bukkit.getWorld((String) MainRedstoneOptimizer.config.read(String.format("redstone.%s.world", name))), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.x", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.y", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.z", name)));
		loc.getBlock().setType(Material.AIR);
	}
	
	/** Listener for commands with one argument
	 * 
	 * @param sender The sender of the command
	 * @param arg The arg0 in /command arg0
	 */
	private void commandsOneLen(CommandSender sender, String arg) {
		if (arg.equalsIgnoreCase("help") && sender.hasPermission("redopt.help"))
			sendHelp(sender);
		else if (arg.equalsIgnoreCase("set") && sender.hasPermission("redopt.set"))
			sendHelpForCommand(sender, arg);
		else
			sendWrongCommand(sender);
	}
	
	/** Listener for commands with two arguments
	 * 
	 * @param sender The sender of the command
	 * @param args Array of arguments which are /command arg0 arg1 arg2 etc...
	 */
	private void commandsTwoLen(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("help") && sender.hasPermission("redopt.help") && MainRedstoneOptimizer.data.commandHelp.containsKey(args[1].toLowerCase()))
			sendHelpForCommand(sender, args[1]);
		else if (args[0].equalsIgnoreCase("set") && sender.hasPermission("redopt.set"))
			sendHelpForCommand(sender, args[0]);
		else if (args[0].equalsIgnoreCase("activate") && sender.hasPermission("redopt.activate"))
			activateRedstone(sender, args[1]);
		else if (args[0].equalsIgnoreCase("deactivate") && sender.hasPermission("redopt.deactivate"))
			deactivateRedstone(sender, args[1]);
		else
			sendWrongCommand(sender);
	}
	
	/** Listener for commands with three arguments
	 * 
	 * @param sender The sender of the command
	 * @param args Array of arguments which are /command arg0 arg1 arg2 etc...
	 */
	private void commandsThreeLen(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("set") && sender.hasPermission("redopt.set")) {
			if (args[1].equalsIgnoreCase("redstone"))
				setRedstone((Player) sender, args[2]);
		}
		else
			sendWrongCommand(sender);
	}
}
