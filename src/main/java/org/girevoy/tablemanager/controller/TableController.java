package org.girevoy.tablemanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create new table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Table was created",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Table can't be created. Table name, fields names or values are unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> createTable(
            @Parameter(description = "Table to be created")
            @RequestBody Table table) {
        return tableService.create(table);
    }

    @DeleteMapping("/{tableName}")
    @Operation(summary = "Delete table by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Table was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Table not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> deleteTable(
            @Parameter(description = "Table to be deleted")
            @PathVariable String tableName) {
        return tableService.delete(tableName);
    }

    @Operation(summary = "Rename table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Table was renamed",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Table can't be renamed. New table name is unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PatchMapping("/{tableName}")
    public ResponseEntity<String> renameTable(
            @Parameter(description = "Table to be renamed")
            @PathVariable String tableName,
            @Parameter(description = "New table name")
            @RequestParam(value = "newName") String newName) {
        return tableService.rename(tableName, newName);
    }
}
