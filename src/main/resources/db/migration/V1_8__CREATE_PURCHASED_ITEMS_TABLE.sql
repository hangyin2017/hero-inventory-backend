CREATE TABLE purchased_items (
    purchased_item_id SERIAL PRIMARY KEY,
    item_id INT REFERENCES items (item_id),
    order_id INT REFERENCES purchaseorders (purchaseorder_id),
    quantity INT,
    rate NUMERIC(14,2)
);

