package prog2.progiitp.g3.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog2.progiitp.g3.dao.JuezDAO;
import prog2.progiitp.g3.dao.RoboDAO;
import prog2.progiitp.g3.dto.JuezDTO;
import prog2.progiitp.g3.modelos.Juez;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JuezService {

    @Autowired
    private JuezDAO juezDAO;

    @Autowired
    private RoboDAO roboDAO;
    @Transactional
    public JuezDTO crearJuez(JuezDTO dto) {
        if (dto.getAniosDeServicio() < 0) {
            throw new RuntimeException("Los anios de servicio no pueden ser negativos.");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
             throw new RuntimeException("El nombre del juez no puede estar vacío.");
        }

        Juez entidad = convertirA_Entidad(dto);
        Juez entidadGuardada = juezDAO.save(entidad);
        
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<JuezDTO> obtenerTodos() {
        return juezDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JuezDTO obtenerPorClave(int clave) {
        Juez entidad = juezDAO.findById(clave)
                .orElseThrow(() -> new RuntimeException("Juez no encontrado con clave: " + clave));
        return convertirA_DTO(entidad);
    }

    @Transactional
    public JuezDTO actualizarJuez(int clave, JuezDTO dto) {
        Juez entidadExistente = juezDAO.findById(clave)
                .orElseThrow(() -> new RuntimeException("Juez no encontrado con clave: " + clave));

        if (dto.getAniosDeServicio() < 0) {
            throw new RuntimeException("Los anios de servicio no pueden ser negativos.");
        }
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
             throw new RuntimeException("El nombre del juez no puede estar vacío.");
        }

        entidadExistente.setNombre(dto.getNombre());
        entidadExistente.setAniosDeServicio(dto.getAniosDeServicio());

        Juez entidadActualizada = juezDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarJuez(int clave) {
        if (!juezDAO.existsById(clave)) {
            throw new RuntimeException("Juez no encontrado con clave: " + clave);
        }

        if (roboDAO.existsByJuezClave(clave)) {
            throw new RuntimeException("No se puede borrar el juez (Clave: " + clave + "), tiene casos (robos) asociados.");
        }

        juezDAO.deleteById(clave);
    }

    private JuezDTO convertirA_DTO(Juez entidad) {
        JuezDTO dto = new JuezDTO();
        dto.setClave(entidad.getClave());
        dto.setNombre(entidad.getNombre());
        dto.setAniosDeServicio(entidad.getAniosDeServicio());
        return dto;
    }

    private Juez convertirA_Entidad(JuezDTO dto) {
        Juez entidad = new Juez();
        entidad.setNombre(dto.getNombre());
        entidad.setAniosDeServicio(dto.getAniosDeServicio());
        return entidad;
    }
}