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

//    @GetMapping(value = "/imgMongo", headers = "Accept=application/json")
//    public ResponseEntity<List<ImagenMongoMysqlDto>> getAllImages(@RequestParam(required = false)  String id) {
//        return new ResponseEntity<>(personaMapper.imagesToImagenAllDtos(imagenMongoService.getImgMongo(id)),//personaRepository.findById(id).get()
//                HttpStatus.OK
//        );
//    }
//    @PostMapping("/imgMongo")
//    public ResponseEntity<ImagenMongoMysqlDto> create(@RequestParam("file") MultipartFile imageFile, @RequestParam String fk_persona) throws Exception {
//        System.out.println("Controller "+imageFile.getOriginalFilename());
//        return (ResponseEntity<ImagenMongoMysqlDto>)ResponseEntity.ok().body(personaMapper.imagenToImagenMongoDto(this.imagenMongoService.guardarImgMongo(imageFile,fk_persona)));
//    }
//    @PutMapping("/imgMongo/{id}")
//    public ResponseEntity<ImagenMongoMysqlDto> updateImagen(@PathVariable("id") String id, @RequestParam("file") MultipartFile imageFile, @RequestParam String fk_persona) {
//        return ResponseEntity.ok().body(personaMapper.imagenToImagenMongoDto(this.imagenMongoService.updateImagen(imageFile,fk_persona,id)));
//
//    }
//    @DeleteMapping("/imgMongo/{id}")
//    public ResponseEntity<String> deleteImagen(@PathVariable("id") String id) {
//        imagenMongoService.deleteImagen(id);
//        return ResponseEntity.ok().body("Imagen deleted with success!");
//    }
}