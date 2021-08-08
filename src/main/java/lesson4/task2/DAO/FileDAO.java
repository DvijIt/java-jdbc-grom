package lesson4.task2.DAO;

import lesson4.task2.model.File;
import lesson4.task2.model.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FileDAO extends DAO<File> {
    private static final String INSERT_FILE = "INSERT INTO FILES VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_FILE_BY_ID = "DELETE FROM FILES WHERE ID=?";
    private static final String UPDATE_FILE = "UPDATE FILES SET NAME=?, FILE_FORMAT=?, FILE_SIZE=?, STORAGE_ID=? WHERE ID=?";
    private static final String UPDATE_FILE_STORAGE = "UPDATE FILES SET STORAGE_ID=? WHERE STORAGE_ID=?";
    private static final String SELECT_FILE_BY_ID = "SELECT * FROM FILES WHERE ID=?";

    private static final StorageDAO STORAGE_DAO = new StorageDAO();

    @Override
    public File save(File file) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILE)) {

            preparedStatement.setLong(1, file.getId());
            prepareStatementSave(preparedStatement, file);
        } catch (SQLException e) {
            throw new SQLException("Unable to save file(id=" + file.getId() + ")");
        }

        return file;
    }

    public List<File> save(List<File> files) {
        try (Connection connection = getConnection()) {
            saveList(files, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return files;
    }

    private void saveList(List<File> files, Connection connection) throws SQLException {
        long fileId = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILE)) {
            connection.setAutoCommit(false);

            for (File file : files) {
                fileId = file.getId();

                preparedStatement.setLong(1, fileId);
                prepareStatementSave(preparedStatement, file);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Unable to save file(id=" + fileId + ")");
        }
    }

    private void prepareStatementSave(PreparedStatement preparedStatement, File file) throws SQLException {
        preparedStatement.setString(2, file.getName());
        preparedStatement.setString(3, file.getFormat());
        preparedStatement.setLong(4, file.getSize());
        preparedStatement.setLong(5, file.getStorage().getId());

        int res = preparedStatement.executeUpdate();
        System.out.println("save was finished with res " + res);
    }

    @Override
    public void delete(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FILE_BY_ID)) {

            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("delete was finished with res " + res);
        } catch (SQLException e) {
            throw new SQLException("Unable to delete file(id=" + id + ")");
        }
    }

    @Override
    public File update(File file) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE)) {

            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getFormat());
            preparedStatement.setLong(3, file.getSize());
            preparedStatement.setLong(4, file.getStorage().getId());
            preparedStatement.setLong(5, file.getId());

            int res = preparedStatement.executeUpdate();
            System.out.println("update was finished with res " + res);

        } catch (SQLException e) {
            throw new SQLException("Unable to update file(id=" + file.getId() + ")");
        }

        return file;
    }

    @Override
    public File findById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILE_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new File(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        STORAGE_DAO.findById(resultSet.getLong(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateStorageForFiles(Storage oldStorage, Storage newStorage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE_STORAGE)) {

            preparedStatement.setLong(1, newStorage.getId());
            preparedStatement.setLong(2, oldStorage.getId());

            int res = preparedStatement.executeUpdate();
            System.out.println("updateStorageForFiles was finished with res " + res);
        } catch (SQLException e) {
            throw new SQLException("Unable to update storage for files from storage(id=" + oldStorage.getId() + ")");
        }

    }
}
