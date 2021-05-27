package org.girevoy.tablemanager.service.impl;

import org.girevoy.tablemanager.entity.Column;
import org.girevoy.tablemanager.service.ColumnService;
import org.springframework.http.ResponseEntity;

public class ColumnServiceImpl implements ColumnService {
    @Override
    public ResponseEntity<String> add(Column column) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Column column) {
        return null;
    }

    @Override
    public ResponseEntity<String> rename(Column column, String newColumnName) {
        return null;
    }

    @Override
    public ResponseEntity<String> changeType(Column column) {
        return null;
    }
}
