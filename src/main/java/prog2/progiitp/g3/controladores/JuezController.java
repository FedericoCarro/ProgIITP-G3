package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.JuezDTO;
import prog2.progiitp.g3.servicios.JuezService;

@Controller
@RequestMapping("/jueces")
public class JuezController {

    @Autowired
    private JuezService juezService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("jueces", juezService.obtenerTodos());
        return "jueces/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("juez", new JuezDTO());
        return "jueces/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("juez", juezService.obtenerPorClave(id));
        return "jueces/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute JuezDTO dto) {
        if (dto.getClave()!=null && dto.getClave() > 0) {
            juezService.actualizarJuez(dto.getClave(), dto);
        } else {
            juezService.crearJuez(dto);
        }
        return "redirect:/jueces";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        juezService.borrarJuez(id);
        return "redirect:/jueces";
    }
}