package org.girevoy.tablemanager.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import org.girevoy.tablemanager.model.Unit;
import org.girevoy.tablemanager.model.mapper.ColumnMapper;
import org.girevoy.tablemanager.model.mapper.UnitMapper;
import org.girevoy.tablemanager.model.table.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class UnitDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Unit insert(Unit unit) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> connection.prepareStatement(getQueryForInsert(unit), new String[] {"id"}), keyHolder);
        unit.setId((long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));

        return unit;
    }

    public int delete(String tableName, long id) {
        String sql = format("DELETE FROM %s WHERE id=%d;", tableName, id);
        return jdbcTemplate.update(sql);
    }

    public int update(Unit unit) {
        String sql = getQueryForUpdate(unit);
        return jdbcTemplate.update(sql);
    }

    public Optional<Unit> findById(String tableName, long id) {
        String sql = format("SELECT * FROM %s WHERE id=%s;", tableName, id);
        List<Column> columns = getColumns(tableName);

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UnitMapper(columns)));
    }

    public List<Unit> findAll(String tableName) {
        String sql = "SELECT * FROM " + tableName + ";";
        List<Column> columns = getColumns(tableName);

        return jdbcTemplate.query(sql, new UnitMapper(columns));
    }

    private List<Column> getColumns(String tableName) {
        String sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = '" + tableName + "';";
        return jdbcTemplate.query(sql, new ColumnMapper(tableName));
    }

    private String getQueryForInsert(Unit unit) {
        StringJoiner attributesNames = new StringJoiner(", ");
        StringJoiner attributesValues = new StringJoiner(", ");

        unit.getAttributes().forEach((columnName, value) -> {
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
                unit.getTableName(), attributesNames, attributesValues);
    }

    private String getQueryForUpdate(Unit unit) {
        StringJoiner attributesValues = new StringJoiner(", ");

        unit.getAttributes().forEach((columnName, value) -> {
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

        return format("UPDATE %s SET %s WHERE id=%d;", unit.getTableName(), attributesValues, unit.getId());
    }
}
