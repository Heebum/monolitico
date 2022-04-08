package com.monolitico;

import com.monolitico.dto.PersonaDto;
import com.monolitico.model.Imagen;
import com.monolitico.model.ImagenMongo;
import com.monolitico.model.Persona;
import org.bson.types.ObjectId;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class datos {
    public final static List<Persona> PERSONAS = Arrays.asList(
            new Persona(2L,"stefanie","cortina","29","soledad"),
            new Persona(3L,"alice","cortina","4","barranquilla"),
            new Persona(4L,"ana","marquez","43","puerto colombia"));

    public static Optional<Persona> crearPersona1() {
        return Optional.of(new Persona(2L,"stefanie","cortina","29","soledad"));
    }
    public static Optional<Persona> crearPersona2() {
        return Optional.of(new Persona(3L,"alice","cortina","4","barranquilla"));
    }
    public static Persona crearPersona3() {
        return new Persona(3L,"alice","cortina","4","barranquilla");
    }

    public static PersonaDto crearPersonaDto() {
        PersonaDto dto = new PersonaDto();
        dto.setNombre(crearPersona3().getNombre());
        dto.setApellido(crearPersona3().getApellido());
        dto.setCiudad(crearPersona3().getCiudad());
        dto.setEdad(crearPersona3().getEdad());
        return dto;
    }


    public static Optional<Imagen> crearImagen1() {
        return Optional.of(new Imagen(1L,"foto2",crearPersona3()));
    }
    public static Imagen crearImagen2() {
        return new Imagen(3L,"foto3",crearPersona3());
    }





    public final static List<Imagen> IMAGENS = Arrays.asList(
            new Imagen(2L,"foto2",crearPersona3()),
            new Imagen(3L,"foto3",crearPersona3()),
            new Imagen(4L,"foto4",crearPersona3()));

    static ObjectId id = ObjectId.get();
    public static Optional<ImagenMongo> crearImagenMongo1() {
        return Optional.of(new ImagenMongo("1L","foto2",crearPersona3()));
    }
    public static ImagenMongo crearImagenMongo2() {
        return new ImagenMongo("1L","foto3",crearPersona3());
    }

    public final static List<ImagenMongo> IMAGENMONGO = Arrays.asList(
            new ImagenMongo("2L","foto2",crearPersona3()),
            new ImagenMongo("3L","foto3",crearPersona3()),
            new ImagenMongo("4L","foto4",crearPersona3()));


}
