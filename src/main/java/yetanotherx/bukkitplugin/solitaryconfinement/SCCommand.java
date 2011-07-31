package yetanotherx.bukkitplugin.solitaryconfinement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Taken from BigBrother, under the GPLv3 license
 */
class SCCommand implements CommandExecutor {

    private HashMap<String, CommandExecutor> executors = new HashMap<String, CommandExecutor>();
    private SCPlugin parent;

    public SCCommand(SCPlugin parent) {
        this.parent = parent;
    }

    public void registerExecutor(String subcmd, CommandExecutor cmd) {
        executors.put(subcmd.toLowerCase(), cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();

        args = groupArgs(args);
        if (sender instanceof Player) {
            if (commandName.equals("j")) {
                if (args.length == 0) {
                    return false;
                }

                String subcommandName = args[0].toLowerCase();

                if (!executors.containsKey(subcommandName)) {
                    return false;
                }

                return executors.get(subcommandName).onCommand(sender, command, commandLabel, args);
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (commandName.equals("j")) {
                ConsoleCommandSender console = (ConsoleCommandSender) sender;
                if (args.length == 0) {
                    return false;
                } else if (args[0].equalsIgnoreCase("version")) {
                    console.sendMessage("You're running: " + ChatColor.AQUA.toString() + parent.getDescription().getName() + " " + parent.getDescription().getVersion());
                }
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * A messy parser to group args together if they are surrounded by quotes.
     * <pre>
     * String[]{"rollback","\"A","Griefer\"","r:15"}
     * </pre>
     * Becomes
     * <pre>
     * String[]{"rollback","A Griefer","r:15"}
     * </pre>
     * @param preargs Arguments to group.
     * @return Grouped args.
     */
    public static String[] groupArgs(String[] preargs) {
        List<String> args = new ArrayList<String>();
        String currentArg = "";
        boolean inQuotes = false;
        for (String arg : preargs) {
            if (inQuotes) {
                SCPlugin.log.info(arg);
                currentArg += " " + arg;
                if (arg.endsWith("\"")) {
                    args.add(currentArg.substring(0, currentArg.lastIndexOf("\"")));
                    inQuotes = false;
                }
            } else {
                if (arg.startsWith("\"")) {
                    inQuotes = true;
                    SCPlugin.log.info(arg);
                    currentArg = arg.substring(1, arg.length());
                } else {
                    args.add(arg);
                }
            }
        }
        String[] gargs = new String[args.size()];
        return args.toArray(gargs);
    }
}
