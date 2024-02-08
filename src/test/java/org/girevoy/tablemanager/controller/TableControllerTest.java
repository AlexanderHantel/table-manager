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

import static java.lang.String.format;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/initTestDB.sql"})
public class TableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String EXISTING_TABLE_FROM_DB = "test_table";
    private static final String TABLE_TO_BE_CREATED = "test_table2";


    @Test
    public void  createTable_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Table table = new Table(TABLE_TO_BE_CREATED, Arrays.asList(
                new Column("column1", TABLE_TO_BE_CREATED, DataType.TEXT),
                new Column("column2", TABLE_TO_BE_CREATED, DataType.INT),
                new Column("column3", TABLE_TO_BE_CREATED, DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tableJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createTable_shouldReturnHttpStatus422_ifUnacceptableTableName() throws Exception {
        Table table = new Table(EXISTING_TABLE_FROM_DB, Arrays.asList(
                new Column("column1", TABLE_TO_BE_CREATED, DataType.TEXT),
                new Column("column2", TABLE_TO_BE_CREATED, DataType.INT),
                new Column("column3", TABLE_TO_BE_CREATED, DataType.DATE)));

        Gson gson = new Gson();
        String tableJson = gson.toJson(table);

        mockMvc.perform(MockMvcRequestBuilders.post("/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tableJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus200_ifCorrectTableName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(format("/deleteTable/%s", EXISTING_TABLE_FROM_DB)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteTable_shouldReturnHttpStatus404_ifNoSuchTable() throws Exception {
        String notExistingTableName = "any_name";
        mockMvc.perform(MockMvcRequestBuilders.delete(format("/deleteTable/%s", notExistingTableName)))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus200_ifNewNameIsCorrect() throws Exception {
        String newTableName = "any_name";

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/renameTable/%s", EXISTING_TABLE_FROM_DB))
                .param("newName", newTableName))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void renameTable_shouldReturnHttpStatus422_ifNewNameIsIncorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(format("/renameTable/%s", EXISTING_TABLE_FROM_DB))
                .param("newName", EXISTING_TABLE_FROM_DB))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
