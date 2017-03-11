package me.binarydctr.speedcoding.move.sql;

import me.binarydctr.speedcoding.sql.Database;
import me.binarydctr.speedcoding.sql.Pool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoveDatabase extends Database {

    private static String CREATE = "CREATE TABLE IF NOT EXISTS `travel` (uuid VARCHAR(36), name VARCHAR(18), traveled INT, PRIMARY KEY (uuid))";

    public MoveDatabase() {
        super(Pool.TEST);
    }

    @Override
    public void onStart() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
