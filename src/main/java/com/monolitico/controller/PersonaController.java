package com.monolitico.controller;

import com.monolitico.dto.PersonaDto;
import com.monolitico.exception.Generated;
import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.mapper.PersonaMapper;
import com.monolitico.model.Persona;
import com.monolitico.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/apiJpa/personasDto")
public class PersonaController {
    @Autowired
    PersonaService personaService;
    @Autowired
    PersonaMapper personaMapper;

    @GetMapping
    public ResponseEntity<List<PersonaDto>> findAll() {
        return ResponseEntity.ok(personaMapper.toPersonaDTOs(personaService.findAllPersona()));
    }

    @PostMapping
    public ResponseEntity<PersonaDto> create(@RequestBody PersonaDto productDTO) {
        personaService.savePersona(personaMapper.toPersona(productDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDto> findById(@PathVariable Long id) {
        Optional<Persona> product = personaService.findPersonaById(id);
        return ResponseEntity.ok(personaMapper.toPersonaDTO(product.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDto> update(@PathVariable Long id, @RequestBody PersonaDto personaDto) {
        PersonaDto current = personaMapper.toPersonaDTO(personaService.updatePersona(personaMapper.toPersona(personaDto),id));
        return ResponseEntity.status(HttpStatus.OK).body(current);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaDto> delete(@PathVariable Long id) {
        PersonaDto deletedPersona = personaMapper.toPersonaDTO(personaService.deletePersonaById(id));
        ResponseEntity responseEntity = new ResponseEntity<PersonaDto>(deletedPersona, HttpStatus.ACCEPTED);
        return responseEntity;
    }


}