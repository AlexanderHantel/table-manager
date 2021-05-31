package org.girevoy.tablemanager.controller;

import java.util.ArrayList;
import java.util.List;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.Table;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql({"/initTestDB.sql"})
public class TableControllerTest {
    @Autowired
    private TableController controller;

//    @Test
//    public void test() throws Exception {
//        Table table = new Table();
//        table.setName("test2");
//        Column column1 = new Column("col1", "test2", DataType.TEXT);
//        Column column2 = new Column("col2", "test2", DataType.INT);
//        List<Column> list = new ArrayList<>();
//        list.add(column1);
//        list.add(column2);
//        table.setColumns(list);
//
//        String jsonObject = "{\"name\":\"test2\"," +
//                            "\"columns\":[" +
//                            "{\"name\": \"col2\"," +
//                            "\"dataType\": \"INT\"" +
//                            "}," +
//                            "{\"name\": \"col3\"," +
//                            "\"dataType\": \"TEXT\"" +
//                            "}]}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/table")
//                    .contentType(MediaType.APPLICATION_JSON).content(jsonObject))
//               .andExpect(MockMvcResultMatchers.status().is(200));
//    }

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
        controller.createTable(table);
        assertThat(controller).isNotNull();
    }
}
