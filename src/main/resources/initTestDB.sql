--liquibase formatted sql

--changeset oleg:create-multiple-tables splitStatements:true endDelimiter:;
DROP TABLE IF EXISTS test CASCADE;
DROP TABLE IF EXISTS test2 CASCADE;
DROP TABLE IF EXISTS anyname CASCADE;

CREATE TABLE test (
    id BIGSERIAL PRIMARY KEY,
    name TEXT,
    date DATE,
    number INT
);

INSERT INTO test (name, date, number) VALUES ('Aaaa', '2021-01-05', 10);
INSERT INTO test (name, date, number) VALUES ('Bbbb', '2021-02-05', 11);
INSERT INTO test (name, date, number) VALUES ('Cccc', '2021-03-05', 12);
INSERT INTO test (name, date, number) VALUES ('Dddd', '2021-04-05', 13);
INSERT INTO test (name, date, number) VALUES ('Eeee', '2021-05-05', 14);
