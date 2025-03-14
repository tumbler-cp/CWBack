CREATE INDEX idx_warehouse_drone_station_goods
    ON ware_house (owner_id);

CREATE INDEX idx_order_status_order_id_status_time
    ON order_status (order_id, estimated_time_left);

CREATE INDEX idx_customer_user_id
    ON customer (user_id);

CREATE INDEX idx_order_sender_customer
    ON app_order (sender_user_id, customer_user_id);
