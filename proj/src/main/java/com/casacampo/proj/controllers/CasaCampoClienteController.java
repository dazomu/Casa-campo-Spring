package com.casacampo.proj.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.entities.Reserva;
import com.casacampo.proj.entities.User;
import com.casacampo.proj.services.CasaCampoService;
import com.casacampo.proj.services.ReservaService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/casas")
public class CasaCampoClienteController {

    private final Logger LOG = LoggerFactory.getLogger(CasaCampoClienteController.class);

    private final CasaCampoService casaCampoService;

    private final ReservaService reservaService;

    public CasaCampoClienteController(CasaCampoService casaCampoService, ReservaService reservaService) {
    this.casaCampoService = casaCampoService;
    this.reservaService = reservaService;
    }



    @GetMapping()
    public String mostrarCasas(Model model) {
        List<CasaCampo> casas = casaCampoService.findAll();
        model.addAttribute("casas", casas);
        return "listaCasas";
    }

    @GetMapping("/disponibles")
    public String listarCasasDisponibles(Model model, HttpSession session) {

        User user = (User) session.getAttribute("usuarioLogueado");

        LOG.info("El usuario es este: " + user.getNombre());

        List<CasaCampo> casasDisponibles = casaCampoService.listarCasasDisponibles();

        model.addAttribute("nombreUsuario", user.getNombre());



        model.addAttribute("casas", casasDisponibles);
        return "listaCasasDisponibles"; // Tu template para mostrar casas disponibles
    }

        
    @GetMapping("/{id}")
    public String mostrarDetalleCasa(@PathVariable Long id, Model model) {
        CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);

        return "details"; 
    }

    //Realizar Nueva Reserva

    @GetMapping("/reserva/nueva/{id}")
    public String realizarReserva(Model model, @PathVariable Long id, HttpSession session) {
    
    // Verificar que el usuario esté logueado
    User usuarioLogueado = (User) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        return "redirect:/login";
    }
    
    // Obtener la casa específica que se va a reservar
    CasaCampo casa = casaCampoService.findById(id);
    if (casa == null) {
        return "redirect:/casas";
    }
    
    // Pasar los datos al formulario
    model.addAttribute("reserva", new Reserva());
    model.addAttribute("usuarioLogueado", usuarioLogueado);
    model.addAttribute("casa", casa);
    
    return "reservaForm";
}


    @PostMapping("/reserva/nueva")
    public String procesarReserva(@ModelAttribute Reserva reserva,
                            @RequestParam Long idCasaCampo,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
    try {
        // Buscar y asignar la casa
        CasaCampo casa = casaCampoService.findById(idCasaCampo);
        reserva.setCasaCampo(casa);

        // Recuperar el usuario logueado desde la sesión
        User usuarioLogueado = (User) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para hacer una reserva.");
            return "redirect:/login";
        }

        reserva.setUser(usuarioLogueado);

        // Guardar reserva
        reservaService.crearReserva(reserva);
        redirectAttributes.addFlashAttribute("mensaje", "Reserva creada correctamente");

    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al crear la reserva: " + e.getMessage());
        e.printStackTrace();
    }

    return "redirect:/casas/disponibles";
    }
    
}
