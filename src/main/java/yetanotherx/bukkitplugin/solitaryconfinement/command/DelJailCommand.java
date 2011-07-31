package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPermissions;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;

public class DelJailCommand implements CommandExecutor {

    private SCPlugin parent;

    public DelJailCommand(SCPlugin parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (SCPermissions.has((Player) sender, "sc.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        
        if (args.length < 2) {
            return false;
        }

        String jail = args[1];

        if (!parent.jails.containsKey(jail)) {
            sender.sendMessage(ChatColor.RED + "\"" + jail + "\" does not exist.");
            return true;
        }

        parent.jails.remove(jail);
        parent.saveJails();

        sender.sendMessage(ChatColor.GREEN + "Jail removed.");

        return true;
    }
}
