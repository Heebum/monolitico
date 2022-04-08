package com.monolitico.service;

import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImagenMongoService {

    public List<ImagenMongo> findAllImagenMongo();
    public Optional<ImagenMongo> findImagenMongoById(String id);
    public ImagenMongo saveImagenMongo(MultipartFile file, String fk_persona) throws IOException;

//    public List<ImagenMongo> getImgMongo(String id);
//    public ImagenMongo guardarImgMongo(MultipartFile file, String fk_persona) throws IOException;
//    public ImagenMongo updateImagen(MultipartFile file, String fk_persona, String id);
//    public void deleteImagen(String id);
}
