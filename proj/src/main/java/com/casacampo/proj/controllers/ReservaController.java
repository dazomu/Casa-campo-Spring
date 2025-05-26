package com.casacampo.proj.controllers;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.entities.Reserva;
import com.casacampo.proj.services.CasaCampoService;
import com.casacampo.proj.services.ReservaService;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("reservas")
public class ReservaController {

    private final CasaCampoService casaCampoService;

    private final ReservaService reservaService;

    public ReservaController(CasaCampoService casaCampoService, ReservaService reservaService) {
        this.casaCampoService = casaCampoService;
        this.reservaService = reservaService;                
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
    
    
    @GetMapping("/realizar_reserva")
    public String realizarReserva(Model model) {
        
        model.addAttribute("reserva", new Reserva());
        List<CasaCampo> casasDisponibles = casaCampoService.listarCasasDisponibles(); 
        model.addAttribute("casasDisponibles", casasDisponibles);
        
        return "reservaForm";

    }

    @PostMapping("/realizar_reserva")
    public String postMethodName(@ModelAttribute Reserva reserva, RedirectAttributes redirectAttributes) {
        
        try {
       
            reservaService.crearReserva(reserva);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva creada correctamente");
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la reserva: " + e.getMessage());
            e.printStackTrace();
        }

        
        return "redirect:/reservas/casas/disponibles";
    }
    
    @GetMapping("/listado")
    public String listadoReservas(Model model) {
        
        List<Reserva> reservas = reservaService.findAll();
        model.addAttribute("reservas", reservas);
        
        return "listadoReservas";
    }
    
    @GetMapping("/pdf")
    public void exportarReservasPdf(HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=reporte_de_reservas.pdf");

    List<Reserva> reservas = reservaService.findAll();

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    document.add(new Paragraph("Listado de Reservas"));
    document.add(new Paragraph(" ")); // espacio

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{1, 3, 2, 3, 3});
    table.setSpacingBefore(10);

    // Encabezados
    Stream.of("ID", "Cliente", "Teléfono", "Fecha", "Casa").forEach(header -> {
        PdfPCell cell = new PdfPCell(new Phrase(header));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    });

    // Filas
    for (Reserva r : reservas) {
        table.addCell(r.getId_reserva().toString());
        table.addCell(r.getUser().toString()); // ESTO TAL VEZ ESTE MAL
        table.addCell(r.getTelefono());
        table.addCell(r.getFechaReserva().toString());
        table.addCell(r.getCasaCampo().getNombre());
    }

    document.add(table);
    document.close();
    }

}
