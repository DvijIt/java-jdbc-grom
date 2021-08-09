package jdbs.lesson4.task1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDemo {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    public static void main(String[] args) {
        Product product1 = new Product(122, "!!!", "!!!!", 777);
        Product product2 = new Product(33, "!!!", "!!!!", 777);
        Product product3 = new Product(44, "!!!", "!!!!", 777);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        save(products);
    }

    public static void save(List<Product> products) {
        try (Connection connection = getConnection()) {
            saveList(products, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void saveList(List<Product> products, Connection connection) throws SQLException {
        long productId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS VALUES (?, ?, ?, ?)")) {
            connection.setAutoCommit(false);

            for (Product product : products) {
                productId = product.getId();
                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDescription());
                preparedStatement.setInt(4, product.getPrice());

                int res = preparedStatement.executeUpdate();

                System.out.println("save was finished with result " + res);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Unable to save product item (id=" + productId + ")");
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
