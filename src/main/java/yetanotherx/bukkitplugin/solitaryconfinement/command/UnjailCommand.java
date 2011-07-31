package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPermissions;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;

public class UnjailCommand implements CommandExecutor {

    private SCPlugin parent;

    public UnjailCommand(SCPlugin parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (SCPermissions.has((Player) sender, "sc.mod")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        
        if( args.length < 2 ) {
            return false;
        }
        
        String player = args[1];
        Player playerIns = parent.getServer().getPlayer(player);
        
        if( !parent.jailedPlayers.containsKey(player) ) {
            sender.sendMessage(ChatColor.RED + player + " is not in jail.");
            return true;
        }
        if( playerIns == null ) {
            sender.sendMessage(ChatColor.RED + player + " is not online.");
            return true;
        }
        
        parent.jailedPlayers.remove(player);
        playerIns.teleport(parent.originalLocations.get(player));
        parent.originalLocations.remove(player);
        
        sender.sendMessage(ChatColor.GREEN + player + " released fom jail.");
        
        return true;
    }
    
}
