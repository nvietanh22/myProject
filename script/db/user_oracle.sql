CREATE TABLE user (
    id NUMBER PRIMARY KEY,
    username CLOB,
    password VARCHAR2(255),
    fullname VARCHAR2(255),
    phoneNumber VARCHAR2(255),
    identityNumber VARCHAR2(255),
    email VARCHAR2(255),
    status VARCHAR2(255),
    createBy VARCHAR2(255),
    createdDate DATE,
    modifyBy VARCHAR2(255)
);
