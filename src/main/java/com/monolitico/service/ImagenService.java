package com.monolitico.service;

import com.monolitico.model.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagenService {

    public List<Imagen> findAllImagen();
    public Imagen findImagenById(Long id);
    public Imagen saveImagen(MultipartFile file, Long fk_persona) throws IOException;

}
