package prog2.progiitp.g3.controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.SucursalBancariaDTO;
import prog2.progiitp.g3.servicios.SucursalBancariaService;
import java.util.List;

@RestController
@RequestMapping("/sucursales")
public class SucursalBancariaController {

    @Autowired
    private SucursalBancariaService sucursalBancariaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<SucursalBancariaDTO> crearSucursal(@RequestBody SucursalBancariaDTO dto) {
        SucursalBancariaDTO nuevaSucursal = sucursalBancariaService.crearSucursal(dto);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<SucursalBancariaDTO>> obtenerTodas() {
        List<SucursalBancariaDTO> sucursales = sucursalBancariaService.obtenerTodas();
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<SucursalBancariaDTO> obtenerPorId(@PathVariable int id) {
        SucursalBancariaDTO sucursal = sucursalBancariaService.obtenerPorId(id);
        return new ResponseEntity<>(sucursal, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<SucursalBancariaDTO> actualizarSucursal(
            @PathVariable int id,
            @RequestBody SucursalBancariaDTO dto) {
        
        SucursalBancariaDTO sucursalActualizada = sucursalBancariaService.actualizarSucursal(id, dto);
        return new ResponseEntity<>(sucursalActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarSucursal(@PathVariable int id) {
        sucursalBancariaService.borrarSucursal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}