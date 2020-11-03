CREATE TABLE suppliers (
    supplier_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(255),
    phone VARCHAR(20),
    address VARCHAR(255)
);

CREATE TABLE brands (
    brand_id SERIAL PRIMARY KEY,
    code VARCHAR(30),
    name VARCHAR(100),
    supplier_id INT REFERENCES suppliers (supplier_id)
);