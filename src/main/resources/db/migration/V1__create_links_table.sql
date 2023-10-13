CREATE TABLE link (
    short_link VARCHAR(20) NOT NULL PRIMARY KEY,
    link VARCHAR(255) NOT NULL,
    open_count BIGINT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    isActive boolean,
    user_id    BIGINT NOT NULL
);
