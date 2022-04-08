package com.monolitico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolitico.datos;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.service.ImagenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.util.HashMap;

import static com.monolitico.datos.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ImagenControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private ImagenService imagenService;
    @Mock
    PersonaMapper personaMapper;
    @InjectMocks
    private ImagenController imagenController;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(imagenController).build();
    }

    @Test
    public void createTest() throws Exception{
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        mvc.perform(fileUpload("/apiJpa/imagenes").
                        file(storedImage).
                        param("fk_persona", String.valueOf(datos.crearPersona3().getId())).
                        contentType(mediaType).
                        content(storedImage.getBytes())).
                andExpect(status().isCreated());

//        verify(imagenService,times(1)).saveImagen(storedImage,datos.crearPersona3().getId());
    }
    @Test
    public void findAllTest() throws Exception {
        when(imagenService.findAllImagen()).thenReturn(IMAGENS);
        mvc.perform(get("/apiJpa/imagenes").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(IMAGENS))).
                andDo(MockMvcResultHandlers.print());
        verify(imagenService).findAllImagen();
        verify(imagenService,times(1)).findAllImagen();
    }
    @Test
    public void findByIdTest() throws Exception {
        when(imagenService.findImagenById(crearImagen2().getId())).thenReturn((crearImagen2()));
        mvc.perform(get("/apiJpa/imagenes/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearImagen2())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


//    @Test(expected = RuntimeException.class)
//    /**/public void whenNullResourceIsCreated_thenException() {
//        imagenService.saveImagen(null);
//    }
}