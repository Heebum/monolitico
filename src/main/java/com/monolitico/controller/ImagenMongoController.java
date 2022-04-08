package com.monolitico.controller;

import com.monolitico.dto.ImagenDto;
import com.monolitico.dto.ImagenMongoMysqlDto;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import com.monolitico.service.ImagenMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/apiJpa/imgMongo")
public class ImagenMongoController {
    @Autowired
    ImagenMongoService imagenMongoService;
    @Autowired
    PersonaMapper personaMapper;

    @GetMapping
    public ResponseEntity<List<ImagenMongoMysqlDto>> findAll() {
        return ResponseEntity.ok(personaMapper.ToImgMongoAllDtos(imagenMongoService.findAllImagenMongo()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImagenMongoMysqlDto> findById(@PathVariable String id) {
        Optional<ImagenMongo> imagen = imagenMongoService.findImagenMongoById(id);
        return ResponseEntity.ok(personaMapper.ToImagenMongoDto(imagen.get()));
    }
    @PostMapping
    public ResponseEntity<ImagenMongoMysqlDto> create(@RequestParam("file") MultipartFile imageFile, @RequestParam String fk_persona) throws IOException {
        ImagenMongoMysqlDto dto = personaMapper.ToImagenMongoDto(imagenMongoService.saveImagenMongo(imageFile,fk_persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}