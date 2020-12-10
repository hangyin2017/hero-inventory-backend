CREATE TABLE users (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 100001),
    username VARCHAR(30) UNIQUE NOT NULL,
    encoded_password VARCHAR(100),
    email VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20)
);

CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    permission VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE users_authorities (
    user_id INT REFERENCES users (id),
    authority_id INT REFERENCES authorities (id),
    PRIMARY KEY (user_id, authority_id)
);