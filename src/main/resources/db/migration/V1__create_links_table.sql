CREATE TABLE links (
    id INT AUTO_INCREMENT PRIMARY KEY,
    short_link VARCHAR(255) NOT NULL,
    original_link VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    transition_count INT NOT NULL,
    created_by VARCHAR(255) NOT NULL
);
