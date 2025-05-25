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




@Controller
@RequestMapping("/casas")
public class CasaCampoController {

    
    private final CasaCampoService casaCampoService;

    public CasaCampoController(CasaCampoService casaCampoService) {
    this.casaCampoService = casaCampoService;
}

    @GetMapping("/lista")
    public String listaCasas(Model model) {
       
        List<CasaCampo> casas = casaCampoService.findAll();
        model.addAttribute("casas", casas);
        return "listaCasasAdmin";
    }
        
    @GetMapping("/{id}")
    public String verDetalles(@PathVariable("id") Long id, Model model) {
    CasaCampo casa = casaCampoService.findById(id);
    
        if (casa.getImagen() != null) {
            String base64Image = Base64.getEncoder().encodeToString(casa.getImagen());
            model.addAttribute("base64Image", base64Image);
        } else {
            model.addAttribute("base64Image", null);
        }
        
        model.addAttribute("casa", casa);
        return "details";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
    CasaCampo casa = casaCampoService.findById(id);
        model.addAttribute("casa", casa);
    return "update"; // nombre de la plantilla del formulario de edición
    }

    @PostMapping("/editar")
        public String procesarFormEditar(
            @ModelAttribute("casa") CasaCampo casa,
            @RequestParam("imagen") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            // Si subieron una imagen nueva, la conviertes a byte[] y la asignas
            if (!imagenFile.isEmpty()) {
                casa.setImagen(imagenFile.getBytes());
            } else {
                // Opcional: si no subieron imagen, mantener la anterior 
                // (deberías cargar la casa existente y copiar la imagen para evitar borrarla)
                CasaCampo casaExistente = casaCampoService.findById(casa.getId_casa());
                if (casaExistente != null) {
                    casa.setImagen(casaExistente.getImagen());
                }
            }

            // Guardar o actualizar la casa en la BD
            casaCampoService.save(casa);

            redirectAttributes.addFlashAttribute("mensaje", "Casa actualizada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la casa: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/casas/lista";
    }

}
