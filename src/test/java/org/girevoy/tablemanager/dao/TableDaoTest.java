package org.girevoy.tablemanager.dao;

import java.util.ArrayList;
import java.util.List;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.Table;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest
@Sql({"/initTestDB.sql"})
public class TableDaoTest {
    @Autowired
    private TableDao tableDao;
    @Autowired
    private UnitDao unitDao;

    @Test
    public void test() {
        Table table = new Table();
        table.setName("test2");
        Column column1 = new Column("col1", "test2", DataType.TEXT);
        Column column2 = new Column("col2", "test2", DataType.INT);
        List<Column> list = new ArrayList<>();
        list.add(column1);
        list.add(column2);
        table.setColumns(list);

        tableDao.create(table);

        assertNotNull(unitDao.findAll("test2"));
    }



}
