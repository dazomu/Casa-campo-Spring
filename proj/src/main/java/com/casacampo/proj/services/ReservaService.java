package com.casacampo.proj.services;

import java.util.List;

import com.casacampo.proj.entities.Reserva;

public interface ReservaService {

    List<Reserva> findAll();

    Reserva create(Reserva reserva);

    Boolean update(Reserva reserva);

    Boolean delete(Long id);

}
