CREATE TABLE STORAGE
(
    ID               NUMBER NOT NULL ENABLE,
    CONSTRAINT STORAGE_PK PRIMARY KEY (ID),
    FormatsSupported CLOB DEFAULT 'txt, jpg, jpeg, png',
    StorageCountry   NVARCHAR2(20) NOT NULL,
    StorageMaxSize   NUMBER NOT NULL
);