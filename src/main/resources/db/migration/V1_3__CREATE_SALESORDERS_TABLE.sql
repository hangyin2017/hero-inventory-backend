CREATE TABLE salesorders (
    salesorder_id SERIAL PRIMARY KEY,
    salesorder_number VARCHAR(30),
    reference_number VARCHAR(20),
    date DATE,
    status VARCHAR(20),
    shipment_date DATE,
    invoiced_status VARCHAR(30),
    paid_status VARCHAR(30),
    shipped_status VARCHAR(30),
    creator_id INT REFERENCES users (id),
    created_time TIMESTAMP WITH TIME ZONE,
    last_modified_time TIMESTAMP WITH TIME ZONE,
    total_quality INT,
    comments VARCHAR(255)
);

