--liquibase formatted sql

--changeset oleg:create-multiple-tables splitStatements:true endDelimiter:;
DROP TABLE IF EXISTS test_table CASCADE;

CREATE TABLE test_table (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    date DATE,
    number INT
);

INSERT INTO test_table (name, date, number) VALUES ('Aaa', '2021-01-05', 10);
INSERT INTO test_table (name, date, number) VALUES ('Bbb', '2021-02-05', 11);
INSERT INTO test_table (name, date, number) VALUES ('Ccc', '2021-03-05', 12);
INSERT INTO test_table (name, date, number) VALUES ('Ddd', '2021-04-05', 13);
INSERT INTO test_table (name, date, number) VALUES ('Eee', '2021-05-05', 14);
