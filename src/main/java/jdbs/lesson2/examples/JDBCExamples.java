package jdbs.lesson2.examples;

import java.sql.*;

public class JDBCExamples {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    private static final String INSERT_INTO_PRODUCT = "INSERT INTO PRODUCTS VALUES(2, 'Car', 'Car for me', 250)";

    public static void main(String[] args) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int response = stmt.executeUpdate(INSERT_INTO_PRODUCT);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
