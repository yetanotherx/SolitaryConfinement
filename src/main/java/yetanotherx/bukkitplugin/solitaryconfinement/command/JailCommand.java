package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPermissions;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;
import yetanotherx.bukkitplugin.solitaryconfinement.SCUtil;

public class JailCommand implements CommandExecutor {

    private SCPlugin parent;

    public JailCommand(SCPlugin parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (SCPermissions.has((Player) sender, "sc.mod")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        
        if( args.length < 3 ) {
            return false;
        }
        
        String player = args[1];
        Player playerIns = parent.getServer().getPlayer(player);
        String jail = args[2];
        
        if( !parent.jails.containsKey(jail) ) {
            sender.sendMessage(ChatColor.RED + "\"" + jail + "\" is not a valid jail.");
            return true;
        }
        if( parent.jailedPlayers.containsKey(player) ) {
            sender.sendMessage(ChatColor.RED + player + " is already in the " + parent.jailedPlayers.get(player) + " jail.");
            return true;
        }
        if( playerIns == null ) {
            sender.sendMessage(ChatColor.RED + player + " is not online.");
            return true;
        }
        
        parent.jailedPlayers.put(player, jail);
        parent.originalLocations.put(player, playerIns.getLocation());
        playerIns.teleport(SCUtil.SCLocationToLocation(parent.jails.get(jail), parent.getServer()));
        playerIns.sendMessage(ChatColor.BLUE + "You have been sent to jail by the moderators.");
        playerIns.sendMessage(ChatColor.BLUE + "You may not speak to anyone else or use any commands.");
        playerIns.sendMessage(ChatColor.BLUE + "You may not damage others, nor will you take any damage.");
        playerIns.sendMessage(ChatColor.BLUE + "Please hold for more information.");
        
        sender.sendMessage(ChatColor.GREEN + player + " sent to jail!");
        
        return true;
    }
    
}
