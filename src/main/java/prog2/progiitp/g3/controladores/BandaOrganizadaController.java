package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.BandaOrganizadaDTO;
import prog2.progiitp.g3.servicios.BandaOrganizadaService;

@Controller
@RequestMapping("/bandas")
public class BandaOrganizadaController {

    @Autowired
    private BandaOrganizadaService bandaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("bandas", bandaService.obtenerTodas());
        return "bandas/lista"; 
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("banda", new BandaOrganizadaDTO());
        return "bandas/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("banda", bandaService.obtenerPorId(id));
        return "bandas/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute BandaOrganizadaDTO banda) {
        if (banda.getId()!=null && banda.getId() > 0) {
            bandaService.actualizarBanda(banda.getId(), banda);
        } else {
            bandaService.crearBanda(banda);
        }
        return "redirect:/bandas";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        bandaService.borrarBanda(id);
        return "redirect:/bandas";
    }
}