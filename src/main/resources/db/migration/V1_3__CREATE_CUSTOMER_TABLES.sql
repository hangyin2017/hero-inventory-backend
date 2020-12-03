CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    customer_name VARCHAR(30),
    company_name VARCHAR(50),
    website VARCHAR(100),
    active BOOLEAN,
    salutation VARCHAR(30),
    first_name VARCHAR(30),
    last_name VARCHAR(30),
    email VARCHAR(50),
    phone VARCHAR(20),
    created_time TIMESTAMP WITH TIME ZONE,
    last_modified_time TIMESTAMP WITH TIME ZONE,
    comments VARCHAR(100)
);