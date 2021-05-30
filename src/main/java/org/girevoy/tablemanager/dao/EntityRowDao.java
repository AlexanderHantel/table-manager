package org.girevoy.tablemanager.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.girevoy.tablemanager.model.EntityRow;
import org.girevoy.tablemanager.model.mapper.ColumnMapper;
import org.girevoy.tablemanager.model.mapper.EntityRowMapper;
import org.girevoy.tablemanager.model.table.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class EntityRowDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EntityRow insert(EntityRow entityRow) throws Exception {
        if (entityRow == null || entityRow.getTableName() == null || entityRow.getAttributes().isEmpty()) {
            throw new Exception();
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> connection.prepareStatement(getQueryForInsert(entityRow), new String[] {"id"}), keyHolder);
        entityRow.setId((long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));

        return entityRow;
    }

    public int delete(EntityRow entityRow) {
        String sql = format("DELETE FROM %s WHERE id=%d;", entityRow.getTableName(), entityRow.getId());
        return jdbcTemplate.update(sql);
    }

    public int update(EntityRow entityRow) {
        String sql = getQueryForUpdate(entityRow);
        return jdbcTemplate.update(sql);
    }

    public EntityRow findInTableById(String tableName, long id) {
        String sql = format("SELECT * FROM %s WHERE id=%s;", tableName, id);
        List<Column> columns = getColumns(tableName);

        return jdbcTemplate.queryForObject(sql, new EntityRowMapper(columns));
    }

    public List<EntityRow> findAll(String tableName) {
        String sql = "SELECT * FROM " + tableName + ";";
        List<Column> columns = getColumns(tableName);

        return jdbcTemplate.query(sql, new EntityRowMapper(columns));
    }

    private List<Column> getColumns(String tableName) {
        String sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = '" + tableName + "';";
        return jdbcTemplate.query(sql, new ColumnMapper(tableName));
    }

    private String getQueryForInsert(EntityRow entityRow) {
        StringJoiner attributesNames = new StringJoiner(", ");
        StringJoiner attributesValues = new StringJoiner(", ");

        entityRow.getAttributes().forEach((columnName, value) -> {
            attributesNames.add(columnName);
            if (value.getClass().equals(String.class)) {
                attributesValues.add("'" + value + "'");
            }  else {
                if (value.getClass().equals(LocalDate.class)) {
                    attributesValues.add("'" + Date.valueOf(value.toString()) + "'");
                }
                else {
                    if (value.getClass().equals(Integer.class)) {
                        attributesValues.add(value.toString());
                    }
                }
            }
        });

        return format("INSERT INTO %s (%s) VALUES (%s);",
                entityRow.getTableName(), attributesNames, attributesValues);
    }

    private String getQueryForUpdate(EntityRow entityRow) {
        StringJoiner attributesValues = new StringJoiner(", ");

        entityRow.getAttributes().forEach((columnName, value) -> {
            if (value.getClass().equals(String.class)) {
                attributesValues.add(columnName + "='" + value + "'");
            }  else {
                if (value.getClass().equals(LocalDate.class)) {
                    attributesValues.add(columnName + "='" + Date.valueOf(value.toString()) + "'");
                }
                else {
                    if (value.getClass().equals(Integer.class)) {
                        attributesValues.add(columnName + "=" + value.toString());
                    }
                }
            }
        });

        return format("UPDATE %s SET %s WHERE id=%d;", entityRow.getTableName(), attributesValues, entityRow.getId());
    }
}
