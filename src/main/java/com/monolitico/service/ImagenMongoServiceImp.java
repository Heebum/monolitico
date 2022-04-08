package com.monolitico.service;

import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.*;
import com.monolitico.repository.ImagenMongoRepository;
import com.monolitico.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImagenMongoServiceImp implements ImagenMongoService{

    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    ImagenMongoRepository imagenMongoRepository;

    @Override
    public List<ImagenMongo> findAllImagenMongo() {
        List<ImagenMongo> list = imagenMongoRepository.findAll();
//        if (list.isEmpty()){ throw new ResourceNotFoundException("Not found Imagen"); }
        return list;
    }

    @Override
    public Optional<ImagenMongo> findImagenMongoById(String id) {
        return Optional.ofNullable((imagenMongoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Imagen with id = " + id))));
    }

    @Override
    public ImagenMongo saveImagenMongo(MultipartFile file, String fk_persona) throws IOException {
        ImagenMongo imagen = new ImagenMongo();
        byte[] fileContent = file.getBytes();
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        imagen.setFoto(encodedString);
        Optional<Persona> persona = personaRepository.findById(Long.valueOf(fk_persona));
        if (persona.isEmpty()){
            throw new ResourceNotFoundException("Not found Persona with id = " + fk_persona);
        }
        Persona persona1 = persona.get();
        imagen.setPersona(persona1);

        return imagenMongoRepository.save(imagen);
    }

}
