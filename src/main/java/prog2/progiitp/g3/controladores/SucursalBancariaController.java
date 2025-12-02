package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.SucursalBancariaDTO;
import prog2.progiitp.g3.servicios.EntidadBancariaService;
import prog2.progiitp.g3.servicios.SucursalBancariaService;

@Controller
@RequestMapping("/sucursales")
public class SucursalBancariaController {

    @Autowired
    private SucursalBancariaService sucursalService;
    
    @Autowired
    private EntidadBancariaService entidadService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public String listar(Model model) {
        model.addAttribute("sucursales", sucursalService.obtenerTodas());
        return "sucursales/lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioCrear(Model model) {
        model.addAttribute("sucursal", new SucursalBancariaDTO());
        model.addAttribute("listaEntidades", entidadService.obtenerTodas());
        return "sucursales/formulario";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String formularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("sucursal", sucursalService.obtenerPorId(id));
        model.addAttribute("listaEntidades", entidadService.obtenerTodas());
        return "sucursales/formulario";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String guardar(@ModelAttribute SucursalBancariaDTO dto) {
        if (dto.getId()!=null && dto.getId() > 0) {
            sucursalService.actualizarSucursal(dto.getId(), dto);
        } else {
            sucursalService.crearSucursal(dto);
        }
        return "redirect:/sucursales";
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public String eliminar(@PathVariable int id) {
        sucursalService.borrarSucursal(id);
        return "redirect:/sucursales";
    }
}