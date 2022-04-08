package com.monolitico.service;

import com.monolitico.dto.PersonaDto;
import com.monolitico.exception.Generated;
import com.monolitico.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    public List<Persona> findAllPersona();
    public Optional<Persona> findPersonaById(Long id);
    public Persona savePersona(Persona persona);
    public Persona deletePersonaById(Long id);
    public Persona updatePersona(Persona persona,Long id);

//    public List<Persona> getPersona(Long id);
//    public Persona createPersona(Persona persona);
//    public Persona updatePersona(Persona persona, Long id);
//    public void deleteById(Long id);
//    public List<Persona> listAllPersonas();
//    public Optional<Persona> findById(Long id);
}
