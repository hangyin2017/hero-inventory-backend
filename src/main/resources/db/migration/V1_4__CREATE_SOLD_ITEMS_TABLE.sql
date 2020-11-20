CREATE TABLE sold_items (
    sold_item_id SERIAL PRIMARY KEY,
    item_id INT REFERENCES items (item_id),
    order_id INT REFERENCES salesorders (salesorder_id),
    created_time TIMESTAMP WITH TIME ZONE,
    quality INT,
    rate NUMERIC(14,2),
    comments VARCHAR(255)
);

