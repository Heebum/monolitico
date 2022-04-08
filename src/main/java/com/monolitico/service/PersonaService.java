package com.monolitico.service;

import com.monolitico.model.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    public List<Persona> findAllPersona();
    public Optional<Persona> findPersonaById(Long id);
    public Persona savePersona(Persona persona);
    public Persona deletePersonaById(Long id);
    public Persona updatePersona(Persona persona,Long id);
}
