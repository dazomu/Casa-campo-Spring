package com.casacampo.proj.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_reserva;

    private String nombre_cliente;

    private String telefono;

    private LocalDateTime fechaReserva;

    @ManyToOne
    @JoinColumn(name = "id_casa", referencedColumnName = "id_casa") // FK
    private CasaCampo CasaCampo;

}
