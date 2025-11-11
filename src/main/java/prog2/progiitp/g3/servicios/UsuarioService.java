package prog2.progiitp.g3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prog2.progiitp.g3.dao.UsuarioDAO;
import prog2.progiitp.g3.dto.UsuarioDTO;
import prog2.progiitp.g3.modelos.Usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    // Inyecta el encriptador de contraseñas que definimos en AppConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lista de permisos válidos según la consigna
    private static final List<String> PERMISOS_VALIDOS = Arrays.asList("VIGILANTE", "INVESTIGADOR", "ADMINISTRADOR");

    
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        if (usuarioDAO.existsByDni(dto.getDni())) {
            throw new RuntimeException("El DNI " + dto.getDni() + " ya está registrado.");
        }

        if (!PERMISOS_VALIDOS.contains(dto.getPermiso().toUpperCase())) {
            throw new RuntimeException("El permiso '" + dto.getPermiso() + "' no es válido.");
        }
        
        String contraseniaHasheada = passwordEncoder.encode(dto.getContrasenia());
        
        Usuario entidad = convertirA_Entidad(dto);
        entidad.setContrasenia(contraseniaHasheada);
        entidad.setPermiso(dto.getPermiso().toUpperCase());

        Usuario entidadGuardada = usuarioDAO.save(entidad);
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(int id) {
        Usuario entidad = usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertirA_DTO(entidad);
    }

        @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorDni(int dni) {
        Usuario entidad = usuarioDAO.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con dni: " + dni));
        return convertirA_DTO(entidad);
    }
    
    @Transactional
    public UsuarioDTO actualizarUsuario(int id, UsuarioDTO dto) {
        Usuario entidadExistente = usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (dto.getDni() != entidadExistente.getDni()) {
            if (usuarioDAO.existsByDni(dto.getDni())) {
                throw new RuntimeException("El DNI " + dto.getDni() + " ya pertenece a otro usuario.");
            }
        }

        if (!PERMISOS_VALIDOS.contains(dto.getPermiso().toUpperCase())) {
            throw new RuntimeException("El permiso '" + dto.getPermiso() + "' no es válido.");
        }

        entidadExistente.setNomApe(dto.getNomApe());
        entidadExistente.setDni(dto.getDni());
        entidadExistente.setTel(dto.getTel());
        entidadExistente.setEdad(dto.getEdad());
        entidadExistente.setPermiso(dto.getPermiso().toUpperCase());

        if (dto.getContrasenia() != null && !dto.getContrasenia().isEmpty()) {
            entidadExistente.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
        }

        Usuario entidadActualizada = usuarioDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarUsuario(int id) {
        if (!usuarioDAO.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }

        usuarioDAO.deleteById(id);
    }

    private UsuarioDTO convertirA_DTO(Usuario entidad) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entidad.getId());
        dto.setNomApe(entidad.getNomApe());
        dto.setDni(entidad.getDni());
        dto.setTel(entidad.getTel());
        dto.setEdad(entidad.getEdad());
        dto.setPermiso(entidad.getPermiso());
        return dto;
    }

    private Usuario convertirA_Entidad(UsuarioDTO dto) {
        Usuario entidad = new Usuario();
        entidad.setNomApe(dto.getNomApe());
        entidad.setDni(dto.getDni());
        entidad.setTel(dto.getTel());
        entidad.setEdad(dto.getEdad());
        entidad.setPermiso(dto.getPermiso());
        return entidad;
    }
}