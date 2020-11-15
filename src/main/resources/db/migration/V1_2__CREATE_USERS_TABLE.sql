CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30),
    encoded_password VARCHAR(100)
);

CREATE TABLE authoriries (
    id SERIAL PRIMARY KEY,
    permission VARCHAR(30)
);

CREATE TABLE users_authorities (
    user_id INT REFERENCES users (id),
    authority_id INT REFERENCES authoriries (id),
    PRIMARY KEY (user_id, authority_id)
);