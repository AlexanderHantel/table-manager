package org.girevoy.tablemanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.girevoy.tablemanager.model.Entity;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class UnitMapper implements RowMapper<Entity> {
    private final List<Column> columns;

    public UnitMapper(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public Entity mapRow(@NonNull ResultSet resultSet, int i) throws SQLException {
        Entity entity = new Entity();
        entity.setTableName((columns.get(0)).getTableName());

        for (Column column : columns) {
            if (column.getName().equals("id")) {
                entity.setId(resultSet.getLong("id"));
            } else{
                if (column.getDataType().equals(DataType.INT)) {
                    entity.getAttributes().put(column.getName(), resultSet.getInt(column.getName()));
                } else {
                    if (column.getDataType().equals(DataType.TEXT)) {
                        entity.getAttributes().put(column.getName(), resultSet.getString(column.getName()));
                    } else {
                        if (column.getDataType().equals(DataType.DATE)) {
                            entity.getAttributes().put(column.getName(), resultSet.getDate(column.getName()).toLocalDate());
                        }
                    }
                }
            }
        }

        return entity;
    }
}
