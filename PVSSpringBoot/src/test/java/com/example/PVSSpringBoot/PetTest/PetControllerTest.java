package com.example.PVSSpringBoot.PetTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.PVSSpringBoot.ControllerPackage.PetController;
import com.example.PVSSpringBoot.Entities.Pet;
import com.example.PVSSpringBoot.services.PetManagementService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebMvcTest(PetController.class)
public class PetControllerTest {
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    PetController controller;

    @MockBean
    PetManagementService service;


    @Test
    void testGettingAll() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.getAll()).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/all"))
                .andExpect(MockMvcResultMatchers.content().json("[{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"pet\",\n" +
                        "    \"type\": \"cat\",\n" +
                        "    \"breed\": \"persian\",\n" +
                        "    \"description\": null,\n" +
                        "    \"birthDate\": \"2021-04-05\",\n" +
                        "    \"age\": 2,\n" +
                        "    \"imageLink\": null\n" +
                        "}]"));
        Mockito.verify(service,Mockito.times(1)).getAll();

    }

    @Test
    void testAddingPet() throws Exception {
        Pet pet = Pet.builder()
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .build();
        when(service.addPet(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/pet/add")
                        .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"name\":\"pet\"," +
                               "\"type\":\"cat\"," +
                               "\"breed\":\"persian\"," +
                               "\"birthDate\":\"2021-04-05\"}"))
                .andExpect(MockMvcResultMatchers.content().string("your pet is added"));
        verify(service,times(1)).addPet(any());
    }

    @Test
    void testAddingExistantPet() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .build();
        when(service.addPet(any())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/pet/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"pet\",\"type\":\"cat\",\"breed\":\"persian\",\"birthDate\":\"2021-04-05\"}"))
                .andExpect(MockMvcResultMatchers.content().string("Can not add Your Pet"));
        verify(service,times(1)).addPet(any());
    }

    @Test
    void testDeletePet() throws Exception {
        when(service.removePet(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/pet/id=1"))
                .andExpect(MockMvcResultMatchers.content().string("pet is remove successfully"));
        verify(service, times(1)).removePet(1L);
    }
    @Test
    void testDeleteNonExistantPet() throws Exception {
        when(service.removePet(1L)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/pet/id=1"))
                .andExpect(MockMvcResultMatchers.content().string("Can not remove pet"));
        verify(service, times(1)).removePet(1L);
    }

    @Test
    void testFindByID() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.getById(1L)).thenReturn(pet);
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/id=1"))
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}"));
        verify(service,times(1)).getById(1L);
    }

    /**
     * {
     *     "id": 102,
     *     "name": "karim",
     *     "type": "cat",
     *     "breed": "siami",
     *     "description": null,
     *     "birthDate": "2002-04-05",
     *     "age": 21,
     *     "imageLink": null
     * }
     * @throws Exception
     */
    @Test
    void testFindByName() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.getByName("pet")).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/name=pet"))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}]"));
        verify(service,times(1)).getByName("pet");
    }

    @Test
    void testFindByType() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.findByType("cat")).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/type=cat"))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}]"));
        verify(service,times(1)).findByType("cat");
    }

    @Test
    void testFindByBreed() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.findByBreed("persian")).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/breed=persian"))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}]"));
        verify(service,times(1)).findByBreed("persian");
    }

    @Test
    void testFindByage() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.findByAgeYoungerThanOrEqual(4L)).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/age<=4"))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}]"));
        verify(service,times(1)).findByAgeYoungerThanOrEqual(4L);
    }

    @Test
    void testFindByAgeBetween() throws Exception {
        Pet pet = Pet.builder()
                .id(1L)
                .name("pet")
                .type("cat")
                .breed("persian")
                .birthDate(Date.valueOf(LocalDate.of(2021,4,5)))
                .age((short) 2)
                .build();
        when(service.findByAgeBetween(1,3)).thenReturn(List.of(pet));
        mockMvc.perform(MockMvcRequestBuilders.get("/pet/age>=1&&age<=3"))
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1," +
                        "\"name\":\"pet\"," +
                        "\"type\":\"cat\"," +
                        "\"breed\":\"persian\"," +
                        "\"birthDate\":\"2021-04-05\"," +
                        "\"age\":2," +
                        "\"imageLink\":null," +
                        "\"description\":null}]"));
        verify(service,times(1)).findByAgeBetween(1,3);
    }
}
