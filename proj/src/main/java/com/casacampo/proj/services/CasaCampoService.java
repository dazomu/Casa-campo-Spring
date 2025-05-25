package com.casacampo.proj.services;

import java.util.List;

import com.casacampo.proj.entities.CasaCampo;

public interface CasaCampoService {

    List<CasaCampo> findAll();

    CasaCampo save(CasaCampo campo);

    CasaCampo findById(Long id_casa);

    Boolean update(CasaCampo campo);

    Boolean delete(Long id);

     List<CasaCampo> listarCasasDisponibles();
}
