CREATE TABLE resources (
    resource_id SERIAL PRIMARY KEY,
    link VARCHAR(255),
    type VARCHAR(30)
);

CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    sku VARCHAR(30),
    upc VARCHAR(20),
    name VARCHAR(100),
    description VARCHAR(255),
    active BOOLEAN,
    category VARCHAR(20),
    brand_id INT REFERENCES brands (brand_id),
    manufacturer_id INT REFERENCES manufacturers (manufacturer_id),
    selling_price NUMERIC(14,2),
    cost_price NUMERIC(14,2),
    apply_gst BOOLEAN,
    length NUMERIC,
    width NUMERIC,
    height NUMERIC,
    weight NUMERIC,
    unit VARCHAR(20),
    units_per_carton int,
    units_per_pallet int,
    created_time TIMESTAMP WITH TIME ZONE,
    last_modified_time TIMESTAMP WITH TIME ZONE,
    physical_stock INT,
    locked_stock INT,
    arriving_quantity INT,
    average_cost NUMERIC(14,2)
);