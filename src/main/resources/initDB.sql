--liquibase formatted sql

--changeset oleg:create-multiple-tables splitStatements:true endDelimiter:;
DROP TABLE IF EXISTS kawabanga CASCADE;