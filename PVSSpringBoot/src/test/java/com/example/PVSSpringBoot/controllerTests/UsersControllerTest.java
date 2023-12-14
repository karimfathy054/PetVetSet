package com.example.PVSSpringBoot.controllerTests;

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
    void testChangeUserNameOfNonExistantUser() throws Exception{
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
    void testGettingJoinDateOfNonExistantUser() throws Exception{
        when(service.getJoinDate(1L)).thenReturn("Wrong Id!!");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getJoinDate/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Wrong Id!!"));
        verify(service, times(1)).getJoinDate(1L);
    }

    
    
}
