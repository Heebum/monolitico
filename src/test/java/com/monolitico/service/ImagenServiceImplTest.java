package com.monolitico.service;

import com.monolitico.datos;
import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.Imagen;
import com.monolitico.model.Persona;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.repository.PersonaRepository;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class ImagenServiceImplTest {


    @InjectMocks
    ImagenServiceImpl imagenService;
    @Mock
    PersonaRepository personaRepository;
    @Mock
    ImagenRepository imagenRepository;


    @Test
    void findImagenByIdTest(){
        when(imagenRepository.findById(anyLong())).thenReturn(datos.crearImagen1());

        Imagen actual =  imagenService.findImagenById(2L);

        assertEquals(1L, actual.getId());
        assertEquals("foto2", actual.getFoto());
        verify(imagenRepository, atLeastOnce()).findById(anyLong());
    }
    @Test
    void exceptionTest(){
        Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
            imagenService.findImagenById(20L);
        }, ()-> "Error "+ResourceNotFoundException.class.getSimpleName());
        assertEquals("Not found Imagen with id = 20", exception.getMessage());
    }

    @Test
    void saveImagenTest() throws IOException {
        Imagen imagen = new Imagen(null, "foto3",datos.crearPersona3());
        when(imagenRepository.save(any())).then(invocation ->{
            Imagen c = invocation.getArgument(0);
            c.setId(5L);
            return c;
        });
        when(personaRepository.findById(3L)).thenReturn(Optional.of(datos.crearPersona3()));

        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);
        Long id = datos.crearPersona3().getId();
        Imagen cuenta = imagenService.saveImagen(storedImage,id);

        assertNotNull(cuenta);
        assertEquals("foto3", cuenta.getFoto());
        assertEquals(5, cuenta.getId());
        assertEquals(3, cuenta.getPersona().getId());
        assertThrows(ResourceNotFoundException.class, ()->{
            imagenService.saveImagen(storedImage,60L);
        });

        verify(imagenRepository).save(any());
        verify(imagenRepository,times(1)).save(any());

    }
    @Test
    void findAllPersonaTest() {
        when(imagenRepository.findAll()).thenReturn(datos.IMAGENS);

        List<Imagen> imagens = imagenService.findAllImagen();

        assertFalse(imagens.isEmpty());
        assertEquals(3, imagens.size());
        assertFalse(imagens.contains(datos.crearPersona2().orElseThrow()));
        assertEquals(3L, imagens.get(1).getId());
        assertEquals("foto2", imagens.get(0).getFoto());
        assertThat(imagens.get(0).getFoto(), is(equalTo("foto2")));

//        assertThrows(ResourceNotFoundException.class, ()->{
//            imagenService.findAllImagen().;
//        });

        verify(imagenRepository,atLeastOnce()).findAll();
        verify(imagenRepository,times(1)).findAll();
    }



}