package org.girevoy.tablemanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.enums.DataType;
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
    @Operation(summary = "Add new column to specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Column was added",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Column can't be added. Table name, column name or data type are unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> addColumn(@PathVariable String tableName,
            @Parameter(description = "Column object to be added")
            @RequestBody Column column) {
        return columnService.add(column);
    }

    @DeleteMapping("/{tableName}/{columnName}")
    @Operation(summary = "Delete column by name from specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Column was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Column can't be deleted. Table name, column name or data type are unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> deleteColumn(
            @Parameter(description = "Table name")
            @PathVariable(value = "tableName") String tableName,
            @Parameter(description = "Column name to be deleted")
            @PathVariable(value = "columnName") String columnName) {
        return columnService.delete(tableName, columnName);
    }

    @PatchMapping("/{tableName}/{columnName}/name")
    @Operation(summary = "Rename column of specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Column was renamed",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Column can't be renamed. Table name, column name or data type are unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> renameColumn(
            @Parameter(description = "Table name")
            @PathVariable(value = "tableName") String tableName,
            @Parameter(description = "Column name to be renamed")
            @PathVariable(value = "columnName") String columnName,
            @Parameter(description = "New column name")
            @RequestParam(value = "newColumnName") String newColumnName) {
        return columnService.rename(tableName, columnName, newColumnName);
    }

    @PatchMapping("/{tableName}/{columnName}/type")
    @Operation(summary = "Change column data type of specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Column was updated",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Column can't be updated. Table name, column name or data type are unacceptable",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> changeColumnType(
            @Parameter(description = "Table name")
            @PathVariable(value = "tableName") String tableName,
            @Parameter(description = "Column name to be renamed")
            @PathVariable(value = "columnName") String columnName,
            @Parameter(description = "New data type")
            @RequestBody DataType dataType) {
        return columnService.changeType(tableName, columnName, dataType);
    }
}
