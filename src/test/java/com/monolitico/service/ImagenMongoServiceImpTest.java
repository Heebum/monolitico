package com.monolitico.service;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClients;
import com.monolitico.datos;
import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import com.monolitico.repository.ImagenMongoRepository;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.repository.PersonaRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
//@DataMongoTest
class ImagenMongoServiceImpTest {
    @InjectMocks
    ImagenMongoServiceImp imagenMongoService;
    @Mock
    PersonaRepository personaRepository;
    @Mock
    ImagenMongoRepository imagenMongoRepository;


    @Test
    void findImgMongoById(){
        ObjectId id = ObjectId.get();
        when(imagenMongoRepository.findById("1L")).thenReturn(Optional.of(datos.crearImagenMongo2()));

        Optional<ImagenMongo> actual =  imagenMongoService.findImagenMongoById("1L");

        assertEquals("1L", actual.get().get_id());
        assertEquals("foto3", actual.orElseThrow().getFoto());

        assertThrows(ResourceNotFoundException.class, ()->{
            imagenMongoService.findImagenMongoById("3L");

        });

        verify(imagenMongoRepository, atLeastOnce()).findById(anyString());
    }
    @Test
    void findAllImgMongoTest() {
//        ObjectId id = ObjectId.get();
        when(imagenMongoRepository.findAll()).thenReturn(datos.IMAGENMONGO);

        List<ImagenMongo> imagens = imagenMongoService.findAllImagenMongo();

        assertFalse(imagens.isEmpty());
        assertEquals(3, imagens.size());
        assertFalse(imagens.contains(datos.crearPersona2().orElseThrow()));
        assertEquals("3L", imagens.get(1).get_id());
        assertEquals("foto2", imagens.get(0).getFoto());
        assertThat(imagens.get(0).getFoto(), is(equalTo("foto2")));

        verify(imagenMongoRepository,atLeastOnce()).findAll();
        verify(imagenMongoRepository,times(1)).findAll();
    }
    @Test
    void saveImagenTest() throws IOException {
        ImagenMongo imagen = new ImagenMongo(null, "foto3",datos.crearPersona3());
        when(imagenMongoRepository.save(any())).then(invocation ->{
            ImagenMongo c = invocation.getArgument(0);
            c.set_id("5L");
            return c;
        });
        when(personaRepository.findById(3L)).thenReturn(Optional.of(datos.crearPersona3()));

        FileInputStream fis = new FileInputStream("C:\\Users\\stefanie.cortina\\Pictures\\spring\\img1.png");
        MockMultipartFile storedImage = new MockMultipartFile("file","foto3","image/jpeg",fis);

        ImagenMongo cuenta = imagenMongoService.saveImagenMongo(storedImage, String.valueOf(datos.crearPersona3().getId()));

        assertNotNull(cuenta);
//        assertEquals("foto3", cuenta.getFoto());
        assertEquals("5L", cuenta.get_id());
        assertEquals(3, cuenta.getPersona().getId());
        assertThrows(ResourceNotFoundException.class, ()->{
            imagenMongoService.saveImagenMongo(storedImage, String.valueOf(60L));
        });

        verify(imagenMongoRepository).save(any());
        verify(imagenMongoRepository,times(1)).save(any());
    }
}