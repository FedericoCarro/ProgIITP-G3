package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;  
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.ContratadoDTO;
import prog2.progiitp.g3.servicios.ContratadoService;
import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratadoController {

    @Autowired
    private ContratadoService contratadoService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ContratadoDTO> crearContrato(@RequestBody ContratadoDTO dto) {
        ContratadoDTO nuevoContrato = contratadoService.crearContrato(dto);
        return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')") 
    public ResponseEntity<List<ContratadoDTO>> obtenerTodos() {
        List<ContratadoDTO> contratos = contratadoService.obtenerTodos();
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<ContratadoDTO> obtenerPorId(@PathVariable int id) {
        ContratadoDTO contrato = contratadoService.obtenerPorId(id);
        return new ResponseEntity<>(contrato, HttpStatus.OK);
    }


    @GetMapping("/mis-contratos")
    @PreAuthorize("hasAuthority('VIGILANTE')")
    public ResponseEntity<List<ContratadoDTO>> obtenerMisContratos(Authentication authentication) {

        int dniVigilante = Integer.parseInt(authentication.getName()); 
        

        List<ContratadoDTO> contratos = contratadoService.obtenerContratosPorDniVigilante(dniVigilante);
        return new ResponseEntity<>(contratos, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarContrato(@PathVariable int id) {
        contratadoService.borrarContrato(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}