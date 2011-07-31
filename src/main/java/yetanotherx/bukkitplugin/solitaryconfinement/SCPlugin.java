package yetanotherx.bukkitplugin.solitaryconfinement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import yetanotherx.bukkitplugin.solitaryconfinement.command.*;

/*
 * SolitaryConfinement Version 1.0 - A better jail plugin
 * Copyright (C) 2011 Yetanotherx <yetanotherx -a--t- gmail -dot- com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Unless otherwise specified, this license applies to all the files in 
 * the 'yetanothrx.bukkitplugin.solitaryconfinement' package, and all its subpackages.
 */
public class SCPlugin extends JavaPlugin {

    public static final SCLogger log = new SCLogger();
    private SCListener listener;
    public HashMap<String, String> jailedPlayers = new HashMap<String, String>();
    public HashMap<String, Location> originalLocations = new HashMap<String, Location>();
    public HashMap<String, SCLocation> jails = new HashMap<String, SCLocation>();

    @Override
    public void onDisable() {
        log.info("Plugin disabled. (version " + this.getDescription().getVersion() + ")");
    }

    @Override
    public void onEnable() {

        try {
            SCPermissions.load(this);
            this.listener = new SCListener(this);

            this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, this.listener.getPlayer(), Event.Priority.Highest, this);
            this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, this.listener.getPlayer(), Event.Priority.Highest, this);
            this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, this.listener.getBlock(), Event.Priority.High, this);
            this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_PLACE, this.listener.getBlock(), Event.Priority.High, this);
            this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, this.listener.getEntity(), Event.Priority.High, this);

            SCCommand jCommand = new SCCommand(this);
            jCommand.registerExecutor("listjails", new ListJailsCommand(this));
            jCommand.registerExecutor("setjail", new SetJailCommand(this));
            jCommand.registerExecutor("deljail", new DelJailCommand(this));
            jCommand.registerExecutor("jail", new JailCommand(this));
            jCommand.registerExecutor("unjail", new UnjailCommand(this));
            jCommand.registerExecutor("version", new VersionCommand(this));
            jCommand.registerExecutor("tell", new TellCommand(this));
            jCommand.registerExecutor("help", new HelpCommand(this));
            getCommand("j").setExecutor(jCommand);

            File jailsFile = new File(this.getDataFolder() + File.separator + "jails.dat");
            if (!jailsFile.exists()) {
                this.getDataFolder().mkdirs();
                jailsFile.createNewFile();
                this.saveJails();
            }
            this.loadJails();

            log.info("Plugin enabled! (version " + this.getDescription().getVersion() + ")");

        } catch (Exception e) {
            log.severe("Caught an exception, disabling plugin! " + e.getMessage());
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public void saveJails() {
        try {
            FileOutputStream fout = new FileOutputStream(this.getDataFolder() + File.separator + "jails.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this.jails);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadJails() {
        try {
            FileInputStream fin = new FileInputStream(this.getDataFolder() + File.separator + "jails.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            this.jails = (HashMap<String, SCLocation>) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
