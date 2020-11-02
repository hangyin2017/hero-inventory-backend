CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    code varchar(30),
    name varchar(100),
    brand varchar(50),
    category varchar(20),
    supplier varchar (50),
    weight varchar(20),
    standardPrice numeric(6,2),
    cost numeric(6,2),
    quantity INT,
    remark varchar(50),
    dateOfAdd DATE,
    shelfLife varchar(20)
);