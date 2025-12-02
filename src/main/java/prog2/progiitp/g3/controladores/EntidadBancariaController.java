package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.EntidadBancariaDTO;
import prog2.progiitp.g3.servicios.EntidadBancariaService;

@Controller
@RequestMapping("/entidades")
public class EntidadBancariaController {

    @Autowired
    private EntidadBancariaService entidadService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')") 
    public String listar(Model model) {
        model.addAttribute("entidades", entidadService.obtenerTodas());
        return "entidades/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("entidad", new EntidadBancariaDTO());
        return "entidades/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("entidad", entidadService.obtenerPorId(id));
        return "entidades/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute EntidadBancariaDTO dto) {
        if (dto.getId()!=null && dto.getId() > 0) {
            entidadService.actualizarEntidad(dto.getId(), dto);
        } else {
            entidadService.crearEntidad(dto);
        }
        return "redirect:/entidades";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        entidadService.borrarEntidad(id);
        return "redirect:/entidades";
    }
}