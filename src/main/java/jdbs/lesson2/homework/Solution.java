package jdbs.lesson2.homework;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    private static final String INCREASE_PRICE = "UPDATE PRODUCTS SET PRICE = PRICE + ? WHERE PRICE < ?";
    private static final String UPDATE_DESCRIPTION = "UPDATE PRODUCTS SET DESCRIPTION = ? WHERE ID = ?";
    private static final String SELECT_PRODUCTS_BY_DESCRIPTION = "SELECT * FROM PRODUCTS WHERE LENGTH(DESCRIPTION) > 10";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM PRODUCTS";

    public static void main(String[] args) {
//        increasePrice();
        changeDescription();

    }

    public static void increasePrice() {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INCREASE_PRICE)) {
            preparedStatement.setInt(1, 100);
            preparedStatement.setInt(2, 970);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeDescription() {
        for (Product product : getProduct(SELECT_PRODUCTS_BY_DESCRIPTION)) {
            Product productForUpdate = deleteSentence(product);
            executeUpdate(UPDATE_DESCRIPTION, productForUpdate);
        }
    }

    public static Product deleteSentence(Product product) {
        String description = product.getDescription();
        ArrayList<String> sentences = new ArrayList<String>(Arrays.asList(description.split("\\.")));
        sentences.remove(sentences.get(sentences.size() - 1));
        product.setDescription(String.join(".", sentences));
        return product;
    }

    public static void executeUpdate(String sql, Product product) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getDescription());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Product> getProduct(String sql) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            return mapProducts(statement.executeQuery(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static List<Product> mapProducts(ResultSet resultSet) throws SQLException {
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

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
