package com.monolitico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monolitico.datos;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.service.ImagenMongoService;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImagenMongoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private ImagenMongoService imagenMongoService;
    @Mock
    PersonaMapper personaMapper;
    @InjectMocks
    private ImagenMongoController imagenMongoController;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders.standaloneSetup(imagenMongoController).build();
    }
    @Test
    public void findAllTest() throws Exception {
        when(imagenMongoService.findAllImagenMongo()).thenReturn(IMAGENMONGO);
        mvc.perform(get("/apiJpa/imgMongo").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(IMAGENS))).
                andDo(MockMvcResultHandlers.print());
        verify(imagenMongoService).findAllImagenMongo();
        verify(imagenMongoService,times(1)).findAllImagenMongo();
    }
    @Test
    public void findByIdTest() throws Exception {
        when(imagenMongoService.findImagenMongoById(crearImagenMongo1().get().get_id())).thenReturn((crearImagenMongo1()));
        mvc.perform(get("/apiJpa/imgMongo/1L")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearImagenMongo1())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void createTest() throws Exception{
        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        mvc.perform(fileUpload("/apiJpa/imgMongo").
                        file(storedImage).
                        param("fk_persona", String.valueOf(datos.crearImagenMongo2().get_id())).
                        contentType(mediaType).
                        content(storedImage.getBytes())).
                andExpect(status().isCreated());

//        verify(imagenService,times(1)).saveImagen(storedImage,datos.crearPersona3().getId());
    }

}