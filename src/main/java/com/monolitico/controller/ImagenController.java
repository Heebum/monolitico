package com.monolitico.controller;

import com.monolitico.dto.ImagenDto;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.model.Imagen;
import com.monolitico.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apiJpa/imagenes")
public class ImagenController {
    @Autowired
    ImagenService imagenService;
    @Autowired
    PersonaMapper personaMapper;

    @GetMapping
    public ResponseEntity<List<ImagenDto>> findAll() {
        return ResponseEntity.ok(personaMapper.ToImagenDtoAll(imagenService.findAllImagen()));
    }

    @PostMapping
    public ResponseEntity<ImagenDto> create(@RequestParam("file") MultipartFile imageFile, @RequestParam Long fk_persona) throws IOException {
        ImagenDto dto = personaMapper.ToImagenDto(imagenService.saveImagen(imageFile,fk_persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImagenDto> findById(@PathVariable Long id) {
        Imagen imagen = imagenService.findImagenById(id);
        return ResponseEntity.ok(personaMapper.ToImagenDto(imagen));
    }

}