package org.girevoy.tablemanager.dao;

import org.girevoy.tablemanager.model.table.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class ColumnDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Column column) {
        String sqlQuery = format("ALTER TABLE %s ADD IF NOT EXISTS %s %s",
                column.getTable().getName(), column.getName(), column.getDataType().name());
        jdbcTemplate.update(sqlQuery);
    }

    public void delete(Column column) {
        String sqlQuery = format("ALTER TABLE %s DROP COLUMN %s",
                column.getTable().getName(), column.getName());
        jdbcTemplate.update(sqlQuery);
    }

    public void rename(Column column, String newColumnName) {
        String sqlQuery = format("ALTER TABLE %s RENAME COLUMN %s TO %s",
                column.getTable().getName(), column.getName(), newColumnName);
        jdbcTemplate.update(sqlQuery);
    }

    public void changeType(Column column) {
        String sqlQuery = format("ALTER TABLE %s ALTER COLUMN %2$s TYPE %3$s USING (\"%2$s\"::text::%3$s)",
                column.getTable().getName(), column.getName(), column.getDataType().name());
        jdbcTemplate.update(sqlQuery);
    }
}
