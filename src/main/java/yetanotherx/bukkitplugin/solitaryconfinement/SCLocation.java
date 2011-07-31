package yetanotherx.bukkitplugin.solitaryconfinement;

import java.io.Serializable;

/**
 * Ugly hack to allow serialization of locations
 */
public class SCLocation implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public SCLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getYaw() {
        return yaw;
    }

    public double getZ() {
        return z;
    }

    public String getWorld() {
        return world;
    }
    
}
