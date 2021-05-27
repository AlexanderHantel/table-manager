package org.girevoy.tablemanager.dao;

import org.girevoy.tablemanager.entity.Column;
import org.girevoy.tablemanager.entity.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Component
@Transactional
public class TableDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Table table) {
        String sqlQuery = getCreateQuery(table);
        jdbcTemplate.update(sqlQuery);
    }

    private String getCreateQuery(Table table) {
        StringBuilder columnsDeclaration = new StringBuilder();

        for (Column column : table.getColumns()) {
            columnsDeclaration.append(", ")
                              .append(column.getName())
                              .append(" ")
                              .append(column.getDataType());
        }

        return format("CREATE TABLE %s (id BIGSERIAL PRIMARY KEY%s);",
                                 table.getName(), columnsDeclaration);
    }

    public void delete(String tableName) {
        String sqlQuery = format("DROP TABLE %s", tableName);
        jdbcTemplate.update(sqlQuery);
    }

    public void rename(String oldName, String newName) {
        String sqlQuery = format("ALTER TABLE %s RENAME TO %s", oldName, newName);
        jdbcTemplate.update(sqlQuery);
    }
}
