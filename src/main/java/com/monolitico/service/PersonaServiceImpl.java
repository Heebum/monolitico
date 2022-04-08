package com.monolitico.service;

import com.monolitico.exception.Generated;
import com.monolitico.exception.ResourceNotFoundException;
import com.monolitico.model.Persona;
import com.monolitico.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService{
    @Autowired
    PersonaRepository personaRepository;
    @Override
    public List<Persona> findAllPersona() {
        List<Persona> list = personaRepository.findAll();
//        if (list.isEmpty()){ throw new ResourceNotFoundException("Not found Persona"); }
        return list;
    }
    @Override
    public Optional<Persona> findPersonaById(Long id) {
        Optional<Persona> personaById = Optional.ofNullable(personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Persona with id = " + id)));
        return personaById;
    }
    @Override
    public Persona savePersona(Persona persona){
        return personaRepository.save(persona);
    }

    @Generated
    @Override
    public Persona updatePersona(Persona personaDto,Long id) {
        Optional<Persona> person = personaRepository.findById(id);//.orElseThrow(() -> new ResourceNotFoundException("Not found Persona with id = " + id));
        if (person.isEmpty()) {
            throw new ResourceNotFoundException("Not found Persona with id = " + id);
        }
        Persona personaUdt = person.get();
        System.out.println("personaUdt "+personaUdt);

        personaUdt.setNombre(personaDto.getNombre());
        personaUdt.setApellido(personaDto.getApellido());
        personaUdt.setCiudad(personaDto.getCiudad());
        personaUdt.setEdad(personaDto.getEdad());

        personaRepository.save(personaUdt);
        System.out.println("personaUdt "+personaUdt);
        return personaUdt;
    }

    @Generated
    @Override
    public Persona deletePersonaById(Long id) {
        Optional optional = personaRepository.findById(id);
        if (optional.isEmpty()){
            throw new ResourceNotFoundException("Not found Persona with id = " + id);
        }
        Persona persona = personaRepository.findById(id).get();
        personaRepository.deleteById(id);
        return persona;
    }

//    @Override
//    public List<Persona> getPersona(Long id) {
//        List<Persona> list = new ArrayList<Persona>( personaRepository.findAll().size() );
//        if (id==null){
//            personaRepository.findAll().forEach(list::add);
//        }else {
//            personaRepository.findById(id).stream().forEach(list::add);
//        }
//        if (list.isEmpty()) {
//            throw new ResourceNotFoundException("Not found Persona with id = " + id);
//        }
//        return list;
//    }
//
//    @Override
//    public Persona createPersona(Persona persona) {
//        return personaRepository.save(persona);
//    }
//
//
//    @Override
//    public Persona updatePersona(Persona personaDto, Long id) {
//        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Persona with id = " + id));
//        return personaRepository.save(personaDto);
//    }
//    @Override
//    public void deleteById(Long id) {
//        Persona persona = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Persona with id = " + id));
//        personaRepository.deleteById(id);
//    }
//
//    public List<Persona> listAllPersonas() {
//        List<Persona> list = new ArrayList<Persona>( personaRepository.findAll().size() );
//        for (Persona persona : personaRepository.findAll()) {
//            list.add(persona);
//        }
//        if (list.isEmpty()) {
//            throw new ResourceNotFoundException("Not found Persona");
//        }
//        return personaRepository.findAll();
//    }
//    @Override
//    public Optional<Persona> findById(Long id) {
//        return personaRepository.findAll()
//                .stream()
//                .filter(e -> e.getId().equals(id))
//                .findFirst();
//    }

}