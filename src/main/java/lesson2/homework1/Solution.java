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

    private static final String INSERT_INTO_PRODUCT = "INSERT INTO PRODUCT VALUES(999, 'toy', 'for children', 60)";
    private static final String DELETE_FROM_PRODUCT_WHERE_NAME_TOY = "DELETE FROM PRODUCT WHERE NAME!='toy'";
    private static final String DELETE_FROM_PRODUCT_BY_PRICE = "DELETE FROM PRODUCT WHERE PRICE!=100";

    public static void main(String[] args) {
//        System.out.println(getAllProducts());
//        System.out.println(getProductsByPrice());
//        System.out.println(getProductsByDescription());
//        saveProduct();
//        deleteProduct();
//        deleteProductsByPrice();
    }

    static void saveProduct() {
        executeQuery(INSERT_INTO_PRODUCT);
    }

    static void deleteProduct() {
        executeQuery(DELETE_FROM_PRODUCT_WHERE_NAME_TOY);
    }

    static void deleteProductsByPrice() {
        executeQuery(DELETE_FROM_PRODUCT_BY_PRICE);
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
            return mapProduct(statement.executeQuery(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
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
