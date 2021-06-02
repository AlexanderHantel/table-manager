package org.girevoy.tablemanager.service;

import java.util.List;
import org.girevoy.tablemanager.model.Entity;
import org.springframework.http.ResponseEntity;

public interface EntityService {
    ResponseEntity<Entity> insert(Entity entity);
    ResponseEntity<String> delete(String tableName, long id);
    ResponseEntity<String> update(Entity entity);
    ResponseEntity<Entity> findById(String tableName, long id);
    ResponseEntity<List<Entity>> findAll(String tableName);
}
