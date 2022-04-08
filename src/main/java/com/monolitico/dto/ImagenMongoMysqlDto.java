package com.monolitico.dto;

import lombok.Data;

@Data
public class ImagenMongoMysqlDto {
    private String _id;
    private String foto;
    private PersonaDto persona;

}
