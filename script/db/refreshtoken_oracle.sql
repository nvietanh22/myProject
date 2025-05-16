CREATE TABLE refreshtoken (
    id NUMBER PRIMARY KEY,
    refreshToken VARCHAR2(255),
    userID VARCHAR2(255),
    createdDate DATE,
    endDate DATE
);
