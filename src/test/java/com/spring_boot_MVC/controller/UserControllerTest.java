package com.spring_boot_MVC.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_MVC.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.spring_boot_MVC.converter.UserDtoConverter;
import com.spring_boot_MVC.dto.CreateUserDto;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDtoConverter userDtoConverter;

    @Test
    void checkСreateUser() throws Exception {

        var userDto = new CreateUserDto(
                null,
                "Test",
                "test@email.com",
                25,
                List.of());

        when(userDtoConverter.toUserDtoCreate(any())).thenReturn(userDto);

        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Response: " + response);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.age").value(25));

        assertDoesNotThrow(() -> userService.findUserById(userDto.getId()));
    }
}
