package com.monolitico.service;

import com.monolitico.datos;
import com.monolitico.exception.ControllerExceptionHandler;
import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.Persona;
import com.monolitico.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class PersonaServiceImplTest {

    @InjectMocks
    PersonaServiceImpl personaService;
    @Mock
    PersonaRepository personaRepository;

    @Test
    void findPersonaByIdTest(){
        when(personaRepository.findById(anyLong())).thenReturn(datos.crearPersona1());

        Optional<Persona> actual =  personaService.findPersonaById(2L);

        assertEquals(2L, actual.orElseThrow().getId());
        assertEquals("stefanie", actual.orElseThrow().getNombre());
        verify(personaRepository).findById(anyLong());
        //verify(personaRepository).findAll();
    }

    @Test
    void savePersonaTest() {
        Persona persona = new Persona(5L,"ana","marquez","43","puerto colombia");// new Persona(null, "ana","marquez","43","puerto colombia");
        when(personaRepository.save(any())).then(invocation ->{
            Persona c = invocation.getArgument(0);
            c.setId(5L);
            return c;
        });

        Persona cuenta = personaService.savePersona(persona);

        assertEquals("ana", cuenta.getNombre());
        assertEquals(5, cuenta.getId());
        assertEquals("puerto colombia", cuenta.getCiudad());

        verify(personaRepository).save(any());
        verify(personaRepository,times(1)).save(any());

    }

    @Test
    void findAllPersonaTest() {
//        List<Persona> datoss = Arrays.asList(datos.crearPersona1().orElseThrow(), datos.crearPersona2().orElseThrow());
        when(personaRepository.findAll()).thenReturn(datos.PERSONAS);

        List<Persona> personas = personaService.findAllPersona();

        assertFalse(personas.isEmpty());
        assertEquals(3, personas.size());
        assertFalse(personas.contains(datos.crearPersona2().orElseThrow()));
        assertEquals(3L, personas.get(1).getId());
        assertEquals("stefanie", personas.get(0).getNombre());
        assertThat(personas.get(0).getNombre(), is(equalTo("stefanie")));

        verify(personaRepository,atLeastOnce()).findAll();
        verify(personaRepository,times(1)).findAll();
    }
    @Test
    public void ListAllPersonTest(){
        personaRepository.save(datos.crearPersona3());
        //stubbing mock to return specific data
        when(personaRepository.findAll()).thenReturn(datos.PERSONAS);
        List<Persona> productList1 =personaService.findAllPersona();
        assertEquals(productList1,datos.PERSONAS);
        verify(personaRepository,times(1)).save(any());
        verify(personaRepository,times(1)).findAll();
    }


    @Test
    void updatePersonaTest() {
        Persona newPersona =  datos.crearPersona3();
        when(personaService.savePersona(any(Persona.class))).then(new Answer<Persona>(){
            Long secuencia = 8L;
            @Override
            public Persona answer(InvocationOnMock invocationOnMock) throws Throwable {
                Persona persona = invocationOnMock.getArgument(0);
                persona.setId(secuencia++);
                return persona;
            }
        });
        Persona persona = personaService.savePersona(newPersona);
        assertNotNull(persona.getId());
        assertEquals(8L, persona.getId());
        assertEquals("alice", persona.getNombre());
        assertThrows(ResourceNotFoundException.class, ()->{
            personaService.findPersonaById(60L);
        });

        verify(personaRepository).save(any(Persona.class));
    }
    @Test
    void exceptionTest(){
        Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
            personaService.findPersonaById(20L);
        }, ()-> "Error "+ResourceNotFoundException.class.getSimpleName());
        assertEquals("Not found Persona with id = 20", exception.getMessage());
    }
}