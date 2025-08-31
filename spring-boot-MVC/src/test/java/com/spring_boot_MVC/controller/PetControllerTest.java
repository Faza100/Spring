package com.spring_boot_MVC.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_MVC.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.spring_boot_MVC.converter.PetDtoConverter;
import com.spring_boot_MVC.dto.PetDto;
import com.spring_boot_MVC.model.Pet;
import com.spring_boot_MVC.service.PetService;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @MockBean
    private PetDtoConverter petDtoConverter;

    @Test
    void checkCreatePet() throws Exception {

        Long ownerId = 1L;
        var petDto = new PetDto(
                null,
                "Test",
                null);

        Pet petEntity = new Pet(
                null,
                "Test",
                null);

        when(petDtoConverter.toPet(any(PetDto.class))).thenReturn(petEntity);
        when(petDtoConverter.toPetDto(any())).thenReturn(petDto);

        mockMvc.perform(post("/api/pets/{userId}", ownerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.userId").doesNotExist());

        assertDoesNotThrow(() -> petService.findPetById(petDto.getId()));
    }
}
