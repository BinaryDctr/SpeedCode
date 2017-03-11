package me.binarydctr.speedcoding.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Logger {

    @Getter
    public enum LogType {

        INFO("Info", ChatColor.GOLD),
        ERROR("Error", ChatColor.DARK_RED);

        private String string;
        private ChatColor chatColor;

        LogType(String string, ChatColor chatColor) {
            this.string = string;
            this.chatColor = chatColor;
        }
    }

    public static void log(LogType logType, String message) {
        switch (logType) {
            case INFO:
                Bukkit.getConsoleSender().sendMessage("{" + logType.getString() + "} " + message);
                break;
            case ERROR:
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(player.isOp() || player.hasPermission("log.listen")) {
                        player.sendMessage("{" + logType.getString() + "} " + message);
                    }
                }
                Bukkit.getConsoleSender().sendMessage("{" + logType.getString() + "} " + message);
                break;
        }
    }

}
