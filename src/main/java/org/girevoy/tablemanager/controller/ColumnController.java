package org.girevoy.tablemanager.controller;

import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/table", produces = "application/json")
public class ColumnController {
    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    @PostMapping("/{tableName}")
    public ResponseEntity<String> addColumn(@PathVariable String tableName,
            @RequestBody Column column) {
        return columnService.add(column);
    }

    @DeleteMapping("/{tableName}/{columnName}")
    public ResponseEntity<String> deleteColumn(
            @PathVariable(value = "tableName") String tableName,
            @PathVariable(value = "columnName") String columnName,
            @RequestBody Column column) {
        return columnService.delete(column);
    }

    @PatchMapping("/{tableName}/{columnName}/name")
    public ResponseEntity<String> renameColumn(
            @PathVariable(value = "tableName") String tableName,
            @PathVariable(value = "columnName") String columnName,
            @RequestBody Column column,
            @RequestParam(value = "newColumnName") String newColumnName) {
        return columnService.rename(column, newColumnName);
    }

    @PatchMapping("/{tableName}/{columnName}/type")
    public ResponseEntity<String> changeColumnType(
            @PathVariable(value = "tableName") String tableName,
            @PathVariable(value = "columnName") String columnName,
            @RequestBody Column column) {
        return columnService.changeType(column);
    }
}
