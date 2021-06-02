package org.girevoy.tablemanager.controller;

import com.google.gson.Gson;
import org.girevoy.tablemanager.model.table.Column;
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
public class ColumnControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createColumn_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Column column = new Column("col1", "test", DataType.TEXT);

        Gson gson = new Gson();
        String columnJson = gson.toJson(column);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(columnJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void createColumn_shouldReturnHttpStatus422_ifIncorrectRequestBody() throws Exception {
        Column column = new Column("col1", "wrongTable", DataType.TEXT);

        Gson gson = new Gson();
        String columnJson = gson.toJson(column);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(columnJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void createColumn_shouldReturnHttpStatus422_ifUnacceptableColumnName() throws Exception {
        Column column = new Column("table", "test", DataType.TEXT);

        Gson gson = new Gson();
        String columnJson = gson.toJson(column);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/table/test")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(columnJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void deleteColumn_shouldReturnHttpStatus200_ifCorrectPathVariables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/table/test/name"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteColumn_shouldReturnHttpStatus422_ifIncorrectPathVariables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/table/test/someColumn"))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void renameColumn_shouldReturnHttpStatus200_ifCorrectPathVariablesAndParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/date/name")
                                              .param("newColumnName", "someName"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void renameColumn_shouldReturnHttpStatus422_ifIncorrectPathVariables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/someColumn/name")
                                              .param("newColumnName", "someName"))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void renameColumn_shouldReturnHttpStatus422_ifUnacceptableColumnName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/date/name")
                                              .param("newColumnName", "table"))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void changeColumnType_shouldReturnHttpStatus200_ifCorrectParameters() throws Exception {
        Gson gson = new Gson();
        String typeJson = gson.toJson(DataType.TEXT);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/number/type")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(typeJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void changeColumnType_shouldReturnHttpStatus404_ifNoSuchColumn() throws Exception {
        Gson gson = new Gson();
        String typeJson = gson.toJson(DataType.TEXT);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/someColumn/type")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(typeJson))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void changeColumnType_shouldReturnHttpStatus422_ifColumnCantBeConverted() throws Exception {
        Gson gson = new Gson();
        String typeJson = gson.toJson(DataType.INT);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/table/test/date/type")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(typeJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }
}
