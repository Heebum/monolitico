package com.monolitico.mapper;

import com.monolitico.dto.*;
import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import com.monolitico.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

//    PersonaPostDto personaToPersonaPostDTOs(Persona persona);
//    Persona personaDTOtoPersona(PersonaPostDto personaDto);
//
//    @Mappings({
//            @Mapping(target = "id", source = "id"),
//            @Mapping(target = "nombre", source = "nombre"),
//            @Mapping(target = "apellido", source = "apellido"),
//            @Mapping(target = "ciudad", source = "ciudad")
//    })
//    PersonaGetDto personaToPersonaGetDto(Persona persona);
//    List<PersonaGetDto> personasToPersonaAllDtos(List<Persona> personas);

    PersonaDto toPersonaDTO(Persona persona);
    List<PersonaDto> toPersonaDTOs(List<Persona> personas);
    Persona toPersona(PersonaDto personaDto);

    //Imagen
    ImagenDto ToImagenDto(Imagen imagen);
    Imagen ToImagen(ImagenDto imagenDto);
    List<ImagenDto> ToImagenDtoAll(List<Imagen> imagenList);

    //ImagenMongo
    ImagenMongoMysqlDto ToImagenMongoDto(ImagenMongo imagenMongo);
    ImagenMongo ToImagenMongo(ImagenMongoMysqlDto imagenMongoMysqlDto);
    List<ImagenMongoMysqlDto> ToImgMongoAllDtos(List<ImagenMongo> imagenMongos);



}
//    @Mappings({
//            @Mapping(source = "id",target = "idPersona"),
//            @Mapping(source = "nombre",target = "nombrePersona"),
//            @Mapping(source = "apellido",target = "apellidoPersona"),
//            @Mapping(source = "edad",target = "edadPersona"),
//            @Mapping(source = "ciudad",target = "ciudadPersona")
//    })