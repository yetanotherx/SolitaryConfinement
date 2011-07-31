package yetanotherx.bukkitplugin.solitaryconfinement.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import yetanotherx.bukkitplugin.solitaryconfinement.SCPlugin;

public class VersionCommand implements CommandExecutor {

    private SCPlugin parent;

    public VersionCommand(SCPlugin parent) {
        this.parent = parent;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("You're running: " + ChatColor.AQUA.toString() + parent.getDescription().getName() + " " + parent.getDescription().getVersion());
        return true;
    }
    
}
