package org.girevoy.tablemanager.service.impl;

import org.girevoy.tablemanager.dao.ColumnDao;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl implements ColumnService {
    private final ColumnDao columnDao;

    @Autowired
    public ColumnServiceImpl(ColumnDao columnDao) {
        this.columnDao = columnDao;
    }

    @Override
    public ResponseEntity<String> add(Column column) {
        try {
            columnDao.add(column);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Column column) {
        try {
            columnDao.delete(column);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    public ResponseEntity<String> rename(Column column, String newColumnName) {
        try {
            columnDao.rename(column, newColumnName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Override
    public ResponseEntity<String> changeType(Column column) {
        try {
            columnDao.changeType(column);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
