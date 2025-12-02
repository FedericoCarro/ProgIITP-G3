package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.RoboDTO;
import prog2.progiitp.g3.servicios.DetenidoService;
import prog2.progiitp.g3.servicios.JuezService;
import prog2.progiitp.g3.servicios.RoboService;
import prog2.progiitp.g3.servicios.SucursalBancariaService;

@Controller
@RequestMapping("/robos")
public class RoboController {

    @Autowired
    private RoboService roboService;
    @Autowired
    private DetenidoService detenidoService;
    @Autowired
    private SucursalBancariaService sucursalService;
    @Autowired
    private JuezService juezService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("robos", roboService.obtenerTodos());
        return "robos/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("robo", new RoboDTO());
        model.addAttribute("listaDetenidos", detenidoService.obtenerTodos());
        model.addAttribute("listaSucursales", sucursalService.obtenerTodas());
        model.addAttribute("listaJueces", juezService.obtenerTodos());
        return "robos/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("robo", roboService.obtenerPorId(id));
        model.addAttribute("listaDetenidos", detenidoService.obtenerTodos());
        model.addAttribute("listaSucursales", sucursalService.obtenerTodas());
        model.addAttribute("listaJueces", juezService.obtenerTodos());
        return "robos/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute RoboDTO dto) {
        if (dto.getId()!=null && dto.getId() > 0) {
            roboService.actualizarRobo(dto.getId(), dto);
        } else {
            roboService.crearRobo(dto);
        }
        return "redirect:/robos";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        roboService.borrarRobo(id);
        return "redirect:/robos";
    }
}