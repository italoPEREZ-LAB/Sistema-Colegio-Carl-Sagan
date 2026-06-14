package com.cibertec.colegio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cibertec.colegio.model.Rol;
import com.cibertec.colegio.model.Usuario;
import com.cibertec.colegio.repository.UsuarioRepository;
import com.cibertec.colegio.service.RolService;
import com.cibertec.colegio.service.UsuarioService;



@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Vista login
    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; // corresponde a src/main/resources/templates/login.html
    }

    // Vista registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("listaRoles", rolService.listarTodosRol());
        return "registrar"; // corresponde a registrar.html
    }

    // Guardar usuario nuevo
    @PostMapping("/register/save")
    public String register(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        Usuario existingUser = usuarioService.buscarByUsuario(usuario.getUsername());

        if (usuario.getNombres() == null || usuario.getNombres().isEmpty())
            result.rejectValue("nombres", null, "Ingresar nombres");

        if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty())
            result.rejectValue("apellidos", null, "Ingresar apellidos");

        if (usuario.getUsername() == null || usuario.getUsername().isEmpty())
            result.rejectValue("username", null, "Ingresar username");

        if (usuario.getClave() == null || usuario.getClave().isEmpty())
            result.rejectValue("clave", null, "Ingresar clave");

        if (usuario.getRol() == null || usuario.getRol().getId() == null)
            result.rejectValue("rol.id", null, "Seleccionar rol");

        if (existingUser != null)
            result.rejectValue("username", null, "Ya existe una cuenta con este usuario");

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaRoles", rolService.listarTodosRol()); 
            return "registrar";
        }

        Rol rolSeleccionado = rolService.buscarById(usuario.getRol().getId());
        usuario.setRol(rolSeleccionado);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/register?success";
    }

    // Vista de usuarios (después del login)
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("listaUsuarios", usuarios);
        return "usuario/index"; // corresponde a templates/usuario/index.html
    }

    // Vista de alumnos
    @GetMapping("/alumnos")
    public String listarAlumnos(Model model) {
        // Por ahora una vista básica, puedes conectar con AlumnoService después
        model.addAttribute("titulo", "Gestión de Alumnos");
        return "alumnos/index";
    }

    // Vista de tutores
    @GetMapping("/tutores")
    public String listarTutores(Model model) {
        // Por ahora una vista básica, puedes conectar con TutorService después
        model.addAttribute("titulo", "Gestión de Tutores");
        return "tutores/index";
    }

    // Vista de grados
    @GetMapping("/grados")
    public String listarGrados(Model model) {
        // Por ahora una vista básica, puedes conectar con GradoService después
        model.addAttribute("titulo", "Gestión de Grados");
        return "grados/index";
    }

    // Menú principal después del login exitoso
    @GetMapping("/menu")
    public String menuPrincipal() {
        return "menu"; // corresponde a templates/menu.html
    }

    // NUEVO: redirección después del login exitoso
    @GetMapping("/default")
    public String defaultAfterLogin() {
        // Redirigir al menú principal
        return "redirect:/menu";
    }
}
