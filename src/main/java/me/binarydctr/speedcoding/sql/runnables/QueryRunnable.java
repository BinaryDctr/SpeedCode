package me.binarydctr.speedcoding.sql.runnables;

import me.binarydctr.speedcoding.sql.Callback;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryRunnable extends BukkitRunnable {

    private final DataSource dataSource;
    private final String statement;
    private final Callback<ResultSet, SQLException> callback;

    public QueryRunnable(DataSource dataSource, String statement, Callback<ResultSet, SQLException> callback) {
        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }

    @Override
    public void run() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            resultSet = preparedStatement.executeQuery();
            if(callback != null) {
                callback.call(resultSet, null);
            }
        } catch (SQLException e) {
            if(callback != null) {
                callback.call(null, e);
            }
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

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
