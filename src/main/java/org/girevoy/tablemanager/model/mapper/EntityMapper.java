package org.girevoy.tablemanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.girevoy.tablemanager.model.Row;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.springframework.jdbc.core.RowMapper;

public class EntityMapper implements RowMapper<Row> {
    private List<Column> columns;

    public EntityMapper(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public Row mapRow(ResultSet resultSet, int i) throws SQLException {
        Row row = new Row();
        columns.forEach(column -> {
            try {
                if (column.getName().equals("id")) {
                    row.setId(resultSet.getInt("id"));
                } else{
                    if (column.getDataType().equals(DataType.INT)) {
                        row.getAttributes().put(column.getName(), resultSet.getInt(column.getName()));
                    } else {
                        if (column.getDataType().equals(DataType.TEXT)) {
                            row.getAttributes().put(column.getName(), resultSet.getString(column.getName()));
                        } else {
                            if (column.getDataType().equals(DataType.DATE)) {
                                row.getAttributes().put(column.getName(), resultSet.getDate(column.getName()).toLocalDate());
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return row;
    }
}
