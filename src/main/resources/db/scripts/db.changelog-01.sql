--liquibase formatted sql

--changeset andre:1

DROP TABLE IF EXISTS "USER";

CREATE TABLE "USER"(
    USER_ID SERIAL PRIMARY KEY,
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR,
    EMAIL VARCHAR,
    AGE INT,
    BANNED BOOLEAN
);

--changeset andre:2
DROP TABLE IF EXISTS "SERIE";

CREATE TABLE "SERIE"(
   SERIE_ID SERIAL PRIMARY KEY NOT NULL,
   SERIE_NAME VARCHAR(2000) NOT NULL,
   SERIE_SYNOPSE VARCHAR,
   SERIE_AIRING_DATE DATE,
   SERIE_STATUS VARCHAR
);

ALTER TABLE "SERIE" ADD CONSTRAINT CHECK_SERIE_STATUS_TYPES
    CHECK (SERIE_STATUS = 'AIRING' OR SERIE_STATUS = 'FINISHED' OR SERIE_STATUS = 'NOT_YET_AIRED');

--changeset andre:3
ALTER TABLE "USER" ALTER COLUMN USERNAME SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN PASSWORD SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN EMAIL SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN AGE SET NOT NULL;
ALTER TABLE "USER" ADD CONSTRAINT unique_email unique(email);
ALTER TABLE "USER" ALTER COLUMN BANNED SET DEFAULT FALSE;

--changeset andre:4
DROP TABLE IF EXISTS "CATEGORY";
CREATE TABLE "CATEGORY"(
    CATEGORY_ID SERIAL PRIMARY KEY NOT NULL,
    CATEGORY_NAME VARCHAR
);

--changeset andre:5
CREATE TABLE "SERIE_CATEGORY"(
    SERIE_CATEGORY_ID SERIAL PRIMARY KEY NOT NULL,
    SERIE_ID INT,
    CATEGORY_ID INT,
    CONSTRAINT fk_serie
        FOREIGN KEY(SERIE_ID)
            REFERENCES "USER"(USER_ID),
    CONSTRAINT fk_category
        FOREIGN KEY(CATEGORY_ID)
            REFERENCES "CATEGORY"(CATEGORY_ID)
);

--changeset andre:6
ALTER TABLE "SERIE_CATEGORY" ALTER COLUMN SERIE_ID SET NOT NULL;
ALTER TABLE "SERIE_CATEGORY" ALTER COLUMN CATEGORY_ID SET NOT NULL;

--changeset andre:7
DROP TABLE IF EXISTS "USER_SERIE_RELATIONSHIP";

CREATE TABLE "USER_SERIE_RELATIONSHIP"(
    USER_SERIE_RELATIONSHIP_ID SERIAL PRIMARY KEY NOT NULL,
    USER_ID INT NOT NULL ,
    SERIE_ID INT NOT NULL ,
    SCORE INT,
    EPISODES_COMPLETED INT,
    STATUS VARCHAR NOT NULL,
    CONSTRAINT fk_user_relation
        FOREIGN KEY(USER_ID)
        REFERENCES "USER"(USER_ID),
    CONSTRAINT fk_serie_relation
        FOREIGN KEY(SERIE_ID)
            REFERENCES "SERIE"(SERIE_ID),
    CONSTRAINT check_status
        CHECK ( STATUS = 'PLAN_TO_WATCH' OR STATUS = 'WATCHING' OR STATUS = 'COMPLETED' OR STATUS = 'DROPPED' OR STATUS = 'ON_HOLD')
);

--changeset andre:8
ALTER TABLE "USER" ADD COLUMN CREATED_AT DATE;
ALTER TABLE "USER" ADD COLUMN UPDATED_AT DATE;

--changeset andre:9
ALTER TABLE "USER_SERIE_RELATIONSHIP" DROP CONSTRAINT fk_user_relation;
ALTER TABLE "SERIE_CATEGORY" DROP CONSTRAINT fk_serie;
DROP TABLE "USER";
ALTER TABLE "SERIE_CATEGORY" ADD CONSTRAINT fk_serie FOREIGN KEY (SERIE_ID) REFERENCES "SERIE"(SERIE_ID);
ALTER TABLE "USER_SERIE_RELATIONSHIP" ALTER COLUMN USER_ID type varchar(36);
ALTER TABLE "USER_SERIE_RELATIONSHIP" ADD CONSTRAINT fk_user_relation FOREIGN KEY (USER_ID) REFERENCES "user_entity"(id);


--changeset andre:10
ALTER TABLE "SERIE" ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "USER_SERIE_RELATIONSHIP" ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "SERIE_CATEGORY" ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE "CATEGORY" ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;


