package com.monolitico.service;

import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.Imagen;
import com.monolitico.model.Persona;
import com.monolitico.repository.ImagenRepository;
import com.monolitico.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImagenServiceImpl implements ImagenService{
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    ImagenRepository imagenRepository;

    @Override
    public List<Imagen> findAllImagen() {
        List<Imagen> list = imagenRepository.findAll();
//        if (list.isEmpty()){ throw new ResourceNotFoundException("Not found Imagen"); }
        return list;
    }

    @Override
    public Imagen findImagenById(Long id) {
        Imagen imagenById = imagenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Imagen with id = " + id));
        return imagenById;
    }

    @Override
    public Imagen saveImagen(MultipartFile imageFile, Long idPersona) throws IOException {
        Imagen imagen = new Imagen();
        byte[] fileContent = imageFile.getBytes();
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        imagen.setFoto(imageFile.getOriginalFilename());
        Optional<Persona> persona = personaRepository.findById(idPersona);
        if (persona.isEmpty()){
            throw new ResourceNotFoundException("Not found Persona with id = " + idPersona);
        }
        Persona persona1 = persona.get();
        imagen.setPersona(persona1);

        return imagenRepository.save(imagen);
    }

}
