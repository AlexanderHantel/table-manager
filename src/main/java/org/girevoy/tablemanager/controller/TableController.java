package org.girevoy.tablemanager.controller;

import org.girevoy.tablemanager.entity.Table;
import org.girevoy.tablemanager.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/table", produces = "application/json")
public class TableController {
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

//    @GetMapping("/new")
//    public String showCreateTablePage(Model model) {
//        List<DataType> dataTypes = List.of(DataType.INT, DataType.TEXT, DataType.DATE);
//        model.addAttribute("table", new Table());
//        model.addAttribute("dataTypes", dataTypes);
//        model.addAttribute("column1", new Column());
//        model.addAttribute("column2", new Column());
//        model.addAttribute("column3", new Column());
//        model.addAttribute("column4", new Column());
//        model.addAttribute("column5", new Column());
//
//
//        return "/table/new";
//    }

    @PostMapping
    public ResponseEntity<String> createTable(@RequestBody Table table) {
        return tableService.create(table);
    }

    @DeleteMapping("/{tableName}")
    public ResponseEntity<String> deleteTable(@PathVariable String tableName) {
        return tableService.delete(tableName);
    }
}
