package com.enn3developer.redstone;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RedOptCommands extends AbstractCommand{
	JavaPlugin pl;
	String interchange;
	
	public RedOptCommands(JavaPlugin pl) {
		super("redopt", "redopt.use", false);
		this.pl = pl;
		interchange = (String) MainRedstoneOptimizer.config.read("messages.command_help.interchange");
	}
	
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
	
	private void sendHelp(CommandSender sender) {
		MainRedstoneOptimizer.data.commandHelp.forEach((key, value) -> sender.sendMessage(String.format("/%s %s %s", key, interchange, value)));
	}
	private void sendHelpForCommand(CommandSender sender, String commandName) {
		sender.sendMessage(String.format("/%s %s %s", commandName, interchange, MainRedstoneOptimizer.data.commandHelp.get(commandName.toLowerCase())));
	}
	private void sendWrongCommand(CommandSender sender) {
		sender.sendMessage((String) MainRedstoneOptimizer.config.read("messages.wrong_command"));
	}
	
	private void setRedstone(Player player, String name) {
		Location loc = player.getLocation();
		double x = loc.getX(); double y = loc.getY(); double z = loc.getZ();
		World world = loc.getWorld();
		MainRedstoneOptimizer.config.write(x, String.format("redstone.%s.x", name));
		MainRedstoneOptimizer.config.write(y, String.format("redstone.%s.y", name));
		MainRedstoneOptimizer.config.write(z, String.format("redstone.%s.z", name));
		MainRedstoneOptimizer.config.write(world, String.format("redstone.%s.world", name));
	}
	
	private void activateRedstone(CommandSender sender, String name) {
		Location loc = new Location((World) MainRedstoneOptimizer.config.read(String.format("redstone.%s.world", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.x", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.y", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.z", name)));
		loc.getBlock().setType(Material.REDSTONE_BLOCK);
	}
	private void deactivateRedstone(CommandSender sender, String name) {
		Location loc = new Location((World) MainRedstoneOptimizer.config.read(String.format("redstone.%s.world", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.x", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.y", name)), (Double) MainRedstoneOptimizer.config.read(String.format("redstone.%s.z", name)));
		loc.getBlock().setType(Material.AIR);
	}
	
	private void commandsOneLen(CommandSender sender, String arg) {
		if (arg.equalsIgnoreCase("help") && sender.hasPermission("redopt.help"))
			sendHelp(sender);
		else if (arg.equalsIgnoreCase("set") && sender.hasPermission("redopt.set"))
			sendHelpForCommand(sender, arg);
		else
			sendWrongCommand(sender);
	}
	
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
	
	private void commandsThreeLen(CommandSender sender, String[] args) {
		if (args[0].equalsIgnoreCase("set") && sender.hasPermission("redopt.set")) {
			if (args[1].equalsIgnoreCase("redstone"))
				setRedstone((Player) sender, args[3]);
		}
		else
			sendWrongCommand(sender);
	}
}
