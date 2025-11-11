package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.RoboDTO;
import prog2.progiitp.g3.servicios.RoboService;
import java.util.List;

@RestController
@RequestMapping("/robos")
public class RoboController {

    @Autowired
    private RoboService roboService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RoboDTO> registrarRobo(@RequestBody RoboDTO dto) {
        RoboDTO nuevoRobo = roboService.crearRobo(dto);
        return new ResponseEntity<>(nuevoRobo, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<RoboDTO>> obtenerTodos() {
        List<RoboDTO> robos = roboService.obtenerTodos();
        return new ResponseEntity<>(robos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<RoboDTO> obtenerPorId(@PathVariable int id) {
        RoboDTO robo = roboService.obtenerPorId(id);
        return new ResponseEntity<>(robo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RoboDTO> actualizarRobo(@PathVariable int id, @RequestBody RoboDTO dto) {
        RoboDTO roboActualizado = roboService.actualizarRobo(id, dto);
        return new ResponseEntity<>(roboActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarRobo(@PathVariable int id) {
        roboService.borrarRobo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}