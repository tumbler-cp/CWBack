CREATE
OR REPLACE FUNCTION update_last_update_time()
RETURNS TRIGGER AS $$
BEGIN
    NEW.last_update_time
:= NOW();
RETURN NEW;
END;
$$
LANGUAGE plpgsql;
;
CREATE TRIGGER trg_update_last_update_time
    BEFORE
        UPDATE
    ON order_status
    FOR EACH ROW
    EXECUTE FUNCTION update_last_update_time();

CREATE
OR REPLACE FUNCTION check_good_weight()
RETURNS TRIGGER AS $$
BEGIN
    IF
NEW.weight > 5 THEN
        RAISE EXCEPTION 'Вес товара превышает максимально допустимый (5 кг).';
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_check_good_weight
    BEFORE INSERT OR
UPDATE ON good
    FOR EACH ROW
    EXECUTE FUNCTION check_good_weight();

