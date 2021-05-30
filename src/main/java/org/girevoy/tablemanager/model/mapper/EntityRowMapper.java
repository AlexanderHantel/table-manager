package org.girevoy.tablemanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.girevoy.tablemanager.model.EntityRow;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.springframework.jdbc.core.RowMapper;

public class EntityRowMapper implements RowMapper<EntityRow> {
    private List<Column> columns;

    public EntityRowMapper(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public EntityRow mapRow(ResultSet resultSet, int i) {
        EntityRow entityRow = new EntityRow();
        entityRow.setTableName((columns.get(0)).getTableName());
        columns.forEach(column -> {
            try {
                if (column.getName().equals("id")) {
                    entityRow.setId(resultSet.getLong("id"));
                } else{
                    if (column.getDataType().equals(DataType.INT)) {
                        entityRow.getAttributes().put(column.getName(), resultSet.getInt(column.getName()));
                    } else {
                        if (column.getDataType().equals(DataType.TEXT)) {
                            entityRow.getAttributes().put(column.getName(), resultSet.getString(column.getName()));
                        } else {
                            if (column.getDataType().equals(DataType.DATE)) {
                                entityRow.getAttributes().put(column.getName(), resultSet.getDate(column.getName()).toLocalDate());
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return entityRow;
    }
}
