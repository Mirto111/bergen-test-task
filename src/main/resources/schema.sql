DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    message_id     INTEGER,
    queue            VARCHAR(255) NOT NULL,
    body            VARCHAR(255) NOT NULL,
    date_time            TIMESTAMP NOT NULL
);