package me.binarydctr.speedcoding;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.binarydctr.speedcoding.move.PlayerMoveBlockManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpeedCoding extends JavaPlugin {

    @Getter public static SpeedCoding instance;

    @Getter public HikariDataSource hikariDataSource;

    @Override
    public void onEnable() {
        instance = this;
        hikariDataSource = new HikariDataSource();
        new PlayerMoveBlockManager();

    }

    @Override
    public void onDisable() {
        if(hikariDataSource != null) {
            hikariDataSource.close();
        }
    }

}
