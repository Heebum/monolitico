package com.monolitico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolitico.datos;
import com.monolitico.dto.PersonaDto;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.model.Persona;
import com.monolitico.service.PersonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.monolitico.datos.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)

@ExtendWith(MockitoExtension.class)
class PersonaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private PersonaService personaService;
    @Mock
    PersonaMapper personaMapper;
    @InjectMocks
    private PersonaController personaController;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(personaController).build();
    }

    @Test
    public void createTest() throws Exception{
        PersonaDto dto = new PersonaDto();
        dto.setNombre("frank");
        dto.setApellido("castro");
        dto.setCiudad("malambo");
        dto.setEdad("32");

        mvc.perform(post("/apiJpa/personasDto").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(dto))).
                        andExpect(status().isCreated());
        verify(personaService,times(1)).savePersona(any());
    }
    @Test
    public void findAllTest() throws Exception {
        when(personaService.findAllPersona()).thenReturn(PERSONAS);
        mvc.perform(get("/apiJpa/personasDto").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(PERSONAS))).
                        andDo(print());
        verify(personaService).findAllPersona();
        verify(personaService,times(1)).findAllPersona();
    }
    @Test
    public void findByIdTest() throws Exception {
        when(personaService.findPersonaById(crearPersona3().getId())).thenReturn(Optional.of(crearPersona3()));
        mvc.perform(get("/apiJpa/personasDto/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearPersona3())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Test
    public void updateTest() throws Exception{
        Persona person = new Persona();
        person.setId(3L);
        person.setNombre("frank");
        person.setApellido("castro");
        person.setCiudad("malambo");
        person.setEdad("32");

        mvc.perform(post("/apiJpa/personasDto").
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(person))).
                andExpect(status().isCreated());
        verify(personaService,times(1)).savePersona(any());
    }
    @Test
    public void update2Test() throws Exception{
        Persona person = new Persona();
        person.setId(2L);
        person.setNombre("frank");
        person.setApellido("castro");
        person.setCiudad("malambo");
        person.setEdad("32");
        when(personaService.updatePersona(any(),anyLong())).thenReturn(datos.crearPersona1().get());
        mvc.perform(put("/apiJpa/personasDto/"+person.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
        verify(personaService,times(1)).updatePersona(any(),anyLong());
    }
    @Test
    public void deleteTest() throws Exception {
        when(personaService.deletePersonaById(crearPersona3().getId())).thenReturn(crearPersona3());
        mvc.perform(delete("/apiJpa/personasDto/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearPersona3())))
                .andExpect(MockMvcResultMatchers.status().isAccepted()).andDo(print());
    }

}