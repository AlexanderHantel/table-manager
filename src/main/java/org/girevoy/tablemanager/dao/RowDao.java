package org.girevoy.tablemanager.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.girevoy.tablemanager.model.Row;
import org.girevoy.tablemanager.model.mapper.EntityMapper;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class RowDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Row row) throws Exception {
        if (row == null || row.getTableName() == null || row.getAttributes().isEmpty()) {
            throw new Exception();
        }
        String sql = getInsertQuery(row);
        jdbcTemplate.update(sql);
    }

    public List<Row> findAll(String tableName) {
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

        return jdbcTemplate.query(sql, new EntityMapper(columns));
    }

    private String getInsertQuery(Row row) {
        StringJoiner attributesNames = new StringJoiner(", ");
        StringJoiner attributesValues = new StringJoiner(", ");

        row.getAttributes().forEach((columnName, value) -> {
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

        return format("INSERT INTO %s (%s) VALUES (%s)",
                row.getTableName(), attributesNames, attributesValues);
    }
}
