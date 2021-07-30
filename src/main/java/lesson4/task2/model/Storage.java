package lesson4.task2.model;

import java.util.Arrays;

public class Storage {
    private Long id;
    private String[] formatsSupported;
    private String storageCountry;
    private Long storageMaxSize;

    public Storage(Long id, String[] formatsSupported, String storageCountry, Long storageMaxSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public void setFormatsSupported(String[] formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public Long getStorageMaxSize() {
        return storageMaxSize;
    }

    public void setStorageMaxSize(Long storageMaxSize) {
        this.storageMaxSize = storageMaxSize;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageMaxSize=" + storageMaxSize +
                '}';
    }
}
