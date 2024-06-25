package org.curryman.cleverfb;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CFBCommand implements CommandExecutor {

    private final CleverFB plugin;

    public CFBCommand(CleverFB plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("cfb.reload")) {
                player.sendMessage(plugin.translateColors(plugin.getConfig().getString("commands-list")));
            } else {
                player.sendMessage(plugin.translateColors(plugin.getConfig().getString("no-permission-message")));
            }
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("cfb.reload")) {
                plugin.reloadPluginConfig();
                player.sendMessage(plugin.translateColors(plugin.getConfig().getString("reload-success-message")));
            } else {
                player.sendMessage(plugin.translateColors(plugin.getConfig().getString("no-permission-message")));
            }
            return true;
        }

        return false;
    }
}