package com.monolitico.service;

import com.monolitico.model.Imagen;
import com.monolitico.model.Persona;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImagenService {

    public List<Imagen> findAllImagen();
    public Imagen findImagenById(Long id);
    public Imagen saveImagen(MultipartFile file, Long fk_persona) throws IOException;






//    public Imagen guardarImg(MultipartFile file, Long fk_persona) throws IOException;
//    public List<Imagen> getImagen(Long id);
//    public Imagen updateImagen(MultipartFile file, Long fk_persona, Long id);
//    public void deleteImagen(Long id);

}
