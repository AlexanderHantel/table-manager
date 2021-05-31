package org.girevoy.tablemanager.service.impl;

import org.girevoy.tablemanager.dao.ColumnDao;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> delete(String tableName, String columnName) {
        try {
            columnDao.delete(tableName, columnName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> rename(String tableName, String columnName, String newColumnName) {
        try {
            columnDao.rename(tableName, columnName, newColumnName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> changeType(String tableName, String columnName, DataType dataType) {
        try {
            columnDao.changeType(tableName, columnName, dataType);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
