package org.girevoy.tablemanager.service;

import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.springframework.http.ResponseEntity;

public interface ColumnService {
    ResponseEntity<String> add(Column column);
    ResponseEntity<String> delete(String tableName, String columnName);
    ResponseEntity<String> rename(String tableName, String columnName, String newColumnName);
    ResponseEntity<String> changeType(String tableName, String columnName, DataType dataType);

}
