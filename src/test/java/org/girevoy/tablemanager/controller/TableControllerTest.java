package org.girevoy.tablemanager.controller;

import com.google.gson.Gson;
import java.util.Arrays;
import org.girevoy.tablemanager.model.table.Column;
import org.girevoy.tablemanager.model.table.Table;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/initTestDB.sql"})
public class TableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void  createTable_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Table table = new Table("test_table2", Arrays.asList(
                new Column("column1", "test_table2", DataType.TEXT),
                new Column("column2", "test_table2", DataType.INT),
                new Column("column3", "test_table2", DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tableJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createTable_shouldReturnHttpStatus422_ifUnacceptableTableName() throws Exception {
        Table table = new Table("test_table", Arrays.asList(
                new Column("column1", "test_table2", DataType.TEXT),
                new Column("column2", "test_table2", DataType.INT),
                new Column("column3", "test_table2", DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tableJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus200_ifCorrectTableName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteTable/test_table"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus404_ifNoSuchTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteTable/any_name"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus200_ifNewNameIsCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/renameTable/test_table")
                .param("newName", "any_name"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus422_ifNewNameIsIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/renameTable/test_table")
                .param("newName", "test_table"))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
