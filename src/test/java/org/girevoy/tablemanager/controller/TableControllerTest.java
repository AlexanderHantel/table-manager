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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Sql({"/initTestDB.sql"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTable_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Table table = new Table("test2", Arrays.asList(
                new Column("col1", "test2", DataType.TEXT),
                new Column("col2", "test2", DataType.INT),
                new Column("col3", "test2", DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(tableJson))
               .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createTable_shouldReturnHttpStatus422_ifUnacceptableTableName() throws Exception {
        Table table = new Table("table", Arrays.asList(
                new Column("col1", "test2", DataType.TEXT),
                new Column("col2", "test2", DataType.INT),
                new Column("col3", "test2", DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(tableJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus200_ifCorrectTableName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/table/test"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus404_ifNoSuchTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/table/anyName"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus200_ifNewNameIsCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test")
                                              .param("newName", "anyname"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus422_ifNewNameIsIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test")
                                              .param("newName", "table"))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
