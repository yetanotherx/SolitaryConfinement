package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;

public class HelpCommand implements CommandExecutor {

    private SCPlugin parent;

    public HelpCommand(SCPlugin parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.AQUA + parent.getDescription().getName() + " version " + parent.getDescription().getVersion() + " help:");
        sender.sendMessage(ChatColor.AQUA + "/j jail [player] [jailname] - Sends a player to a jail");
        sender.sendMessage(ChatColor.AQUA + "/j unjail [player] - Frees a player from jail");
        sender.sendMessage(ChatColor.AQUA + "/j setjail [jailname] - Sets a jail at your location");
        sender.sendMessage(ChatColor.AQUA + "/j deljail [jailname] - Deletes a jail");
        sender.sendMessage(ChatColor.AQUA + "/j listjails - Lists all the jails");
        sender.sendMessage(ChatColor.AQUA + "/j tell [player] [message] - Sends a message to a jailed player");
        return true;
    }
    
}
