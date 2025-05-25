package com.casacampo.proj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casacampo.proj.entities.CasaCampo;

public interface CasaCampoRepository extends JpaRepository<CasaCampo, Long>{

    List<CasaCampo> findByDisponibleTrue();
    
}
