package io.github.p250.commands;

import io.github.p250.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WolfFarmingReload implements CommandExecutor {

    private Main plugin;

    public WolfFarmingReload(Main instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission(plugin.getConfig().getString("command.reload.permission")))) {
            sender.sendMessage(ChatColor.RED + "Permission denied.");
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();

            String message = plugin.getConfig().getString("command.reload.success");
            sender.sendMessage(cc(message));

            return true;
        } else {
            String usage = plugin.getConfig().getString("command.reload.usage");
            sender.sendMessage(cc(usage));

            return true;
        }
    }

    private String cc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
