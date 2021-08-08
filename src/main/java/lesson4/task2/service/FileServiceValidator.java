package lesson4.task2.service;

import lesson4.task2.DAO.StorageDAO;
import lesson4.task2.exceptions.IncompatibleFormatsException;
import lesson4.task2.exceptions.InvalidInputException;
import lesson4.task2.exceptions.NoSpaceException;
import lesson4.task2.model.File;
import lesson4.task2.model.Storage;

import java.sql.SQLException;
import java.util.List;

class FileServiceValidator {
    private final static StorageDAO STORAGE_DAO = new StorageDAO();

    private FileServiceValidator() {
    }

    static void validatePut(Storage storage, File file) throws InvalidInputException, IncompatibleFormatsException, NoSpaceException {
        if (file == null || storage == null) {
            throw new InvalidInputException("Input can't be null or empty");
        }

        checkStorageFields(storage);
        checkStorageExistence(storage);

        checkFileFields(file);
        if (file.getStorage() != null) {
            throw new InvalidInputException("File (id=" + file.getId() + " can't have more than one storage");
        }
        checkFileFormatCompatibility(storage.getFormatsSupported().split(", "), file.getFormat());

        checkStorageFreeSpaceAmount(storage, file.getSize());
    }

    static void validateDelete(Storage storage, File file) throws InvalidInputException {
        checkFileFields(file);

        checkStorageFields(storage);
        checkStorageForFile(storage, file);
    }

    static void validatePutAll(Storage storage, List<File> files) throws InvalidInputException,
            IncompatibleFormatsException, NoSpaceException {
        if (files == null || storage == null) {
            throw new InvalidInputException("Input can't be null");
        }

        checkStorageFields(storage);
        checkStorageExistence(storage);

        long filesSizeCounter = 0;
        String[] storageFormats = storage.getFormatsSupported().split(", ");
        for (File file : files) {
            checkFileFields(file);
            if (file.getStorage() != null) {
                throw new InvalidInputException("File (id=" + file.getId() + " can't have more than one storage");
            }
            checkFileFormatCompatibility(storageFormats, file.getFormat());
            checkStorageFreeSpaceAmount(storage, filesSizeCounter + file.getSize());
        }
    }

    static void validateTransferAll(Storage storageFrom, Storage storageTo) throws InvalidInputException,
            NoSpaceException, IncompatibleFormatsException {
        if (storageFrom == null || storageTo == null) {
            throw new InvalidInputException("Input can't be null");
        }

        checkStorageFields(storageFrom);
        checkStorageFields(storageTo);
        checkStorageExistence(storageTo);

        checkStorageFreeSpaceAmount(storageTo, storageFrom.getStorageMaxSize());

        String[] formatsFrom = storageFrom.getFormatsSupported().split(", ");
        for (String formatTo : storageTo.getFormatsSupported().split(", ")) {
            checkFileFormatCompatibility(formatsFrom, formatTo);
        }
    }

    static void validateTransferFile(Storage storageFrom, Storage storageTo, File file) throws InvalidInputException,
            IncompatibleFormatsException, NoSpaceException {
        if (storageFrom == null || storageTo == null) {
            throw new InvalidInputException("Input can't be null");
        }

        if (file == null) {
            throw new InvalidInputException("Requested file is absent");
        }
        checkFileFormatCompatibility(storageTo.getFormatsSupported().split(", "), file.getFormat());

        checkStorageFields(storageFrom);
        checkStorageExistence(storageFrom);

        checkStorageFields(storageTo);
        checkStorageExistence(storageTo);

        checkStorageForFile(storageFrom, file);
        checkStorageFreeSpaceAmount(storageTo, file.getSize());
    }

    private static void checkFileFormatCompatibility(String[] formats, String fileFormat)
            throws IncompatibleFormatsException {
        for (String format : formats) {
            if (format.equals(fileFormat)) {
                return;
            }
        }

        throw new IncompatibleFormatsException("Invalid format");
    }

    private static void checkFileFields(File file) throws InvalidInputException {
        if (file.getId() < 0) {
            throw new InvalidInputException("Id must be equals 0 or greater");
        }

        if (file.getName() == null || file.getName().isEmpty()) {
            throw new InvalidInputException("File's (" + file.getId() + ") name can't be null or empty");
        }

        if (file.getFormat() == null || file.getFormat().isEmpty()) {
            throw new InvalidInputException("File's (" + file.getId() + ") format can't be null or empty");
        }

        if (file.getSize() < 0) {
            throw new InvalidInputException("File size must be equals 0 or greater");
        }
    }

    private static void checkStorageFields(Storage storage) throws InvalidInputException {
        if (storage.getId() < 0) {
            throw new InvalidInputException("Id must be equals 0 or greater");
        }

        if (storage.getStorageMaxSize() <= 0) {
            throw new InvalidInputException("Size must be equals 0 or greater");
        }

        if (storage.getStorageCountry() == null || storage.getStorageCountry().isEmpty()) {
            throw new InvalidInputException("Storage's country can't be null or empty");
        }
    }

    private static void checkStorageExistence(Storage storage) throws InvalidInputException {
        try {
            if (STORAGE_DAO.findById(storage.getId()) == null) {
                throw new InvalidInputException("Storage (id=" + storage.getId() + ") is absent at database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkStorageFreeSpaceAmount(Storage storage, long size) throws NoSpaceException {
        if (size > storage.getStorageMaxSize()) {
            throw new NoSpaceException("Storage (id=" + storage.getId() + ") has no space available");
        }
    }

    private static void checkStorageForFile(Storage storage, File file) throws InvalidInputException {
        if (file.getStorage().getId() != storage.getId()) {
            throw new InvalidInputException("Invalid file's(id=" + file.getId() +
                    ") storage(id=" + storage.getId() + ")");
        }
    }
}

