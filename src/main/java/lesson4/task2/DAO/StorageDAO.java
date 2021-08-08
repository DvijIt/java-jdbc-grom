package lesson4.task2.DAO;

import lesson4.task2.model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageDAO extends DAO<Storage> {
    private static final String INSERT_STORAGE = "INSERT INTO STORAGES VALUES (?, ?, ?, ?)";
    private static final String DELETE_STORAGE_BY_ID = "DELETE FROM STORAGES WHERE ID=?";
    private static final String UPDATE_STORAGE = "UPDATE STORAGES SET FORMATS_SUPPORTED=?, STORAGE_COUNTRY=?, STORAGE_MAX_FILES-SIZE=? WHERE ID=?";
    private static final String UPDATE_FILE_STORAGE = "UPDATE FILES SET STORAGE_ID=? WHERE STORAGE_ID=?";
    private static final String SELECT_STORAGE_BY_ID = "SELECT * FROM STORAGES WHERE ID=?";

    @Override
    public Storage save(Storage storage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STORAGE)) {

            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, storage.getFormatsSupported());
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageMaxSize());

            int res = preparedStatement.executeUpdate();

            System.out.println("Storage save was finished with res " + res);
        } catch (SQLException e) {
            throw new SQLException("Unable to save storage(id=" + storage.getId() + ")");
        }

        return storage;
    }

    public Storage update(Storage storage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STORAGE)) {

            preparedStatement.setString(2, storage.getFormatsSupported());
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageMaxSize());
            preparedStatement.setLong(4, storage.getId());

            int res = preparedStatement.executeUpdate();

            System.out.println("Storage update was finished with res " + res);
        } catch (SQLException e) {
            throw new SQLException("Unable to update storage(id=" + storage.getId() + ")");
        }

        return storage;
    }

    public void delete(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORAGE_BY_ID)) {

            preparedStatement.setLong(1, id);

            int res = preparedStatement.executeUpdate();

            System.out.println("Storage delete was finished with res " + res);
        } catch (SQLException e) {
            throw new SQLException("Unable to update storage(id=" + id + ")");
        }
    }

    public Storage findById(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STORAGE_BY_ID)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Storage(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4));
            }
            System.out.println("Select was finished");
        } catch (SQLException e) {
            throw new SQLException("Unable to select storage(id=" + id + ")");
        }

        return null;
    }
}
