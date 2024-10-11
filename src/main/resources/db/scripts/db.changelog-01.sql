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
DROP TABLE IF EXISTS serie;

CREATE TABLE serie(
   SERIE_ID SERIAL PRIMARY KEY NOT NULL,
   SERIE_NAME VARCHAR(2000) NOT NULL,
   SERIE_SYNOPSE VARCHAR,
   SERIE_AIRING_DATE DATE,
   SERIE_STATUS VARCHAR
);

ALTER TABLE serie ADD CONSTRAINT CHECK_SERIE_STATUS_TYPES
    CHECK (SERIE_STATUS = 'AIRING' OR SERIE_STATUS = 'FINISHED' OR SERIE_STATUS = 'NOT_YET_AIRED');

--changeset andre:3
ALTER TABLE "USER" ALTER COLUMN USERNAME SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN PASSWORD SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN EMAIL SET NOT NULL;
ALTER TABLE "USER" ALTER COLUMN AGE SET NOT NULL;
ALTER TABLE "USER" ADD CONSTRAINT unique_email unique(email);
ALTER TABLE "USER" ALTER COLUMN BANNED SET DEFAULT FALSE;

--changeset andre:4
DROP TABLE IF EXISTS category;
CREATE TABLE category(
    CATEGORY_ID SERIAL PRIMARY KEY NOT NULL,
    CATEGORY_NAME VARCHAR
);

--changeset andre:5
CREATE TABLE serie_category(
    SERIE_CATEGORY_ID SERIAL PRIMARY KEY NOT NULL,
    SERIE_ID INT,
    CATEGORY_ID INT,
    CONSTRAINT fk_serie
        FOREIGN KEY(SERIE_ID)
            REFERENCES "USER"(USER_ID),
    CONSTRAINT fk_category
        FOREIGN KEY(CATEGORY_ID)
            REFERENCES category(CATEGORY_ID)
);

--changeset andre:6
ALTER TABLE serie_category ALTER COLUMN SERIE_ID SET NOT NULL;
ALTER TABLE serie_category ALTER COLUMN CATEGORY_ID SET NOT NULL;

--changeset andre:7
DROP TABLE IF EXISTS user_serie_relationship;

CREATE TABLE user_serie_relationship(
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
            REFERENCES serie(SERIE_ID),
    CONSTRAINT check_status
        CHECK ( STATUS = 'PLAN_TO_WATCH' OR STATUS = 'WATCHING' OR STATUS = 'COMPLETED' OR STATUS = 'DROPPED' OR STATUS = 'ON_HOLD')
);

--changeset andre:8
ALTER TABLE "USER" ADD COLUMN CREATED_AT DATE;
ALTER TABLE "USER" ADD COLUMN UPDATED_AT DATE;

--changeset andre:9
ALTER TABLE user_serie_relationship DROP CONSTRAINT fk_user_relation;
ALTER TABLE serie_category DROP CONSTRAINT fk_serie;
DROP TABLE "USER";
ALTER TABLE serie_category ADD CONSTRAINT fk_serie FOREIGN KEY (SERIE_ID) REFERENCES serie(SERIE_ID);
ALTER TABLE user_serie_relationship ALTER COLUMN USER_ID type varchar(36);
ALTER TABLE user_serie_relationship ADD CONSTRAINT fk_user_relation FOREIGN KEY (USER_ID) REFERENCES "user_entity"(id);


--changeset andre:10
ALTER TABLE serie ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE user_serie_relationship ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE serie_category ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;
ALTER TABLE category ADD COLUMN ACTIVE boolean NOT NULL DEFAULT TRUE;


--changeset andre:11
CREATE TABLE image(
    id_image SERIAL PRIMARY KEY NOT NULL,
    desc_image VARCHAR(255),
    hash VARCHAR(255) not null,
    created_at timestamp not null ,
    active bool not null default true
);

CREATE TABLE serie_image(
    id_image INTEGER,
    id_serie INTEGER,
    CONSTRAINT fk_image
        FOREIGN KEY(id_image)
            REFERENCES image(id_image),
    CONSTRAINT fk_serie
        FOREIGN KEY(id_serie)
            REFERENCES serie(SERIE_ID),
    CONSTRAINT pk_serie_image
        PRIMARY KEY (id_image, id_serie)
);


--changeset andre:12

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION generate_searchable(_serie_name VARCHAR, _serie_synopse VARCHAR, _serie_status VARCHAR)
    RETURNS TEXT AS '
    BEGIN
        RETURN _serie_name || _serie_synopse || _serie_status;
    END;
    ' LANGUAGE plpgsql IMMUTABLE;

ALTER TABLE SERIE ADD COLUMN searchable text GENERATED ALWAYS AS (generate_searchable(serie_name, serie_synopse, SERIE_STATUS)) STORED;

CREATE INDEX IF NOT EXISTS idx_series_searchable ON public.serie USING gist (searchable public.gist_trgm_ops);