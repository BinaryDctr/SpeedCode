package me.binarydctr.speedcoding.warn.sql;

import me.binarydctr.speedcoding.sql.Database;
import me.binarydctr.speedcoding.sql.Pool;
import me.binarydctr.speedcoding.utils.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarnDatabase extends Database {

    private static String CREATE = "CREATE TABLE IF NOT EXISTS `warn` (warner VARCHAR(16), warned VARCHAR(16), inform VARCHAR(8), PRIMARY KEY (warner))";

    public WarnDatabase() {
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
            Logger.log(Logger.LogType.ERROR, "WARN DATABASE WASN'T CREATED.");
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
