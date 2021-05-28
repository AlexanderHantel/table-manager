package org.girevoy.tablemanager.controller;

import org.girevoy.tablemanager.model.table.Table;
import org.girevoy.tablemanager.service.TableService;
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
public class TableController {
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<String> createTable(@RequestBody Table table) {
        return tableService.create(table);
    }

    @DeleteMapping("/{tableName}")
    public ResponseEntity<String> deleteTable(@PathVariable String tableName) {
        return tableService.delete(tableName);
    }

    @PatchMapping("/{tableName}")
    public ResponseEntity<String> renameTable(@PathVariable String tableName,
                                              @RequestParam(value = "newName") String newName) {
        return tableService.rename(tableName, newName);
    }
}
