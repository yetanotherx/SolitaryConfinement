package yetanotherx.bukkitplugin.solitaryconfinement;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SCPermissions {

    private static PermissionHandler Permissions;
    private static HandlerType handlerType;

    public enum HandlerType {
        PERMISSIONS,
        VANILLA
    }

    /**
     * Check if permissions is installed, and initiate it
     */
    public static void load(JavaPlugin parent) {
        Plugin perm_plugin = parent.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {

            if (perm_plugin != null) {
                Permissions = ((Permissions) perm_plugin).getHandler();
                handlerType = HandlerType.PERMISSIONS;

            } else {
                handlerType = HandlerType.VANILLA;

            }
        }

    }

    public static PermissionHandler getPermissions() {
        return Permissions;
    }

    public static HandlerType getHandlerType() {
        return handlerType;
    }

    public static boolean has(Player player, String permission, boolean restricted) {
        switch (getHandlerType()) {
            case PERMISSIONS:
                return Permissions.has(player, permission);
            default:
                if (restricted) {
                    return player.hasPermission(permission);
                }
                return true;

        }

    }

    public static boolean has(Player player, String permission) {
        return has(player, permission, true);
    }
}
