package org.girevoy.tablemanager.service.impl;

import java.util.List;
import java.util.Optional;
import org.girevoy.tablemanager.dao.EntityDao;
import org.girevoy.tablemanager.model.Entity;
import org.girevoy.tablemanager.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl implements EntityService {
    EntityDao dao;

    @Autowired
    public EntityServiceImpl(EntityDao dao) {
        this.dao = dao;
    }

    @Override
    public ResponseEntity<Entity> insert(Entity entity) {
        if (entity == null || entity.getTableName() == null || entity.getAttributes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            dao.insert(entity);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(entity);
    }

    @Override
    public ResponseEntity<String> delete(String tableName, long id) {
        if (tableName == null || "".equals(tableName) || id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int deletedRows;
        try {
            deletedRows = dao.delete(tableName, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (deletedRows == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> update(Entity entity) {
        if (entity.getTableName() == null ||
            "".equals(entity.getTableName()) ||
            entity.getId() == 0 ||
            entity.getAttributes() == null ||
            entity.getAttributes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int updatedRows;
        try {
            updatedRows = dao.update(entity);
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
    public ResponseEntity<Entity> findById(String tableName, long id) {
        if (tableName == null || "".equals(tableName) || id == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Entity> entity;
        try {
            entity = dao.findById(tableName, id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public ResponseEntity<List<Entity>> findAll(String tableName) {
        List<Entity> result;
        try {
            result = dao.findAll(tableName);
        } catch (BadSqlGrammarException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(result);
    }
}
