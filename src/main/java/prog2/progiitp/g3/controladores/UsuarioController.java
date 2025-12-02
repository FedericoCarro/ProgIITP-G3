package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.UsuarioDTO;
import prog2.progiitp.g3.servicios.UsuarioService;
import java.util.List;

@Controller
@RequestMapping("/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> lista = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", lista);

        return "usuarios/lista"; 
    }
    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "usuarios/formulario"; 
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardarUsuario(@ModelAttribute("usuario") UsuarioDTO usuario) {
        if (usuario.getId()!=null && usuario.getId() > 0) {
            usuarioService.actualizarUsuario(usuario.getId(), usuario);
        } else {
            usuarioService.crearUsuario(usuario);
        }
        return "redirect:/usuarios"; 
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String borrarUsuario(@PathVariable int id) {
        usuarioService.borrarUsuario(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/mi-perfil")
    @PreAuthorize("isAuthenticated()")
    public String verMiPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int dni = Integer.parseInt(authentication.getName());
        
        UsuarioDTO usuario = usuarioService.obtenerPorDni(dni);
        model.addAttribute("usuario", usuario);
        
        return "usuarios/perfil";
    }
}