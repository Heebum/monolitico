package com.monolitico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foto;

    @ManyToOne//(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_personas", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private Persona persona;
}
