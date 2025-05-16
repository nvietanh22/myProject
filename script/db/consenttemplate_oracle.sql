CREATE TABLE consenttemplate (
    id NUMBER PRIMARY KEY,
    tempName VARCHAR2(255),
    content VARCHAR2(255),
    level1 VARCHAR2(255),
    level2 VARCHAR2(255),
    channel VARCHAR2(255),
    createdDate CLOB,
    createdBy VARCHAR2(255),
    modifyDate DATE,
    status VARCHAR2(255)
);
