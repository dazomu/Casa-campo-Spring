
package com.casacampo.proj.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.casacampo.proj.entities.User;
import com.casacampo.proj.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/usuario")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/registro")
    public String mostrarForm(Model model, @ModelAttribute User user) {
        model.addAttribute("user", new User());
        return "registerForm";
    }
    
    @PostMapping("/registro")
    public String procesarForm(@ModelAttribute User user) {
        
        user.setRol("CLIENTE");
        userService.save(user); // Asegúrate de tener un servicio configurado

        return "redirect:/usuario/login";
        
    }
   
    @GetMapping("/login")
    public String mostrarLogin() {
        
        return "loginForm";
    }
    
    
    @PostMapping("/login")
    public String login(@RequestParam String correo,
                    @RequestParam String contrasenia,
                    HttpSession session,
                    Model model) {

    Optional<User> optionalUsuario = userService.findByCorreo(correo);

        if (optionalUsuario.isPresent()) {
            User user = optionalUsuario.get();
            if (user.getContrasenia().equals(contrasenia) && user.getRol().equals("CLIENTE")) {
                session.setAttribute("usuarioLogueado", user);
                return "redirect:/casas/disponibles"; 
            }else if(user.getContrasenia().equals(contrasenia) && user.getRol().equals("ADMIN")){
                session.setAttribute("usuarioLogueado", user);
                return "redirect:/usuario/indexAdmin"; 
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "loginForm"; // 
    }

    @GetMapping("/index")
    public String clienteIndex() {
        return "index"; // plantilla para cliente
    }

    @GetMapping("/indexAdmin")
    public String adminIndex() {
        return "indexAdmin"; // plantilla para admin
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // invalida la sesión actual
        return "redirect:/usuario/login"; // redirige al login o página pública
    }

}
