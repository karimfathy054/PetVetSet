package com.example.PVSSpringBoot.controllerTests;

import com.example.PVSSpringBoot.Entities.UserFront;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.PVSSpringBoot.ControllerPackage.Controller;
import com.example.PVSSpringBoot.ControllerPackage.RequestService;
import com.example.PVSSpringBoot.repositories.UsersRepo;

@WebMvcTest(Controller.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = Controller.class)
@WithMockUser
public class UsersControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsersRepo repo;

    @MockBean
    RequestService service;

    @Test
    void testChangeUserName() throws Exception{
        Mockito.when(service.changeUserName(1L, "John Smith")).thenReturn("User Saved Successfully!!");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/changeUserName")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":1,\"newName\":\"John Smith\"}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("User Saved Successfully!!"));
        verify(service, times(1)).changeUserName(1L, "John Smith");
    }

    @Test
    void testChangeUserNameOfNonExistentUser() throws Exception{
        Mockito.when(service.changeUserName(1L, "John Smith")).thenReturn("Wrong Id!!");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/changeUserName")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":1,\"newName\":\"John Smith\"}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Wrong Id!!"));
        verify(service, times(1)).changeUserName(1L, "John Smith");
    }

    @Test
    void testGettingJoinDateOfUser() throws Exception{
        when(service.getJoinDate(1L)).thenReturn("2002-04-05");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getJoinDate/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("2002-04-05"));
        verify(service, times(1)).getJoinDate(1L);
    }
    @Test
    void testGettingJoinDateOfNonExistentUser() throws Exception{
        when(service.getJoinDate(1L)).thenReturn("Wrong Id!!");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getJoinDate/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Wrong Id!!"));
        verify(service, times(1)).getJoinDate(1L);
    }

    @Test
    void testAdminSuccessfullyAddingAdmin() throws Exception{
        when(service.setAdmin(0L, 1L)).thenReturn("Admin Access Granted Successfully");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/setAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"admin\":0,\"user\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Admin Access Granted Successfully"));
        verify(service, times(1)).setAdmin(0L, 1L);
    }
    @Test
    void testAdminSuccessfullyRemovingAdmin() throws Exception{
        when(service.removeAdminAccess(0L, 1L)).thenReturn("Admin Access Removed Successfully");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/removeAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"admin\":0,\"user\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Admin Access Removed Successfully"));
        verify(service, times(1)).removeAdminAccess(0L, 1L);
    }
    @Test
    void testGetUserById() throws Exception{
        UserFront user=  UserFront.builder()
                .user_name("Omar Tarek")
                .id(1L)
                .email("Omar@pet.com")
                .is_admin(false).build();
        when(service.getUserById(1)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getUserById/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"user_name\": \"Omar Tarek\",\n" +
                        "    \"email\": \"Omar@pet.com\",\n" +
                        "    \"is_admin\": false\n" +
                        "}"));
        verify(service, times(1)).getUserById(1);
    }
    @Test
    void testGetUserByEmail() throws Exception{
        UserFront user=  UserFront.builder()
                .user_name("Omar Tarek")
                .id(1L)
                .email("Omar@pet.com")
                .is_admin(false).build();
        when(service.getUserByEmail("Omar@pet.com")).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/getUserByEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"email\": \"Omar@pet.com\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"user_name\": \"Omar Tarek\",\n" +
                        "    \"email\": \"Omar@pet.com\",\n" +
                        "    \"is_admin\": false\n" +
                        "}"));
        verify(service, times(1)).getUserByEmail("Omar@pet.com");
    }

    @Test
    void testDeleteUser() throws Exception{
        when(service.deleteUser(0L, 1L)).thenReturn("User Deleted Successfully");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"admin\": \"0\",\n" +
                                "    \"user\": \"1\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("User Deleted Successfully"));
        verify(service, times(1)).deleteUser(0L, 1L);
    }
}
