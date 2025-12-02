package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.CondenaDTO;
import prog2.progiitp.g3.servicios.CondenaService;
import prog2.progiitp.g3.servicios.RoboService;

@Controller
@RequestMapping("/condenas")
public class CondenaController {

    @Autowired
    private CondenaService condenaService;
    @Autowired
    private RoboService roboService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("condenas", condenaService.obtenerTodas());
        return "condenas/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("condena", new CondenaDTO());
        model.addAttribute("listaRobos", roboService.obtenerTodos());
        return "condenas/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("condena", condenaService.obtenerPorId(id));
        model.addAttribute("listaRobos", roboService.obtenerTodos());
        return "condenas/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute CondenaDTO dto) {
        if (dto.getId()!=null && dto.getId() > 0) {
            condenaService.actualizarCondena(dto.getId(), dto);
        } else {
            condenaService.crearCondena(dto);
        }
        return "redirect:/condenas";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        condenaService.borrarCondena(id);
        return "redirect:/condenas";
    }
}