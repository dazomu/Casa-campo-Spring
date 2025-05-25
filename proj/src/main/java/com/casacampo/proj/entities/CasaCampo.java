package com.casacampo.proj.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "casacampo")
public class CasaCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_casa;

    private String nombre;
    
    private String direccion;

    private double precio;
    
    private Integer habitaciones;

    private String imagen;

    private boolean disponible;

}

