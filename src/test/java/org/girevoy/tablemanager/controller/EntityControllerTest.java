package org.girevoy.tablemanager.controller;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.girevoy.tablemanager.model.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.String.format;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/initTestDB.sql"})
public class EntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String TEST_TABLE_NAME = "test_table";

    @Test
    public void insertEntity_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Entity entity = new Entity();
        entity.setTableName(TEST_TABLE_NAME);

        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("name", "Zzz");
        attributes.put("date", "2021-05-31");
        attributes.put("number", 10);
        entity.setAttributes(attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.post(format("/%s/insertEntity", TEST_TABLE_NAME))
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void insertEntity_shouldReturnHttpStatus422_ifIncorrectRequestBody() throws Exception {
        Entity entity = new Entity();
        entity.setTableName(TEST_TABLE_NAME);

        Map<String, Object> attributes = new HashMap<>();

        String incorrectColumnName = "someParam";

        attributes.put(incorrectColumnName, "2021-05-31");
        attributes.put("name", "Zzz");
        attributes.put("number", 10);
        entity.setAttributes(attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.post(format("/%s/insertEntity", TEST_TABLE_NAME))
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void insertEntity_shouldReturnHttpStatus400_ifRequestBodyHasEmptyFields() throws Exception {
        Entity emptyEntity = new Entity();

        Gson gson = new Gson();
        String entityJson = gson.toJson(emptyEntity);

        mockMvc.perform(MockMvcRequestBuilders.post(format("/%s/insertEntity", TEST_TABLE_NAME))
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus200_ifCorrectVariables() throws Exception {
        String existingEntityId = "1";

        mockMvc.perform(MockMvcRequestBuilders.delete(format("/%s/deleteEntity/%s", TEST_TABLE_NAME,
                existingEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus400_ifIncorrectParameters() throws Exception {
        String incorrectEntityId = "0";

        mockMvc.perform(MockMvcRequestBuilders.delete(format("/%s/deleteEntity/%s", TEST_TABLE_NAME,
                incorrectEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        String notExistingEntityId = "10";

        mockMvc.perform(MockMvcRequestBuilders.delete(format("/%s/deleteEntity/%s", TEST_TABLE_NAME,
                notExistingEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus200_ifCorrectRequestBody() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        int existingEntityId = 1;
        String newText = "Xxx";
        int newNumber = 20;
        String newDate = "2021-05-30";

        attributes.put("date", newDate);
        attributes.put("name", newText);
        attributes.put("number", newNumber);
        Entity entity = new Entity(existingEntityId, TEST_TABLE_NAME, attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/%s/updateEntity/%s", TEST_TABLE_NAME,
                existingEntityId))
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus400_ifRequestBodyHasEmptyFields() throws Exception {
        Entity entity = new Entity();

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        String existingEntityId = "1";

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/%s/updateEntity/%s", TEST_TABLE_NAME,
                existingEntityId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus400_ifIdIs0() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        int existingEntityId = 1;
        String newText = "Xxx";
        int newNumber = 20;
        String newDate = "2021-05-30";

        attributes.put("date", newDate);
        attributes.put("name", newText);
        attributes.put("number", newNumber);
        Entity entity = new Entity(existingEntityId, TEST_TABLE_NAME, attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        String incorrectEntityId = "0";

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/%s/updateEntity/%s", TEST_TABLE_NAME,
                incorrectEntityId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus422_ifIdIs0() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        int existingEntityId = 1;
        String newText = "Xxx";
        int newNumber = 20;
        String newDate = "2021-05-30";
        String incorrectParameterName = "some_name";

        attributes.put(incorrectParameterName, newDate);
        attributes.put("name", newText);
        attributes.put("number", newNumber);
        Entity entity = new Entity(existingEntityId, TEST_TABLE_NAME, attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/%s/updateEntity/%s", TEST_TABLE_NAME,
                existingEntityId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        int notExistingEntityId = 10;
        String newText = "Xxx";
        int newNumber = 20;
        String newDate = "2021-05-30";

        attributes.put("date", newDate);
        attributes.put("name", newText);
        attributes.put("number", newNumber);
        Entity entity = new Entity(notExistingEntityId, TEST_TABLE_NAME, attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch(format("/%s/updateEntity/%s", TEST_TABLE_NAME,
                notExistingEntityId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void findEntityById_shouldReturnHttpStatus200_ifRequestIsCorrect() throws Exception {
        String existingEntityId = "1";

        mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findEntityById/%s", TEST_TABLE_NAME,
                existingEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void findEntityById_shouldReturnEntity_ifRequestIsCorrect() throws Exception {
        Map<String, Object> attributes = new HashMap<>();

        int existingEntityId = 1;

        attributes.put("date", "2021-01-05");
        attributes.put("name", "Aaaa");
        attributes.put("number", 10);

        Entity expectedEntity = new Entity(existingEntityId, TEST_TABLE_NAME, attributes);

        Gson gson = new Gson();
        String expectedEntityJson = gson.toJson(expectedEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findEntityById/%s",
                TEST_TABLE_NAME, existingEntityId)))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(expectedEntityJson, response);
    }

    @Test
    public void findEntityById_shouldReturnHttpStatus400_ifIdIs0() throws Exception {
        String incorrectEntityId = "0";

        mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findEntityById/%s",
                TEST_TABLE_NAME, incorrectEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void findEntityById_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        String notExistingEntityId = "10";

        mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findEntityById/%s",
                TEST_TABLE_NAME, notExistingEntityId)))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void findAllEntities_shouldReturnHttpStatus200_ifCorrectRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findAllEntities", TEST_TABLE_NAME)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void findAllEntities_shouldReturnHttpStatus400_ifNoSuchTable() throws Exception {
        String notExistingTableName = "someTable";

        mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findAllEntities", notExistingTableName)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void findAllEntities_shouldReturnListOfEntities_ifCorrectRequest() throws Exception {
        Map<String, Object> attributes1 = new LinkedHashMap<>();
        attributes1.put("date", "2021-01-05");
        attributes1.put("number", 10);
        attributes1.put("name", "Aaaa");

        Map<String, Object> attributes2 = new LinkedHashMap<>();
        attributes2.put("date", "2021-02-05");
        attributes2.put("number", 11);
        attributes2.put("name", "Bbbb");

        Map<String, Object> attributes3 = new LinkedHashMap<>();
        attributes3.put("date", "2021-03-05");
        attributes3.put("number", 12);
        attributes3.put("name", "Cccc");

        Map<String, Object> attributes4 = new LinkedHashMap<>();
        attributes4.put("date", "2021-04-05");
        attributes4.put("number", 13);
        attributes4.put("name", "Dddd");

        Map<String, Object> attributes5 = new LinkedHashMap<>();
        attributes5.put("date", "2021-05-05");
        attributes5.put("number", 14);
        attributes5.put("name", "Eeee");

        List<Entity> expectedList = Arrays.asList(
                new Entity(1, TEST_TABLE_NAME, attributes1),
                new Entity(2, TEST_TABLE_NAME, attributes2),
                new Entity(3, TEST_TABLE_NAME, attributes3),
                new Entity(4, TEST_TABLE_NAME, attributes4),
                new Entity(5, TEST_TABLE_NAME, attributes5));

        Gson gson = new Gson();
        String expectedListJson = gson.toJson(expectedList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(format("/%s/findAllEntities", TEST_TABLE_NAME)))
                .andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(expectedListJson, response);
    }
}
