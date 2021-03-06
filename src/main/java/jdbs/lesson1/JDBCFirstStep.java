package jdbs.lesson1;

import java.sql.*;

public class JDBCFirstStep {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    static {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println(DB_URL);
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + JDBC_DRIVER + " not found");
        }
    }

    //1,DB Driver
    //2. create connection
    //3. create query
    //4. execute query
    //5. work with result
    //6. close all the connection

    public static void main(String[] args) {
        try (
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = connection.createStatement()
        ) {
            try (ResultSet resultSet = statement.executeQuery("SELECT name FROM USERS")) {
                while (resultSet.next()) {
                    System.out.println("Object found");
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
