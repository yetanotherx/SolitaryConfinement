package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPermissions;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;
import yetanotherx.bukkitplugin.solitaryconfinement.SCUtil;

public class ListJailsCommand implements CommandExecutor {

    private SCPlugin parent;

    public ListJailsCommand(SCPlugin parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (SCPermissions.has((Player) sender, "sc.mod")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        
        parent.loadJails();
        
        sender.sendMessage(ChatColor.AQUA + "Jails: " + ChatColor.YELLOW + SCUtil.implode(parent.jails.keySet().toArray(new String[]{}), ", "));
        
        return true;
    }
    
}
