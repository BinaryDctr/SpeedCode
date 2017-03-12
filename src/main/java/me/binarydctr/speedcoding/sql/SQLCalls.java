package me.binarydctr.speedcoding.sql;

import me.binarydctr.speedcoding.SpeedCoding;
import me.binarydctr.speedcoding.sql.runnables.QueryRunnable;
import me.binarydctr.speedcoding.sql.runnables.UpdateRunnable;
import me.binarydctr.speedcoding.utils.Logger;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLCalls {

    /*
    *
    *
    * VERY SIMPLE DATABASE POOL AND ASYNC DATABASE CALLS.
    * TO USE AND GET INFORMATION FROM THE DATABASE CALLS USE THE
    * CALLBACKS.
    *
    *
     */

    //TO GET PLAYER'S UUID FROM TABLE WITH NAME
    public static void exampleQuery(String name, Callback<ResultSet, SQLException> callback) {
        new QueryRunnable(Pool.TEST, "SELECT uuid FROM `players` WHERE name=`"+name+"`", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    //TO UPDATE PLAYER'S NAME
    public static void exampleUpdate(String name, String uuid, Callback<Integer, SQLException> callback) {
        new UpdateRunnable(Pool.TEST, "UPDATE `players` SET name='"+name+"' WHERE uuid='"+uuid+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public void usage(Player player) {
        exampleQuery(player.getName(), (result, thrown) -> {
            if (thrown == null) {
                try {
                    if(result.next()) {
                        UUID uuid = UUID.fromString(result.getString("uuid"));
                        exampleUpdate("MonkeyMan", uuid.toString(), (result1, thrown1) -> {
                            if(result1 != null) {
                                Logger.log(Logger.LogType.INFO, "Successfully updated player's name in database.");
                            }
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    *
    *
    * TRAVEL SQL CALLS
    *
    *
    *
     */

    public static void addPlayerToTravelDatabase(Player player, Callback<Integer, SQLException> callback) {
        new UpdateRunnable(Pool.TEST, "INSERT INTO `travel` (uuid, name, traveled) VALUES ('"+player.getUniqueId().toString()+"', '"+player.getName()+"', '0') ON DUPLICATE KEY UPDATE name='"+player.getName()+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public static void updateBlocksTraveled(Integer amount, String uuid, Callback<Integer, SQLException> callback) {
        new UpdateRunnable(Pool.TEST, "UPDATE `travel` SET=traveled'"+amount+"' WHERE uuid='"+uuid+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public static void getBlocksTraveled(String uuid, Callback<ResultSet, SQLException> callback) {
        new QueryRunnable(Pool.TEST, "SELECT traveled FROM `travel` WHERE uuid='"+uuid+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    /*
    *
    *
    * WARN SQL CALLS
    *
    *
    *
     */

    public static void addPlayerToWarnDatabase(String warner, String warned, String inform, Callback<Integer, SQLException> callback) {
        new UpdateRunnable(Pool.TEST, "INSERT INTO `warn` (warner, warned, inform) VALUES ('"+warner+"', '"+warned+"', '"+inform+"')", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public static void getWarnings(String warned, Callback<ResultSet, SQLException> callback) {
        new QueryRunnable(Pool.TEST, "SELECT * FROM `warn` WHERE warned='"+warned+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public static void getWarningsInform(String warned, Callback<ResultSet, SQLException> callback) {
        new QueryRunnable(Pool.TEST, "SELECT * FROM `warn` WHERE warned='"+warned+"' AND inform='t'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }

    public static void updateInform(String warned, Callback<Integer, SQLException> callback) {
        new UpdateRunnable(Pool.TEST, "UPDATE `warn` SET=inform'f' WHERE warned='"+warned+"'", callback).runTaskAsynchronously(SpeedCoding.getInstance());
    }
}
