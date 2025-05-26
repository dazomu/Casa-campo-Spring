package com.casacampo.proj.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    private String nombre;
    
    @Column(unique = true)
    private String correo;

    private String contrasenia;

    private String rol; // Ejemplo: "ADMIN", "CLIENTE"

   

}
