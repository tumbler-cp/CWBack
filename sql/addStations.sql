CREATE SEQUENCE IF NOT EXISTS drone_station_id_seq;

ALTER TABLE drone_station
    ALTER COLUMN id SET DEFAULT nextval('drone_station_id_seq');

SELECT setval('drone_station_id_seq', COALESCE((SELECT MAX(id) FROM drone_station), 1), false);


INSERT INTO drone_station (uuid)
SELECT gen_random_uuid()
FROM generate_series(1, 10);