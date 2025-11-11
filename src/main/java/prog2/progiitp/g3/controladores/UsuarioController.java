package prog2.progiitp.g3.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog2.progiitp.g3.dto.UsuarioDTO;
import prog2.progiitp.g3.servicios.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> borrarUsuario(@PathVariable int id) {
        usuarioService.borrarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

        @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable int id,
            @RequestBody UsuarioDTO dto) {
        
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, dto);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('INVESTIGADOR')")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable int id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    
@GetMapping("/mi-perfil")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<UsuarioDTO> obtenerMiPerfil() { 
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    int dni = Integer.parseInt(authentication.getName());
    UsuarioDTO usuario = usuarioService.obtenerPorDni(dni);
    return ResponseEntity.ok(usuario);
}
}