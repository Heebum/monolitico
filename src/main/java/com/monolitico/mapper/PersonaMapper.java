package com.monolitico.mapper;

import com.monolitico.dto.*;
import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import com.monolitico.model.Persona;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

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