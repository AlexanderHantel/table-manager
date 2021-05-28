package org.girevoy.tablemanager.service;

import org.girevoy.tablemanager.model.table.Column;
import org.springframework.http.ResponseEntity;

public interface ColumnService {
    ResponseEntity<String> add(Column column);
    ResponseEntity<String> delete(Column column);
    ResponseEntity<String> rename(Column column, String newColumnName);
    ResponseEntity<String> changeType(Column column);

}
