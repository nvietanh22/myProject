CREATE TABLE token (
    id NUMBER PRIMARY KEY,
    token VARCHAR2(255),
    refreshToken VARCHAR2(255),
    userID VARCHAR2(255),
    Status VARCHAR2(255),
    createdDate DATE,
    endDate DATE
);
