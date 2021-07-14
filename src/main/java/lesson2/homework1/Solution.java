package lesson2.homework1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM PRODUCTS";
    private static final String SELECT_ALL_PRODUCTS_BY_PRICE = "SELECT * FROM PRODUCTS WHERE PRICE <= 100";
    private static final String SELECT_ALL_PRODUCTS_BY_DESCRIPTION = "SELECT * FROM PRODUCTS WHERE LENGTH(DESCRIPTION) > 50";

    public static void main(String[] args) {
        System.out.println(getAllProducts());
        System.out.println(getProductsByPrice());
        System.out.println(getProductsByDescription());
    }

    public static List<Product> getAllProducts() {
        return getProducts(SELECT_ALL_PRODUCTS);
    }

    public static List<Product> getProductsByPrice() {
        return getProducts(SELECT_ALL_PRODUCTS_BY_PRICE);
    }

    public static List<Product> getProductsByDescription() {
        return getProducts(SELECT_ALL_PRODUCTS_BY_DESCRIPTION);
    }

    public static List<Product> getProducts(String sql) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            return mapProduct(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static List<Product> mapProduct(ResultSet resultSet) throws SQLException {
        try {
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to map products");
        }
    }
}
