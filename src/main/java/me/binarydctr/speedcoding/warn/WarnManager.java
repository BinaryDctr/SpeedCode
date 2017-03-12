package me.binarydctr.speedcoding.warn;

import me.binarydctr.speedcoding.sql.Callback;
import me.binarydctr.speedcoding.sql.SQLCalls;
import me.binarydctr.speedcoding.warn.sql.WarnDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class WarnManager implements Listener {

    /*
    NO COMPLETED THERE IS MAYBE ONE MORE BUG, WHICH IS WHEN INFORM IS UPDATED IT UPDATES ALL OF PLAYER'S WARNINGS.
     */


    public WarnManager() {
        new WarnDatabase();
    }

    public void warn(String warner, String warned, String inform) {
        SQLCalls.addPlayerToWarnDatabase(warner, warned, inform, (result, thrown) -> {
            if (thrown == null) {
                if (result != null) {
                    Bukkit.getPluginManager().callEvent(new WarnEvent(warner, warned, inform));
                }
            }
        });
    }

    @EventHandler
    public void onWarned(WarnEvent event) {
        if (event.getInform().equalsIgnoreCase("t")) {
            if (Bukkit.getPlayer(event.getWarned()) != null) {
                SQLCalls.updateInform(event.getWarned(), (result, thrown) -> {
                    Player warned = Bukkit.getPlayer(event.getWarned());
                    warned.sendMessage(ChatColor.RED + "[WARN] " + " You have received a warning from " + ChatColor.YELLOW + event.getWarner() + ChatColor.RESET + ".");
                });
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SQLCalls.getWarningsInform(player.getName(), (result, thrown) -> {
            if(thrown == null) {
                try {
                    while(result.next()) {
                        player.sendMessage(ChatColor.RED + "[WARN] " + " You have received a warning from " + ChatColor.YELLOW + result.getString("warner") + ChatColor.RESET + ".");
                        SQLCalls.updateInform(player.getName(), (result1, thrown1) -> {});
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
