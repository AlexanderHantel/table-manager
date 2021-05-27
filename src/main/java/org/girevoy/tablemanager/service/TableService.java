package org.girevoy.tablemanager.service;

import org.girevoy.tablemanager.entity.Table;
import org.springframework.http.ResponseEntity;

public interface TableService {
    ResponseEntity<String> create(Table table);
    ResponseEntity<String> delete(String tableName);
    ResponseEntity<String> rename(String oldName, String newName);
}
