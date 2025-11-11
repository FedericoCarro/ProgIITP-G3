package prog2.progiitp.g3.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog2.progiitp.g3.dao.CondenaDAO;
import prog2.progiitp.g3.dao.RoboDAO;
import prog2.progiitp.g3.dto.CondenaDTO;
import prog2.progiitp.g3.modelos.Condena;
import prog2.progiitp.g3.modelos.Robo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CondenaService {

    @Autowired
    private CondenaDAO condenaDAO;

    @Autowired
    private RoboDAO roboDAO;

    @Transactional
    public CondenaDTO crearCondena(CondenaDTO dto) {
        
    
        Robo roboAsociado = roboDAO.findById(dto.getIdRobo())
                .orElseThrow(() -> new RuntimeException("El robo con id " + dto.getIdRobo() + " no existe."));
                Optional<Condena> condenaExistente = condenaDAO.findByRoboId(dto.getIdRobo());
        if (condenaExistente.isPresent()) {
            throw new RuntimeException("El robo con id " + dto.getIdRobo() + " ya tiene una condena registrada.");
        }
        if (dto.getFechaInicio() == null || dto.getFechaFin() == null) {
            throw new RuntimeException("Las fechas de inicio y fin no pueden ser nulas.");
        }
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        Condena entidad = convertirA_Entidad(dto, roboAsociado);
        Condena entidadGuardada = condenaDAO.save(entidad);
        
        return convertirA_DTO(entidadGuardada);
    }
    @Transactional(readOnly = true)
    public List<CondenaDTO> obtenerTodas() {
        return condenaDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CondenaDTO obtenerPorId(int id) {
        Condena entidad = condenaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Condena no encontrada con id: " + id));
        return convertirA_DTO(entidad);
    }

    @Transactional(readOnly = true)
    public CondenaDTO obtenerPorRoboId(int roboId) {
        Condena entidad = condenaDAO.findByRoboId(roboId)
                .orElseThrow(() -> new RuntimeException("No se encontró condena para el robo con id: " + roboId));
        return convertirA_DTO(entidad);
    }

    @Transactional
    public CondenaDTO actualizarCondena(int id, CondenaDTO dto) {
        Condena entidadExistente = condenaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Condena no encontrada con id: " + id));

        if (dto.getFechaInicio() == null || dto.getFechaFin() == null) {
            throw new RuntimeException("Las fechas de inicio y fin no pueden ser nulas.");
        }
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        if (entidadExistente.getRobo().getId() != dto.getIdRobo()) {
            throw new RuntimeException("No se puede cambiar el robo asociado a una condena.");
        }

        entidadExistente.setFechaInicio(dto.getFechaInicio());
        entidadExistente.setFechaFin(dto.getFechaFin());
        
        Condena entidadActualizada = condenaDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }


    @Transactional
    public void borrarCondena(int id) {
        if (!condenaDAO.existsById(id)) {
            throw new RuntimeException("Condena no encontrada con id: " + id);
        }
        condenaDAO.deleteById(id);
    }

    private CondenaDTO convertirA_DTO(Condena entidad) {
        CondenaDTO dto = new CondenaDTO();
        dto.setId(entidad.getId());
        dto.setFechaInicio(entidad.getFechaInicio());
        dto.setFechaFin(entidad.getFechaFin());
        
        if (entidad.getRobo() != null) {
            dto.setIdRobo(entidad.getRobo().getId());
        }
        
        return dto;
    }

    private Condena convertirA_Entidad(CondenaDTO dto, Robo robo) {
        Condena entidad = new Condena();

        entidad.setFechaInicio(dto.getFechaInicio());
        entidad.setFechaFin(dto.getFechaFin());
        entidad.setRobo(robo);
        return entidad;
    }
}