package me.binarydctr.speedcoding.sql;

import lombok.Getter;
import me.binarydctr.speedcoding.utils.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
public abstract class Database {

    private DataSource source;

    private Connection connection;

    public Database(DataSource source) {
        this.source = source;
        try {
            this.connection = source.getConnection();
        } catch (SQLException e) {
            Logger.log(Logger.LogType.ERROR, "SQLException in Database.java class.");
        }

    }

    public abstract void onStart();
}
