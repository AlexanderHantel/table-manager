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

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql({"/initTestDB.sql"})
public class EntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void insertEntity_shouldReturnHttpStatusOk_ifCorrectRequestBody() throws Exception {
        Entity entity = new Entity();
        entity.setTableName("test");

        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("name", "Zzz");
        attributes.put("date", "2021-05-31");
        attributes.put("number", 10);
        entity.setAttributes(attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void insertEntity_shouldReturnHttpStatus422_ifIncorrectRequestBody() throws Exception {
        Entity entity = new Entity();
        entity.setTableName("test");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("someParam", "2021-05-31");
        attributes.put("name", "Zzz");
        attributes.put("number", 10);
        entity.setAttributes(attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void insertEntity_shouldReturnHttpStatus400_ifRequestBodyHasEmptyFields() throws Exception {
        Entity entity = new Entity();

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus200_ifCorrectVariables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/test/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus400_ifIncorrectParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/test/0"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deleteEntity_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/test/10"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus200_ifCorrectRequestBody() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("date", "2021-05-30");
        attributes.put("name", "Xxx");
        attributes.put("number", 20);
        Entity entity = new Entity(1, "test", attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/test/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus400_ifRequestBodyHasEmptyFields() throws Exception {
        Entity entity = new Entity();

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/test/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus400_ifIdIs0() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("date", "2021-05-30");
        attributes.put("name", "Xxx");
        attributes.put("number", 20);
        Entity entity = new Entity(1, "test", attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/test/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus422_ifIdIs0() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("someName", "2021-05-30");
        attributes.put("name", "Xxx");
        attributes.put("number", 20);
        Entity entity = new Entity(1, "test", attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/test/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void updateEntity_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("date", "2021-05-30");
        attributes.put("name", "Xxx");
        attributes.put("number", 20);
        Entity entity = new Entity(10, "test", attributes);

        Gson gson = new Gson();
        String entityJson = gson.toJson(entity);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/test/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(entityJson))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void findById_shouldReturnHttpStatus200_ifRequestIsCorrect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void findById_shouldReturnEntity_ifRequestIsCorrect() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("date", "2021-01-05");
        attributes.put("name", "Aaaa");
        attributes.put("number", 10);
        Entity expectedEntity = new Entity(1, "test", attributes);

        Gson gson = new Gson();
        String expectedEntityJson = gson.toJson(expectedEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/test/1")).andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(expectedEntityJson, response);
    }

    @Test
    public void findById_shouldReturnHttpStatus400_ifIdIs0() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/0"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void findById_shouldReturnHttpStatus404_ifNoSuchId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/10"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void findAll_shouldReturnHttpStatus200_ifCorrectRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/all"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void findAll_shouldReturnHttpStatus400_ifNoSuchTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/someName/all"))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void findAll_shouldReturnListOfEntities_ifCorrectRequest() throws Exception {
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
                new Entity(1, "test", attributes1),
                new Entity(2, "test", attributes2),
                new Entity(3, "test", attributes3),
                new Entity(4, "test", attributes4),
                new Entity(5, "test", attributes5));

        Gson gson = new Gson();
        String expectedListJson = gson.toJson(expectedList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/test/all")).andReturn();
        String response = result.getResponse().getContentAsString();

        assertEquals(response, expectedListJson);
    }

}
