package org.girevoy.tablemanager.dao;

import org.girevoy.tablemanager.entity.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static java.lang.String.format;

public class ColumnDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Column column) {
        String sqlQuery = format("ALTER TABLE %s ADD %s TYPE %s",
                column.getTable().getName(), column.getName(), column.getDataType().name());
        jdbcTemplate.update(sqlQuery);
    }

    public void delete(Column column) {
        String sqlQuery = format("ALTER TABLE %s DROP COLUMN %s",
                column.getTable().getName(), column.getName());
        jdbcTemplate.update(sqlQuery);
    }

    public void rename(Column column, String newColumnName) {
        String sqlQuery = format("ALTER TABLE %s RENAME COLUMN %s TYPE %s",
                column.getTable().getName(), column.getName(), newColumnName);
        jdbcTemplate.update(sqlQuery);
    }

    public void changeType(Column column) {
        String sqlQuery = format("ALTER TABLE %s ALTER COLUMN %s TYPE %s",
                column.getTable().getName(), column.getName(), column.getDataType().name());
        jdbcTemplate.update(sqlQuery);
    }
}
