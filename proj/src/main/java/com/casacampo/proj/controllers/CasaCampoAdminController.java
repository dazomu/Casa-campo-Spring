package com.casacampo.proj.controllers;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
@RequestMapping("/admin")
public class CasaCampoAdminController {

     private final CasaCampoService casaCampoService;

    private final ReservaService reservaService;

    public CasaCampoAdminController(CasaCampoService casaCampoService, ReservaService reservaService) {
    this.casaCampoService = casaCampoService;
    this.reservaService = reservaService;
    
}

    @GetMapping("/casas")
    public String listaCasas(Model model) {
       
        List<CasaCampo> casas = casaCampoService.findAll();
        model.addAttribute("casas", casas);
        return "listaCasasAdmin";
    }

    @GetMapping("/casas/editar/{id}")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);
        return "update"; 
    }

    
    @PostMapping("/casas/editar")
    public String procesarFormEditar(
        @ModelAttribute CasaCampo casa,
        RedirectAttributes redirectAttributes) {

        try {
            // Guarda normalmente
            casaCampoService.save(casa);
            redirectAttributes.addFlashAttribute("mensaje", "Casa actualizada correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la casa: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/admin/casas";

        }

    @GetMapping("/casas/nueva")
    public String mostrarFormCasa(Model model) {
        model.addAttribute("casa", new CasaCampo()); 
        return "nuevaCasa";
    }
    
    @PostMapping("/casas/nueva")
    public String procesarFormCasa(@ModelAttribute CasaCampo casa, RedirectAttributes redirectAttributes) {

        try {
       
            casaCampoService.save(casa);
            redirectAttributes.addFlashAttribute("mensaje", "Casa agregada correctamente");
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar casa: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/admin/casas";
    }

    
    @GetMapping("/casas/{id}")
    public String mostrarDetalleCasa(@PathVariable Long id, Model model) {
        CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);

        return "detailsAdmin"; 
    }


    @PostMapping("/casas/eliminar/{idCasa}")
    public String eliminarCasa(@PathVariable Long idCasa) {
        
        casaCampoService.delete(idCasa);   
        return "redirect:/admin/casas";
    }

    // GESTION DE RESERVAS

    @GetMapping("/reservas/listado")
    public String listadoReservas(Model model) {
        
        List<Reserva> reservas = reservaService.findAll();
        model.addAttribute("reservas", reservas);
        
        return "listadoReservas";
    }

    
    
    @GetMapping("/reservas/pdf")
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
