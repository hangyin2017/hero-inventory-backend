CREATE TABLE ordered_items (
    ordered_item_id SERIAL PRIMARY KEY,
    item_id INT REFERENCES items (item_id),
    order_id INT REFERENCES sales_orders (salesorder_id),
    created_time TIMESTAMP WITH TIME ZONE,
    total_quality INT,
    comments VARCHAR(255)
);