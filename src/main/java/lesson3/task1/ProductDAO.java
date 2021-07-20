package lesson3.task1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //CRUD
    //create, read, update, delete

    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    public Product save(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCTS VALUES (?, ?, ?, ?)")) {

            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());

            int res = preparedStatement.executeUpdate();

            System.out.println("save was finished with result " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Product update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( "UPDATE PRODUCTS SET NAME=?, DESCRIPTION=?, PRICE=? WHERE ID=?")) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setLong(4, product.getId());

            int res = preparedStatement.executeUpdate();

            System.out.println("update was finished with result " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> getProducts() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS");

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( "DELETE PRODUCTS WHERE ID=?")) {

            preparedStatement.setLong(1, id);

            int res = preparedStatement.executeUpdate();

            System.out.println("delete was finished with result " + res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
