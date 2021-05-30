package org.girevoy.tablemanager.service;

import java.util.List;
import org.girevoy.tablemanager.model.Unit;
import org.springframework.http.ResponseEntity;

public interface UnitService {
    ResponseEntity<Unit> insert(Unit unit);
    ResponseEntity<String> delete(String tableName, long id);
    ResponseEntity<String> update(Unit unit);
    ResponseEntity<Unit> findById(String tableName, long id);
    ResponseEntity<List<Unit>> findAll(String tableName);
}
