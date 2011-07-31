package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPermissions;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;
import yetanotherx.bukkitplugin.solitaryconfinement.SCUtil;

public class TellCommand implements CommandExecutor {

    private SCPlugin parent;

    public TellCommand(SCPlugin parent) {
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
        String message = SCUtil.implode(SCUtil.removeFirstTwoArgs(args), " ");
        
        if( !parent.jailedPlayers.containsKey(player) ) {
            sender.sendMessage(ChatColor.RED + player + " is not in jail.");
            return true;
        }
        if( playerIns == null ) {
            sender.sendMessage(ChatColor.RED + player + " is not online.");
            return true;
        }
        
        playerIns.sendMessage("<" + ChatColor.BLUE + ChatColor.stripColor(((Player) sender).getName()) + ChatColor.WHITE + "> " + message );
        
        return true;
    }
    
}
