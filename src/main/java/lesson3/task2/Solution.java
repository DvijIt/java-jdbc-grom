package lesson3.task2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String USER = "root";
    private static final String PASSWORD = "root12345678";

    private static final String dbServer = "oracle-38327-0.cloudclusters.net"; // change it to your database server name
    private static final int dbPort = 38327; // change it to your database server port
    private static final String DB_URL = String.format("jdbc:oracle:thin:@%s:%d:xe", dbServer, dbPort);

    private static final String SELECT_BY_PRICE = "SELECT * FROM PRODUCTS WHERE PRICE BETWEEN ? AND ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM PRODUCTS WHERE NAME LIKE ?";
    private static final String SELECT_WITH_EMPTY_DESCRIPTION = "SELECT * FROM PRODUCTS WHERE DESCRIPTION IS NULL";

    public List<Product> findProductsByPrice(int price, int delta) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PRICE)) {

            preparedStatement.setInt(1, price - delta);
            preparedStatement.setInt(2, price + delta);

            ResultSet resultSet = preparedStatement.executeQuery();

            return mapProducts(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> findProductsByName(String word) throws BadRequestException {
        validateWord(word);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME)) {

            preparedStatement.setString(1, "%" + word + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            return mapProducts(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> findProductsWithEmptyDescription() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_WITH_EMPTY_DESCRIPTION);

            return mapProducts(resultSet);
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

    private void validateWord(String word) throws BadRequestException {
        if (word.length() < 3) {
            throw new BadRequestException("Input (" + word + ") length should be equals or greater than 3.");
        }

        if (word.split("(\\w+)+").length > 1) {
            throw new BadRequestException("Input (" + word + ") should be only a single word");
        }

        for (char wordChar : word.toCharArray()) {
            if (!Character.isLetterOrDigit(wordChar)) {
                throw new BadRequestException("Input (" + word + ") mustn't contain any special characters");
            }
        }
    }

    private static List<Product> mapProducts(ResultSet resultSet) {
        try {
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

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
