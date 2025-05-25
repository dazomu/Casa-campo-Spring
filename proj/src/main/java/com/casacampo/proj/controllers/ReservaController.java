package com.casacampo.proj.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.entities.Reserva;
import com.casacampo.proj.services.CasaCampoService;


@Controller
@RequestMapping("reservas")
public class ReservaController {

    private final CasaCampoService casaCampoService;

    public ReservaController(CasaCampoService casaCampoService) {
    this.casaCampoService = casaCampoService;
    }


    @GetMapping("/casas")
    public String mostrarCasas(Model model) {
        List<CasaCampo> casas = casaCampoService.findAll();
        model.addAttribute("casas", casas);
        return "listaCasas";
    }

    @GetMapping("/casas/disponibles")
    public String listarCasasDisponibles(Model model) {
        List<CasaCampo> casasDisponibles = casaCampoService.listarCasasDisponibles();
        model.addAttribute("casas", casasDisponibles);
        return "listaCasasDisponibles"; // Tu template para mostrar casas disponibles
    }
    
    
    @GetMapping("realizar_reserva")
    public String realizarReserva(Model model) {
        
        model.addAttribute("reserva", new Reserva());
        List<CasaCampo> casasDisponibles = casaCampoService.listarCasasDisponibles(); // what
        model.addAttribute("casasDisponibles", casasDisponibles);
        
        return "reservaForm";
    }

}
