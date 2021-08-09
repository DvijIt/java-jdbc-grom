package jdbs.lesson4.task2.controller;

import jdbs.lesson4.task2.exceptions.InvalidInputException;
import jdbs.lesson4.task2.model.File;
import jdbs.lesson4.task2.model.Storage;
import jdbs.lesson4.task2.service.Service;

import java.sql.SQLException;
import java.util.List;

public class FileController {
    private static final Service FILE_SERVICE = new Service();

    public File put(Storage storage, File file) throws Exception {
        return FILE_SERVICE.put(storage, file);
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        return FILE_SERVICE.putAll(storage, files);
    }

    public void delete(Storage storage, File file) throws InvalidInputException, SQLException {
        FILE_SERVICE.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        FILE_SERVICE.transferAll(storageFrom, storageTo);
    }

    public File transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        return FILE_SERVICE.transferFile(storageFrom, storageTo, id);
    }
}
