package com.casacampo.proj.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.repositories.CasaCampoRepository;
import com.casacampo.proj.services.CasaCampoService;

@Service
public class CasaCampoServiceImpl implements CasaCampoService{

    @Autowired
    CasaCampoRepository casaCampoRepository;

    @Override
    public List<CasaCampo> findAll() {
        
        return casaCampoRepository.findAll();

    }

    @Override
    public CasaCampo save(CasaCampo casacampo) {
        
        return casaCampoRepository.save(casacampo);
    }

    @Override
    public Boolean update(CasaCampo casacampo) {

        if (casaCampoRepository.existsById(casacampo.getId_casa())) {
            casaCampoRepository.save(casacampo);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        if (casaCampoRepository.existsById(id)) {
            casaCampoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CasaCampo findById(Long id_casa) {

        return casaCampoRepository.findById(id_casa)
                                .orElseThrow(() -> new RuntimeException("Casa no encontrada con id: " + id_casa));
    }

}
