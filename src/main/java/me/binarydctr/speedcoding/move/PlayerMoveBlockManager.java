package me.binarydctr.speedcoding.move;

import me.binarydctr.speedcoding.SpeedCoding;
import me.binarydctr.speedcoding.move.sql.MoveDatabase;
import me.binarydctr.speedcoding.sql.Callback;
import me.binarydctr.speedcoding.sql.SQLCalls;
import me.binarydctr.speedcoding.utils.Logger;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.SQLException;
import java.util.HashMap;

public class PlayerMoveBlockManager implements Listener {

    /*
    *
    *
    * KEEPS TRACK OF ALL THE BLOCKS THE PLAYER HAS MOVES SINCE HE JOINED THE SERVER/NETWORK.
    * DOES ALL THE UPDATING ASYNC. THERE IS A MUCH CLEANER WAY OF DOING THIS BUT IM SHOWING YOU
    * HOW TO DO IT WITH MY DATABASE SYSTEM, I MAY UPDATE IT LATER.
    *
    *
     */

    private HashMap<String, Location> lastLocation = new HashMap<>();

    public PlayerMoveBlockManager() {
        SpeedCoding.getInstance().getServer().getPluginManager().registerEvents(this, SpeedCoding.getInstance());
        new MoveDatabase();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        SQLCalls.addPlayerToTravelDatabase(event.getPlayer(), (result, thrown) -> {
            Logger.log(Logger.LogType.INFO, "Successfully added " + event.getPlayer().getName() + " to travel database.");
        });
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(!lastLocation.containsKey(event.getPlayer().getName())) {
            lastLocation.put(event.getPlayer().getName(), event.getPlayer().getLocation());
            return;
        }
        Location lastLoc = lastLocation.get(event.getPlayer().getName());
        Location loc = event.getPlayer().getLocation();
        if(loc.getBlockX() != lastLoc.getBlockX() || loc.getBlockY() != lastLoc.getBlockY() || loc.getBlockZ() != lastLoc.getBlockZ()) {
            SQLCalls.getBlocksTraveled(event.getPlayer().getUniqueId().toString(), (result, thrown) -> {
                if(thrown == null) {
                    try {
                        if(result.next()) {
                            Integer amount = result.getInt("traveled") + 1;
                            SQLCalls.updateBlocksTraveled(amount, event.getPlayer().getUniqueId().toString(), (result1, thrown1) -> {
                                PlayerMoveBlockEvent moveBlockEvent = new PlayerMoveBlockEvent(event.getPlayer(), event.getPlayer().getLocation());
                                SpeedCoding.getInstance().getServer().getPluginManager().callEvent(moveBlockEvent);
                                if(moveBlockEvent.isCancelled()) {
                                    event.getPlayer().teleport(lastLocation.get(event.getPlayer().getName()));
                                    return;
                                }
                            });
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        lastLocation.remove(event.getPlayer().getName());
        lastLocation.put(event.getPlayer().getName(), loc);
    }
}
