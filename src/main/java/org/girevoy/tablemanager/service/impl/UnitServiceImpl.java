package org.girevoy.tablemanager.service.impl;

import java.util.List;
import java.util.Optional;
import org.girevoy.tablemanager.dao.UnitDao;
import org.girevoy.tablemanager.model.Unit;
import org.girevoy.tablemanager.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {
    UnitDao dao;

    @Autowired
    public UnitServiceImpl(UnitDao dao) {
        this.dao = dao;
    }

    @Override
    public ResponseEntity<Unit> insert(Unit unit) {
        if (unit == null || unit.getTableName() == null || unit.getAttributes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            dao.insert(unit);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (unit.getId() == 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return ResponseEntity.ok(unit);
    }

    @Override
    public ResponseEntity<String> delete(String tableName, long id) {
        if (tableName.isBlank() || id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int deletedRows;
        try {
            deletedRows = dao.delete(tableName, id);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (deletedRows == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> update(Unit unit) {
        if (unit.getTableName().isBlank() || unit.getId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int updatedRows;
        try {
            updatedRows = dao.update(unit);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (updatedRows == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Unit> findById(String tableName, long id) {
        if (tableName.isBlank() || id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Unit> unit;
        try {
            unit = dao.findById(tableName, id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return unit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<List<Unit>> findAll(String tableName) {
        List<Unit> result;
        try {
            result = dao.findAll(tableName);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(result);
    }
}
