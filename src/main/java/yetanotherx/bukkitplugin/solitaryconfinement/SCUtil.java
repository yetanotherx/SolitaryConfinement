package yetanotherx.bukkitplugin.solitaryconfinement;

import java.lang.reflect.Array;
import org.bukkit.Location;
import org.bukkit.Server;

public class SCUtil {
    
    public static String implode(String[] array, String glue) {

        String out = "";

        if (array.length == 0) {
            return out;
        }

        for ( Object part : array) {
            out = out + part + glue;
        }
        out = out.substring(0, out.length() - glue.length());

        return out;
    }
    
    public static String[] removeFirstTwoArgs(String[] args) {
        if( args.length < 2 ) {
            return args;
        }
        String[] args2 = args.clone();
        return remove(remove(args2, 0), 0);
    }
    
    /**
     * From apache commons, Apache Software License v2
     */
    private static String[] remove(String[] array, int index) {
        int length = array.length;
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        String[] result = (String[]) Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }
    
    public static SCLocation LocationToSCLocation(Location loc) {
        return new SCLocation(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
    
    public static Location SCLocationToLocation(SCLocation loc, Server server) {
        return new Location(server.getWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
    
}
