package org.girevoy.tablemanager.controller;

import java.util.List;
import org.girevoy.tablemanager.model.Unit;
import org.girevoy.tablemanager.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class UnitController {
    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/{tableName}/unit")
    public ResponseEntity<Unit> insertUnit(
            @PathVariable String tableName,
            @RequestBody Unit unit) {

        return unitService.insert(unit);
    }

    @DeleteMapping("/{tableName}/unit/{id}")
    public ResponseEntity<String> deleteUnit(
            @PathVariable String tableName,
            @PathVariable long id) {
        return unitService.delete(tableName, id);
    }

    @PatchMapping("/{tableName}/unit/{id}")
    public ResponseEntity<String> updateUnit(
            @PathVariable String tableName,
            @PathVariable long id,
            @RequestBody Unit unit) {
        return unitService.update(unit);
    }

    @GetMapping("/{tableName}/unit/{id}")
    public ResponseEntity<Unit> findById(
            @PathVariable String tableName,
            @PathVariable long id) {
        return unitService.findById(tableName, id);
    }

    @GetMapping("/{tableName}/unit")
    public ResponseEntity<List<Unit>> findAll(
            @PathVariable String tableName) {
        return unitService.findAll(tableName);
    }
}
