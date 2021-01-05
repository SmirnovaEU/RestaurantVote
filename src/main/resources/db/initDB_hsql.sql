DROP TABLE user_roles IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX rests_unique_idx
    ON RESTAURANTS (id);

CREATE TABLE dishes
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date    TIMESTAMP NOT NULL,
    rest_id INTEGER   NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    CONSTRAINT dish_rest_date_idx UNIQUE (name, rest_id, date),
    FOREIGN KEY (rest_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_idx
    ON DISHES (id);

CREATE TABLE votes
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date    TIMESTAMP NOT NULL,
    rest_id INTEGER   NOT NULL,
    user_id INTEGER   NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (rest_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx
    ON votes (user_id, date)