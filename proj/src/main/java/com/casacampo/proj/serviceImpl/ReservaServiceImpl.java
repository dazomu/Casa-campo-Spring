package com.casacampo.proj.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.entities.Reserva;
import com.casacampo.proj.repositories.CasaCampoRepository;
import com.casacampo.proj.repositories.ReservaRepository;
import com.casacampo.proj.services.ReservaService;

import jakarta.transaction.Transactional;

@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    private ReservaRepository reservas;

    @Autowired
    private CasaCampoRepository casas;

    @Override
    public List<Reserva> findAll() {
        
        return reservas.findAll();
    }

    @Override
    public Reserva create(Reserva reserva) {

        return reservas.save(reserva);
    }

    @Override
    public Boolean update(Reserva reserva) {
        
        if (reservas.existsById(reserva.getId_reserva())) {
            reservas.save(reserva);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        if (reservas.existsById(id)) {
            reservas.deleteById(id);
            return true;
        }
        return false;
    }

   
    @Override
    @Transactional
    public void crearReserva(Reserva reserva) {
  
        CasaCampo casa = reserva.getCasaCampo();
        casa.setDisponible(false);
        casas.save(casa);
        reservas.save(reserva);
    }

    

}
