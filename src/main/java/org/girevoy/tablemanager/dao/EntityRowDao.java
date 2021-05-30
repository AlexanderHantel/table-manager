package org.girevoy.tablemanager.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.girevoy.tablemanager.model.EntityRow;
import org.girevoy.tablemanager.model.mapper.EntityRowMapper;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
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

        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
                getInsertQuery(entityRow));
        creatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator creator = creatorFactory.newPreparedStatementCreator(new ArrayList<>());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);

        long l = (long) keyHolder.getKeys().get("id");

        entityRow.setId((int) l);
        return entityRow;
    }

    public int delete(EntityRow entityRow) {
        String sql = format("DELETE FROM %s WHERE id=%d;", entityRow.getTableName(), entityRow.getId());
        return jdbcTemplate.update(sql);
    }

    public int update(EntityRow entityRow) {
        String sql = getUpdateQuery(entityRow);
        return jdbcTemplate.update(sql);
    }

    public List<EntityRow> findAll(String tableName) {
        String sql = "SELECT * FROM " + tableName + ";";
        List<Column> columns = new ArrayList();
        jdbcTemplate.query(sql, new ResultSetExtractor() {

            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for(int i = 1 ; i <= columnCount ; i++){
                    Column column = new Column();
                    column.setName(metaData.getColumnName(i));
                    String dataTypeName = metaData.getColumnTypeName(i);

                    if (dataTypeName.equals("int4") || dataTypeName.equals("bigserial")) {
                        column.setDataType("INT");
                    } else {
                        if (dataTypeName.equals("date")) {
                            column.setDataType("DATE");
                        } else {
                            if (dataTypeName.equals("text")) {
                                column.setDataType("TEXT");
                            }
                        }
                    }
                    Table table = new Table();
                    table.setName(tableName);
                    column.setTable(table);
                    columns.add(column);
                }
                return columnCount;
            }
        });

        return jdbcTemplate.query(sql, new EntityRowMapper(columns));
    }

    private String getInsertQuery(EntityRow entityRow) {
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

    private String getUpdateQuery(EntityRow entityRow) {
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
