SET AUTO OFF;
DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM user_tables WHERE table_name = upper('ERS_REIMBURSMENT_STATUS');
   IF c = 1 then
      EXECUTE IMMEDIATE 'DROP TABLE ERS_REIMBURSMENT_STATUS CASCADE CONSTRAINTS';
   END IF;
END;
/

CREATE TABLE ERS_REIMBURSMENT_STATUS (
  reimb_status_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  reimb_status VARCHAR2(10)
);

CREATE OR REPLACE PROCEDURE create_ers_reimbursment_status(
  rei_id OUT NUMBER, 
  rei_status IN VARCHAR2
)
IS
BEGIN
  SELECT MIN(reimb_status_id) INTO rei_id FROM ERS_REIMBURSMENT_STATUS WHERE ERS_REIMBURSMENT_STATUS.reimb_status = rei_status; 
  IF rei_id > 0
  THEN
   DBMS_OUTPUT.PUT_LINE(rei_id);
  ELSE
  INSERT INTO ERS_REIMBURSMENT_STATUS(reimb_status) 
    VALUES (rei_status)
    RETURNING reimb_status_id 
      INTO rei_id;
  END IF;
  COMMIT;
END;
/

SET AUTO OFF;
DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM user_tables WHERE table_name = upper('ERS_REIMBURSEMENT_TYPE');
   IF c = 1 then
      EXECUTE IMMEDIATE 'DROP TABLE ERS_REIMBURSEMENT_TYPE CASCADE CONSTRAINTS';
   END IF;
END;
/

CREATE TABLE ERS_REIMBURSEMENT_TYPE (
  reimb_type_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  reimb_type VARCHAR2(10)
);

CREATE OR REPLACE PROCEDURE create_ers_reimbursment_type(
  rei_id OUT NUMBER, 
  rei_type IN VARCHAR2
)
IS
BEGIN
  SELECT MIN(reimb_type_id) INTO rei_id FROM ERS_REIMBURSEMENT_TYPE WHERE ERS_REIMBURSEMENT_TYPE.reimb_type = rei_type; 
  IF rei_id > 0
  THEN
   DBMS_OUTPUT.PUT_LINE(rei_type);
  ELSE
  INSERT INTO ERS_REIMBURSEMENT_TYPE(reimb_type) 
    VALUES (rei_type)
    RETURNING reimb_type_id 
      INTO rei_id;
  END IF;
  COMMIT;
END;
/

SET AUTO OFF;
DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM user_tables WHERE table_name = upper('ERS_USER_ROLES');
   IF c = 1 then
      EXECUTE IMMEDIATE 'DROP TABLE ERS_USER_ROLES CASCADE CONSTRAINTS';
   END IF;
END;
/

CREATE TABLE ERS_USER_ROLES (
  ers_user_role_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_role VARCHAR2(10)
);

CREATE OR REPLACE PROCEDURE create_ers_user_role(
  usr_id OUT NUMBER, 
  usr_role IN VARCHAR2
)
IS
BEGIN
  SELECT MIN(ers_user_role_id) INTO usr_id FROM ERS_USER_ROLES WHERE ERS_USER_ROLES.user_role = usr_role; 
  IF usr_id > 0
  THEN
   DBMS_OUTPUT.PUT_LINE(usr_id);
  ELSE
  INSERT INTO ERS_USER_ROLES(user_role) 
    VALUES (usr_role)
    RETURNING ers_user_role_id 
      INTO usr_id;
  END IF;
  COMMIT;
END;
/

SET AUTO OFF;
DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM user_tables WHERE table_name = upper('ERS_USERS');
   IF c = 1 then
      EXECUTE IMMEDIATE 'DROP TABLE ERS_USERS CASCADE CONSTRAINTS';
   END IF;
END;
/

CREATE TABLE ERS_USERS (
  ers_users_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  ers_username VARCHAR2(50) UNIQUE NOT NULL,
  ers_password VARCHAR2(300) NOT NULL,
  user_first_name VARCHAR2(100),
  user_last_name VARCHAR2(100),
  user_email VARCHAR2(150) UNIQUE NOT NULL,
  user_role_id NUMBER
);

CREATE OR REPLACE PROCEDURE create_ers_user(
  usr_id OUT NUMBER, 
  usr_name IN VARCHAR2,
  usr_pass IN VARCHAR2,
  usr_email IN VARCHAR2
)
IS
BEGIN
  INSERT INTO ERS_USERS(ers_username, ers_password, user_email) 
    VALUES (usr_name, usr_pass, usr_email)
    RETURNING ers_users_id 
      INTO usr_id;
  COMMIT;
END;
/

SET AUTO OFF;
DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM user_tables WHERE table_name = upper('ERS_REIMBURSEMENT');
   IF c = 1 then
      EXECUTE IMMEDIATE 'DROP TABLE ERS_REIMBURSEMENT CASCADE CONSTRAINTS';
   END IF;
END;
/

CREATE TABLE ERS_REIMBURSEMENT (
  reimb_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  reimb_amount NUMBER NOT NULL,
  reimb_submitted TIMESTAMP NOT NULL,
  reimb_resolved TIMESTAMP,
  reimb_description VARCHAR2(250),
  reimb_receipt BLOB,
  reimb_author NUMBER NOT NULL,
  reimb_resolver NUMBER,
  reimb_status_id NUMBER NOT NULL,
  reimb_type_id NUMBER
);

CREATE OR REPLACE PROCEDURE create_ers_reimbursement(
  usr_id OUT NUMBER, 
  rei_am IN NUMBER,
  rei_description IN VARCHAR2,
  rei_aut IN NUMBER,
  rei_status IN NUMBER,
  rei_type IN NUMBER
)
IS
BEGIN
  INSERT INTO ERS_REIMBURSEMENT(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) 
    VALUES (rei_am, CURRENT_TIMESTAMP, rei_description, rei_aut, rei_status, rei_type)
    RETURNING reimb_id 
      INTO usr_id;
  COMMIT;
END;
/

ALTER TABLE ERS_USERS 
  ADD CONSTRAINT ers_usr_role
    FOREIGN KEY (user_role_id)
    REFERENCES ERS_USER_ROLES (ers_user_role_id)
    ON DELETE SET NULL
;
ALTER TABLE ERS_REIMBURSEMENT 
  ADD CONSTRAINT ers_rei_status_id
    FOREIGN KEY (reimb_status_id)
    REFERENCES ERS_REIMBURSMENT_STATUS (reimb_status_id)
    ON DELETE SET NULL
;
ALTER TABLE ERS_REIMBURSEMENT 
  ADD CONSTRAINT ers_rei_type_id
    FOREIGN KEY (reimb_type_id)
    REFERENCES ERS_REIMBURSEMENT_TYPE (reimb_type_id)
    ON DELETE SET NULL
;
ALTER TABLE ERS_REIMBURSEMENT 
  ADD CONSTRAINT ers_rei_author
    FOREIGN KEY (reimb_author)
    REFERENCES ERS_USERS (ers_users_id)
    ON DELETE SET NULL
;
ALTER TABLE ERS_REIMBURSEMENT 
  ADD CONSTRAINT ers_rei_resolver
    FOREIGN KEY (reimb_resolver)
    REFERENCES ERS_USERS (ers_users_id)
    ON DELETE SET NULL
;

DECLARE
  c int;
BEGIN
  create_reimbursment_status(c,'Pending');
  create_reimbursment_status(c,'Approved');
  create_reimbursment_status(c,'Denied');
  create_reimbursment_type(c,'Equipment');
  create_reimbursment_type(c,'Food');
  create_reimbursment_type(c,'Lodging');
  create_reimbursment_type(c,'Other');
  create_reimbursment_type(c,'Training');
  create_reimbursment_type(c,'Travel');
  create_ers_user_role(c,'Employee');
  create_ers_user_role(c,'Admin');
  COMMIT;
END;
/


DECLARE
   c int;
BEGIN
   SELECT count(*) INTO c FROM dba_users WHERE username  = upper('exp_connection');
   IF c = 1 
   THEN
    EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT CREATE PROCEDURE TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON create_ers_reimbursment_status TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON create_ers_reimbursment_type TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON create_ers_user_role TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON create_ers_user TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON create_ers_reimbursement TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT INSERT, SELECT, UPDATE, DELETE ON admin.ers_reimbursment_status TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT INSERT, SELECT, UPDATE, DELETE ON admin.ers_reimbursement_type TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT INSERT, SELECT, UPDATE, DELETE ON admin.ers_user_roles TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT INSERT, SELECT, UPDATE, DELETE ON admin.ers_users TO exp_connection';
    EXECUTE IMMEDIATE 'GRANT INSERT, SELECT, UPDATE, DELETE ON admin.Ers_Reimbursement TO exp_connection';
   END IF;
END;
/
--SELECT reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id
--FROM ADMIN.ERS_REIMBURSEMENT  
--    OFFSET 5 ROWS FETCH NEXT 10 ROWS ONLY;
UPDATE ADMIN.ERS_REIMBURSEMENT SET reimb_resolved = CURRENT_TIMESTAMP, reimb_description = 'Death Rays are not expensable', reimb_resolver = 5, reimb_status_id = 2, reimb_type_id = 3 WHERE reimb_id = 165;