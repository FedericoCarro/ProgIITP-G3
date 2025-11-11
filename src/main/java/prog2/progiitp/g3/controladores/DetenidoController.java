package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.DetenidoDTO;
import prog2.progiitp.g3.servicios.DetenidoService;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/detenidos")
public class DetenidoController {

    @Autowired
    private DetenidoService detenidoService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<DetenidoDTO> crearDetenido(@RequestBody DetenidoDTO dto) {
        DetenidoDTO nuevoDetenido = detenidoService.crearDetenido(dto);
        return new ResponseEntity<>(nuevoDetenido, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<DetenidoDTO>> obtenerTodos() {
        List<DetenidoDTO> detenidos = detenidoService.obtenerTodos();
        return new ResponseEntity<>(detenidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<DetenidoDTO> obtenerPorId(@PathVariable int id) {
        DetenidoDTO detenido = detenidoService.obtenerPorId(id);
        return new ResponseEntity<>(detenido, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<DetenidoDTO> actualizarDetenido(
            @PathVariable int id,
            @RequestBody DetenidoDTO dto) {
        
        DetenidoDTO detenidoActualizado = detenidoService.actualizarDetenido(id, dto);
        return new ResponseEntity<>(detenidoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarDetenido(@PathVariable int id) {
        detenidoService.borrarDetenido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}