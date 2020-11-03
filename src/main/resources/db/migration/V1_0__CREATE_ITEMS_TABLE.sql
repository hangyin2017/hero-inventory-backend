CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    code varchar(30),
    name varchar(100),
    brand varchar(50),
    category varchar(20),
    supplier varchar (50),
    weight varchar(20),
    standard_price numeric(6,2),
    cost numeric(6,2),
    quantity INT,
    remark varchar(50),
    date_of_add DATE,
    shelf_life varchar(20)
);