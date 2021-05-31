package org.girevoy.tablemanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Insert new entity to specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Unit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request. Entity, tableName or attributes can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity, wrong fields schema",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{tableName}/unit")
    public ResponseEntity<Unit> insertUnit(
            @Parameter(description = "Table name for entity insert")
            @PathVariable String tableName,
            @RequestBody Unit unit) {

        return unitService.insert(unit);
    }

    @DeleteMapping("/{tableName}/unit/{id}")
    @Operation(summary = "Delete entity from specified table by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request. Table name & id can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found, there is not such id in table",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<String> deleteUnit(
            @Parameter(description = "Table name for entity delete")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be deleted")
            @PathVariable long id) {
        return unitService.delete(tableName, id);
    }

    @Operation(summary = "Update entity in specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request. Table name & id can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity, fields of entity can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found, there is not such id in table",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PatchMapping("/{tableName}/unit/{id}")
    public ResponseEntity<String> updateUnit(
            @Parameter(description = "Table name for entity update")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be update")
            @PathVariable long id,
            @Parameter(description = "All entity fields will be updated in DB according to condition of this object")
            @RequestBody Unit unit) {

        unit.setId(id);
        unit.setTableName(tableName);
        return unitService.update(unit);
    }

    @Operation(summary = "Find entity in specified table by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Unit.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request. Table name & id can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found, there is not such id in table",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{tableName}/unit/{id}")
    public ResponseEntity<Unit> findById(
            @Parameter(description = "Table name for search")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be found")
            @PathVariable long id) {
        return unitService.findById(tableName, id);
    }

    @GetMapping("/{tableName}/unit")
    @Operation(summary = "Get all entities from specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entities were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Unit.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<List<Unit>> findAll(
            @Parameter(description = "Table name")
            @PathVariable String tableName) {
        return unitService.findAll(tableName);
    }
}
