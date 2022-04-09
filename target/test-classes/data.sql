CREATE TABLE accounts(
    user_id    serial PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(50)         NOT NULL
);
CREATE TABLE histories (
    user_id INT NULL,
    username VARCHAR ( 50 ) UNIQUE NOT NULL,
    history VARCHAR ( 50 ) NOT NULL
);
