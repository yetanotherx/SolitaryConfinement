package yetanotherx.bukkitplugin.solitaryconfinement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

public class SCListener {

    private SCPlugin parent;
    private PlayerListener player = new SCListener.PlayerL();
    private BlockListener block = new SCListener.BlockL();
    private EntityListener entity = new SCListener.EntityL();

    public SCListener(SCPlugin parent) {
        this.parent = parent;
    }

    public BlockListener getBlock() {
        return block;
    }

    public EntityListener getEntity() {
        return entity;
    }

    public PlayerListener getPlayer() {
        return player;
    }

    public class PlayerL extends PlayerListener {

        @Override
        public void onPlayerChat(PlayerChatEvent event) {
            if (!parent.jailedPlayers.isEmpty()) {
                event.setCancelled(true);

                if (parent.jailedPlayers.containsKey(event.getPlayer().getName())) {
                    SCPlugin.log.info("Jailed player - " + event.getPlayer().getName() + " - " + event.getMessage() );
                            
                    for (Player player : parent.getServer().getOnlinePlayers()) {
                        if (SCPermissions.has(player, "sc.mod")) {
                            player.sendMessage("[" + ChatColor.RED + "Jailed - " + event.getPlayer().getName() + "] " + ChatColor.BLUE + event.getMessage());
                        }
                    }
                } else {
                    for (Player player : parent.getServer().getOnlinePlayers()) {
                        if (!parent.jailedPlayers.containsKey(player.getName())) {
                            player.sendMessage(event.getMessage());
                        }
                    }
                }
            }
        }

        @Override
        public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
            if (!parent.jailedPlayers.isEmpty()) {
                if (parent.jailedPlayers.containsKey(event.getPlayer().getName())) {
                    SCPlugin.log.info("Jailed player - " + event.getPlayer().getName() + " - " + event.getMessage() );
                    event.setCancelled(true);
                }
            }
        }
    }

    public class BlockL extends BlockListener {

        @Override
        public void onBlockBreak(BlockBreakEvent event) {
            if (!parent.jailedPlayers.isEmpty()) {
                if (parent.jailedPlayers.containsKey(event.getPlayer().getName())) {
                    event.setCancelled(true);
                }
            }
        }

        @Override
        public void onBlockPlace(BlockPlaceEvent event) {
            if (!parent.jailedPlayers.isEmpty()) {
                if (parent.jailedPlayers.containsKey(event.getPlayer().getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public class EntityL extends EntityListener {

        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            if (!parent.jailedPlayers.isEmpty()) {

                if (event.getEntity() instanceof Player) {
                    if (parent.jailedPlayers.containsKey(((Player) event.getEntity()).getName())) {
                        event.setCancelled(true);
                    }
                }

                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent) event;
                    if (entEvent.getDamager() instanceof Player) {
                        if (parent.jailedPlayers.containsKey(((Player) entEvent.getDamager()).getName())) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
