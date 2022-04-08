package com.monolitico.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class PersonaGetDto implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private String ciudad;
}
