package org.girevoy.tablemanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.girevoy.tablemanager.model.table.Column;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class ColumnMapper implements RowMapper<Column> {
    private final String tableName;

    public ColumnMapper(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Column mapRow(@NonNull ResultSet resultSet, int i) throws SQLException {
        Column column = new Column();
        column.setName(resultSet.getString("column_name"));
        String dataTypeName = resultSet.getString("data_type");

        if (dataTypeName.equals("integer")) {
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
        column.setTableName(tableName);
        return column;
    }
}
