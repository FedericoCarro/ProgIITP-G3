package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.CondenaDTO;
import prog2.progiitp.g3.servicios.CondenaService; 
import java.util.List;

@RestController
@RequestMapping("/condenas")
public class CondenaController {

    @Autowired
    private CondenaService condenaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CondenaDTO> crearCondena(@RequestBody CondenaDTO dto) {
        CondenaDTO nuevaCondena = condenaService.crearCondena(dto);
        return new ResponseEntity<>(nuevaCondena, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<CondenaDTO>> obtenerTodas() {
        List<CondenaDTO> condenas = condenaService.obtenerTodas();
        return new ResponseEntity<>(condenas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<CondenaDTO> obtenerPorId(@PathVariable int id) {
        CondenaDTO condena = condenaService.obtenerPorId(id);
        return new ResponseEntity<>(condena, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CondenaDTO> actualizarCondena(@PathVariable int id, @RequestBody CondenaDTO dto) {
        CondenaDTO condenaActualizada = condenaService.actualizarCondena(id, dto);
        return new ResponseEntity<>(condenaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarCondena(@PathVariable int id) {
        condenaService.borrarCondena(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}