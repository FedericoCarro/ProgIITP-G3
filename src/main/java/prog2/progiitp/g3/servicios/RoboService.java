package prog2.progiitp.g3.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog2.progiitp.g3.dao.CondenaDAO;
import prog2.progiitp.g3.dao.DetenidoDAO;
import prog2.progiitp.g3.dao.JuezDAO;
import prog2.progiitp.g3.dao.RoboDAO;
import prog2.progiitp.g3.dao.SucursalBancariaDAO;
import prog2.progiitp.g3.dto.RoboDTO;
import prog2.progiitp.g3.modelos.Detenido;
import prog2.progiitp.g3.modelos.Juez;
import prog2.progiitp.g3.modelos.Robo;
import prog2.progiitp.g3.modelos.SucursalBancaria;
import prog2.progiitp.g3.modelos.Condena;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoboService {

    // DAOs para las operaciones principales
    @Autowired
    private RoboDAO roboDAO;
    @Autowired
    private CondenaDAO condenaDAO;

    // DAOs para validación de claves foráneas
    @Autowired
    private DetenidoDAO detenidoDAO;
    @Autowired
    private SucursalBancariaDAO sucursalBancariaDAO;
    @Autowired
    private JuezDAO juezDAO;

    @Transactional
    public RoboDTO crearRobo(RoboDTO dto) {
        

        Detenido detenido = detenidoDAO.findById(dto.getIdDetenido())
                .orElseThrow(() -> new RuntimeException("El detenido con id " + dto.getIdDetenido() + " no existe."));
        
        SucursalBancaria sucursal = sucursalBancariaDAO.findById(dto.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("La sucursal con id " + dto.getIdSucursal() + " no existe."));
        
        Juez juez = juezDAO.findById(dto.getIdJuez())
                .orElseThrow(() -> new RuntimeException("El juez con clave " + dto.getIdJuez() + " no existe."));

        if (dto.getFechaRobo() == null) {
             throw new RuntimeException("La fecha del robo no puede ser nula.");
        }
        if (dto.getFechaRobo().isAfter(LocalDate.now())) {
             throw new RuntimeException("La fecha del robo no puede ser una fecha futura.");
        }

        Robo entidad = convertirA_Entidad(dto, detenido, sucursal, juez);
        Robo entidadGuardada = roboDAO.save(entidad);
        
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<RoboDTO> obtenerTodos() {
        return roboDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoboDTO obtenerPorId(int id) {
        Robo entidad = roboDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Robo no encontrado con id: " + id));
        return convertirA_DTO(entidad);
    }


    @Transactional
    public RoboDTO actualizarRobo(int id, RoboDTO dto) {
        Robo entidadExistente = roboDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Robo no encontrado con id: " + id));

        Detenido detenido = detenidoDAO.findById(dto.getIdDetenido())
                .orElseThrow(() -> new RuntimeException("El detenido con id " + dto.getIdDetenido() + " no existe."));
        
        SucursalBancaria sucursal = sucursalBancariaDAO.findById(dto.getIdSucursal())
                .orElseThrow(() -> new RuntimeException("La sucursal con id " + dto.getIdSucursal() + " no existe."));
        
        Juez juez = juezDAO.findById(dto.getIdJuez())
                .orElseThrow(() -> new RuntimeException("El juez con clave " + dto.getIdJuez() + " no existe."));

        if (dto.getFechaRobo().isAfter(LocalDate.now())) {
             throw new RuntimeException("La fecha del robo no puede ser una fecha futura.");
        }
        
        // Actualizar campos
        entidadExistente.setDetenido(detenido);
        entidadExistente.setSucursal(sucursal);
        entidadExistente.setJuez(juez);
        entidadExistente.setFechaRobo(dto.getFechaRobo());
        
        Robo entidadActualizada = roboDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }


    @Transactional
    public void borrarRobo(int id) {
        if (!roboDAO.existsById(id)) {
            throw new RuntimeException("Robo no encontrado con id: " + id);
        }
        Optional<Condena> condenaAsociada = condenaDAO.findByRoboId(id);
        
        if (condenaAsociada.isPresent()) {
            condenaDAO.delete(condenaAsociada.get());
        }
        roboDAO.deleteById(id);
    }

    private RoboDTO convertirA_DTO(Robo entidad) {
        RoboDTO dto = new RoboDTO();
        dto.setId(entidad.getId());
        dto.setFechaRobo(entidad.getFechaRobo());
        
        if (entidad.getDetenido() != null) {
            dto.setIdDetenido(entidad.getDetenido().getId());
        }
        if (entidad.getSucursal() != null) {
            dto.setIdSucursal(entidad.getSucursal().getId());
        }
        if (entidad.getJuez() != null) {
            dto.setIdJuez(entidad.getJuez().getClave());
        }
        
        return dto;
    }

    private Robo convertirA_Entidad(RoboDTO dto, Detenido detenido, SucursalBancaria sucursal, Juez juez) {
        Robo entidad = new Robo();
        entidad.setFechaRobo(dto.getFechaRobo());
        entidad.setDetenido(detenido);
        entidad.setSucursal(sucursal);
        entidad.setJuez(juez);
        
        return entidad;
    }
}