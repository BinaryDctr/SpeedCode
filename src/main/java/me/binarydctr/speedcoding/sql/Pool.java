package me.binarydctr.speedcoding.sql;

import com.zaxxer.hikari.HikariDataSource;
import me.binarydctr.speedcoding.SpeedCoding;

import javax.sql.DataSource;

public class Pool {

    public static final DataSource TEST = startPool("jdbe:mysql//localhost:3306/Test", "root", "");

    private static DataSource startPool(String url, String username, String password) {
        HikariDataSource hikariDataSource = SpeedCoding.getInstance().getHikariDataSource();
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.addDataSourceProperty("autoReconnect", "true");

        return hikariDataSource;
    }

}
