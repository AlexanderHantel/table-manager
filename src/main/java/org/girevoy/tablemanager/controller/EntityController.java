package org.girevoy.tablemanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.girevoy.tablemanager.model.Entity;
import org.girevoy.tablemanager.service.EntityService;
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
@RequestMapping(produces = "application/json")
public class EntityController {
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @Operation(summary = "Insert new entity to specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Entity.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request. Entity, tableName or attributes can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable entity, wrong fields schema",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @PostMapping("/{tableName}/insertEntity")
    public ResponseEntity<Entity> insertEntity(
            @Parameter(description = "Table name for entity insert")
            @PathVariable String tableName,
            @RequestBody Entity entity) {

        return entityService.insert(entity);
    }

    @Operation(summary = "Delete entity from specified table by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request. Id can't be 0",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found, there is not such id in table",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @DeleteMapping("/{tableName}/deleteEntity/{id}")
    public ResponseEntity<String> deleteEntity(
            @Parameter(description = "Table name for entity delete")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be deleted")
            @PathVariable long id) {
        return entityService.delete(tableName, id);
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
    @PatchMapping("/{tableName}/updateEntity/{id}")
    public ResponseEntity<String> updateEntity(
            @Parameter(description = "Table name for entity update")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be update")
            @PathVariable long id,
            @Parameter(description = "All entity fields will be updated in DB according to condition of this object")
            @RequestBody Entity entity) {

        entity.setId(id);
        entity.setTableName(tableName);
        return entityService.update(entity);
    }

    @Operation(summary = "Find entity in specified table by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity was found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Entity.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request. Table name & id can't be empty or null",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entity not found, there is not such id in table",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/{tableName}/findEntityById/{id}")
    public ResponseEntity<Entity> findEntityById(
            @Parameter(description = "Table name for search")
            @PathVariable String tableName,
            @Parameter(description = "Entity id to be found")
            @PathVariable long id) {
        return entityService.findById(tableName, id);
    }

    @GetMapping("/{tableName}/findAllEntities")
    @Operation(summary = "Get all entities from specified table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entities were found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Entity.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request. Wrong table name",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    public ResponseEntity<List<Entity>> findAllEntities(
            @Parameter(description = "Table name")
            @PathVariable String tableName) {
        return entityService.findAll(tableName);
    }
}
