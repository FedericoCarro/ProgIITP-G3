package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import prog2.progiitp.g3.dto.EntidadBancariaDTO;
import prog2.progiitp.g3.servicios.EntidadBancariaService;

import java.util.List;

@RestController 
@RequestMapping("/entidades") 
public class EntidadBancariaController {

    @Autowired
    private EntidadBancariaService entidadBancariaService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EntidadBancariaDTO>> obtenerTodas() {
        List<EntidadBancariaDTO> entidades = entidadBancariaService.obtenerTodas();
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EntidadBancariaDTO> obtenerPorId(@PathVariable int id) {
        EntidadBancariaDTO entidad = entidadBancariaService.obtenerPorId(id);
        return new ResponseEntity<>(entidad, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<EntidadBancariaDTO> crearEntidad(@RequestBody EntidadBancariaDTO dto) {
        EntidadBancariaDTO nuevaEntidad = entidadBancariaService.crearEntidad(dto);
        return new ResponseEntity<>(nuevaEntidad, HttpStatus.CREATED); 
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<EntidadBancariaDTO> actualizarEntidad(
            @PathVariable int id, 
            @RequestBody EntidadBancariaDTO dto) {
        
        EntidadBancariaDTO entidadActualizada = entidadBancariaService.actualizarEntidad(id, dto);
        return new ResponseEntity<>(entidadActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarEntidad(@PathVariable int id) {
        entidadBancariaService.borrarEntidad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}