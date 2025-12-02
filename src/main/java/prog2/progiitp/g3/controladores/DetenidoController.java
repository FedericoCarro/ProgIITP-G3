package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.DetenidoDTO;
import prog2.progiitp.g3.servicios.BandaOrganizadaService;
import prog2.progiitp.g3.servicios.DetenidoService;

@Controller
@RequestMapping("/detenidos")
public class DetenidoController {

    @Autowired
    private DetenidoService detenidoService;
    @Autowired
    private BandaOrganizadaService bandaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("detenidos", detenidoService.obtenerTodos());
        return "detenidos/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("detenido", new DetenidoDTO());
        model.addAttribute("listaBandas", bandaService.obtenerTodas());
        return "detenidos/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("detenido", detenidoService.obtenerPorId(id));
        model.addAttribute("listaBandas", bandaService.obtenerTodas());
        return "detenidos/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute DetenidoDTO dto) {
        if (dto.getId()!=null && dto.getId() > 0) {
            detenidoService.actualizarDetenido(dto.getId(), dto);
        } else {
            detenidoService.crearDetenido(dto);
        }
        return "redirect:/detenidos";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        detenidoService.borrarDetenido(id);
        return "redirect:/detenidos";
    }
}