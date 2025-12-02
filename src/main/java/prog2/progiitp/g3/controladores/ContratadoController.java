package prog2.progiitp.g3.controladores;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.ContratadoDTO;
import prog2.progiitp.g3.dto.UsuarioDTO;
import prog2.progiitp.g3.servicios.ContratadoService;
import prog2.progiitp.g3.servicios.SucursalBancariaService;
import prog2.progiitp.g3.servicios.UsuarioService;

@Controller
@RequestMapping("/contratos")
public class ContratadoController {

    @Autowired
    private ContratadoService contratadoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SucursalBancariaService sucursalService;

    // Lista general para Admin
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("contratos", contratadoService.obtenerTodos());
        return "contratos/lista";
    }

    // Lista especial para Vigilante
    @GetMapping("/mis-contratos")
    @PreAuthorize("hasAuthority('VIGILANTE')")
    public String obtenerMisContratos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int dniVigilante = Integer.parseInt(auth.getName());
        
        model.addAttribute("contratos", contratadoService.obtenerContratosPorDniVigilante(dniVigilante));
        return "contratos/mis-contratos";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("contrato", new ContratadoDTO());
        List<UsuarioDTO> todos = usuarioService.obtenerTodos();
        List<UsuarioDTO> soloVigilantes = todos.stream()
        .filter(u -> "VIGILANTE".equals(u.getPermiso()))
        .collect(Collectors.toList());
        
    model.addAttribute("listaVigilantes", soloVigilantes);
        model.addAttribute("listaSucursales", sucursalService.obtenerTodas());
        return "contratos/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute ContratadoDTO dto) {
        contratadoService.crearContrato(dto);
        return "redirect:/contratos";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        contratadoService.borrarContrato(id);
        return "redirect:/contratos";
    }
}