package lesson2.homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Solution {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    private static final String INCREASE_PRICE = "UPDATE PRODUCTS SET PRICE = PRICE + 100 WHERE PRICE < 970";

    public static void main(String[] args) {
        increasePrice();
    }

    public static void increasePrice() {
        executeQuery(INCREASE_PRICE);
    }

    public static void changeDescription() {
        
    }

    static void executeQuery(String sql) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            int response = statement.executeUpdate(sql);

            if (response == 0) {
                System.out.println("(" + sql + ") didn't executed");
            } else {
                System.out.println("(" + sql + ") succeeded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
