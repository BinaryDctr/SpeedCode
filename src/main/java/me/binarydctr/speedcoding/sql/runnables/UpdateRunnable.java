package me.binarydctr.speedcoding.sql.runnables;

import me.binarydctr.speedcoding.sql.Callback;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRunnable extends BukkitRunnable {

    private final DataSource dataSource;
    private final String statement;
    private final Callback<Integer, SQLException> callback;

    public UpdateRunnable(DataSource dataSource, String statement, Callback<Integer, SQLException> callback) {
        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }

    @Override
    public void run() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(statement);

            if(callback != null) {
                callback.call(preparedStatement.executeUpdate(), null);
            }
        } catch (SQLException e) {
            if(callback != null) {
                callback.call(null, e);
            }
        } finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
