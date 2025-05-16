CREATE TABLE permission (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(255),
    action VARCHAR2(255),
    createAt CLOB,
    createBy VARCHAR2(255)
);
