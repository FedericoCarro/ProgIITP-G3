package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.BandaOrganizadaDTO;
import prog2.progiitp.g3.servicios.BandaOrganizadaService;
import java.util.List;

@RestController
@RequestMapping("/bandas") 
public class BandaOrganizadaController {

    @Autowired
    private BandaOrganizadaService bandaOrganizadaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<BandaOrganizadaDTO> crearBanda(@RequestBody BandaOrganizadaDTO dto) {
        BandaOrganizadaDTO nuevaBanda = bandaOrganizadaService.crearBanda(dto);
        return new ResponseEntity<>(nuevaBanda, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<BandaOrganizadaDTO>> obtenerTodas() {
        List<BandaOrganizadaDTO> bandas = bandaOrganizadaService.obtenerTodas();
        return new ResponseEntity<>(bandas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<BandaOrganizadaDTO> obtenerPorId(@PathVariable int id) {
        BandaOrganizadaDTO banda = bandaOrganizadaService.obtenerPorId(id);
        return new ResponseEntity<>(banda, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<BandaOrganizadaDTO> actualizarBanda(
            @PathVariable int id,
            @RequestBody BandaOrganizadaDTO dto) {
        
        BandaOrganizadaDTO bandaActualizada = bandaOrganizadaService.actualizarBanda(id, dto);
        return new ResponseEntity<>(bandaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarBanda(@PathVariable int id) {
        bandaOrganizadaService.borrarBanda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}