package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.JuezDTO;
import prog2.progiitp.g3.servicios.JuezService; 
import java.util.List;

@RestController
@RequestMapping("/jueces")
public class JuezController {

    @Autowired
    private JuezService juezService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<JuezDTO> crearJuez(@RequestBody JuezDTO dto) {
        JuezDTO nuevoJuez = juezService.crearJuez(dto);
        return new ResponseEntity<>(nuevoJuez, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<JuezDTO>> obtenerTodos() {
        List<JuezDTO> jueces = juezService.obtenerTodos();
        return new ResponseEntity<>(jueces, HttpStatus.OK);
    }

    @GetMapping("/{clave}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<JuezDTO> obtenerPorClave(@PathVariable int clave) {
        JuezDTO juez = juezService.obtenerPorClave(clave);
        return new ResponseEntity<>(juez, HttpStatus.OK);
    }

    @PutMapping("/{clave}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<JuezDTO> actualizarJuez(
            @PathVariable int clave,
            @RequestBody JuezDTO dto) {
        
        JuezDTO juezActualizado = juezService.actualizarJuez(clave, dto);
        return new ResponseEntity<>(juezActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{clave}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarJuez(@PathVariable int clave) {
        juezService.borrarJuez(clave);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}