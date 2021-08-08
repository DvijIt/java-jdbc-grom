package lesson4.task2.service;

import lesson4.task2.DAO.FileDAO;
import lesson4.task2.exceptions.InvalidInputException;
import lesson4.task2.model.File;
import lesson4.task2.model.Storage;

import java.sql.SQLException;
import java.util.List;

public class Service {
    FileDAO FILE_DAO = new FileDAO();

    public File put(Storage storage, File file) throws Exception {
        FileServiceValidator.validatePut(storage, file);

        file.setStorage(storage);
        return FILE_DAO.save(file);
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        if (files.isEmpty()) {
            return files;
        }

        FileServiceValidator.validatePutAll(storage, files);

        for (File file : files) {
            file.setStorage(storage);
        }
        return FILE_DAO.save(files);
    }

    public void delete(Storage storage, File file) throws InvalidInputException, SQLException {
        if (file == null || storage == null) {
            throw new InvalidInputException("Input can't be null");
        }

        FileServiceValidator.validateDelete(storage, file);
        FILE_DAO.delete(file.getId());
    }

    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        File file = FILE_DAO.findById(id);
        FileServiceValidator.validateTransferFile(storageFrom, storageTo, file);

        file.setStorage(storageTo);
        return FILE_DAO.update(file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        FileServiceValidator.validateTransferAll(storageFrom, storageTo);
        FILE_DAO.updateStorageForFiles(storageFrom, storageTo);
    }


}
