package com.casacampo.proj.controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.casacampo.proj.entities.CasaCampo;
import com.casacampo.proj.repositories.CasaCampoRepository;
import com.casacampo.proj.services.CasaCampoService;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/casas")
public class CasaCampoController {

    
    private final CasaCampoService casaCampoService;

    public CasaCampoController(CasaCampoService casaCampoService) {
    this.casaCampoService = casaCampoService;
}

    @GetMapping()
    public String listaCasas(Model model) {
       
        List<CasaCampo> casas = casaCampoService.findAll();
        model.addAttribute("casas", casas);
        return "listaCasasAdmin";
    }
        
    @GetMapping("/{id}")
    public String mostrarDetalleCasa(@PathVariable Long id, Model model) {
        CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);

        return "details"; 
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);
        return "update"; 
    }

    @PostMapping("/editar")
    public String procesarFormEditar(
        @ModelAttribute("casa") CasaCampo casa,
        RedirectAttributes redirectAttributes) {

        try {
            // Guarda normalmente
            casaCampoService.save(casa);
            redirectAttributes.addFlashAttribute("mensaje", "Casa actualizada correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la casa: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/casas";

        }

    @GetMapping("/nueva_casa")
    public String mostrarFormCasa(Model model) {
        model.addAttribute("casa", new CasaCampo()); 
        return "nuevaCasa";
    }
    
    @PostMapping("/nueva_casa")
    public String procesarFormCasa(@ModelAttribute CasaCampo casa, RedirectAttributes redirectAttributes) {

        try {
       
        casaCampoService.save(casa);

            redirectAttributes.addFlashAttribute("mensaje", "Casa agregada correctamente");
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agregar casa: " + e.getMessage());
            e.printStackTrace();
        }


        return "redirect:/casas";
    }
    
    @PostMapping("/eliminar/{idCasa}")
    public String eliminarCasa(@PathVariable Long idCasa) {
        
        casaCampoService.delete(idCasa);
        
        return "redirect:/casas";
    }
    


}
