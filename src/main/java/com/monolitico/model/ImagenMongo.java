package com.monolitico.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "images")
@AllArgsConstructor
@NoArgsConstructor
public class ImagenMongo {
    @Id
    private String _id;
    private String foto;
    private Persona persona;

}
