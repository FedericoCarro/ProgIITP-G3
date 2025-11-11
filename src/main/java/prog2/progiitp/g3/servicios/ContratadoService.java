package prog2.progiitp.g3.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog2.progiitp.g3.dao.ContratadoDAO;
import prog2.progiitp.g3.dao.SucursalBancariaDAO;
import prog2.progiitp.g3.dao.UsuarioDAO;
import prog2.progiitp.g3.dto.ContratadoDTO;
import prog2.progiitp.g3.modelos.Contratado;
import prog2.progiitp.g3.modelos.Jornada;
import prog2.progiitp.g3.modelos.SucursalBancaria;
import prog2.progiitp.g3.modelos.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratadoService {

    @Autowired
    private ContratadoDAO contratadoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private SucursalBancariaDAO sucursalBancariaDAO; 
    @Transactional
    public ContratadoDTO crearContrato(ContratadoDTO dto) {

        Usuario vigilante = usuarioDAO.findById(dto.getIdVigilante())
                .orElseThrow(() -> new RuntimeException("El usuario (vigilante) con id " + dto.getIdVigilante() + " no existe."));
        
        if (!"VIGILANTE".equals(vigilante.getPermiso())) {
            throw new RuntimeException("El usuario " + vigilante.getNomApe() + " no es un VIGILANTE.");
        }

        SucursalBancaria sucursal = sucursalBancariaDAO.findById(dto.getIdSucBancaria())
                .orElseThrow(() -> new RuntimeException("La sucursal con id " + dto.getIdSucBancaria() + " no existe."));

        if (dto.getHorasTrabajadas() <= 0) {
            throw new RuntimeException("Las horas trabajadas deben ser positivas.");
        }
        if (dto.getFechaInicioJornada() == null || dto.getFechaFinJornada() == null) {
            throw new RuntimeException("Las fechas de la jornada no pueden ser nulas.");
        }
        if (dto.getFechaFinJornada().isBefore(dto.getFechaInicioJornada())) {
            throw new RuntimeException("La fecha de fin de jornada no puede ser anterior a la de inicio.");
        }

        if (contratadoDAO.existsByVigilanteIdAndSucursalBancariaIdAndJornadaFechaInicio(
                dto.getIdVigilante(), dto.getIdSucBancaria(), dto.getFechaInicioJornada())) {
            throw new RuntimeException("Este vigilante ya tiene un contrato en esta sucursal para este día de inicio.");
        }
        
        Contratado entidad = convertirA_Entidad(dto, vigilante, sucursal);
        Contratado entidadGuardada = contratadoDAO.save(entidad);

        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<ContratadoDTO> obtenerTodos() {
        return contratadoDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContratadoDTO obtenerPorId(int id) {
        Contratado entidad = contratadoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + id));
        return convertirA_DTO(entidad);
    }

@Transactional(readOnly = true)
    public List<ContratadoDTO> obtenerContratosPorDniVigilante(int dniVigilante) {
        Usuario vigilante = usuarioDAO.findByDni(dniVigilante)
                .orElseThrow(() -> new RuntimeException("Error interno: No se encontró un vigilante con el DNI: " + dniVigilante));
        int vigilanteId = vigilante.getId();
        List<Contratado> contratos = contratadoDAO.findAllByVigilanteId(vigilanteId);

        return contratos.stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void borrarContrato(int id) {
        if (!contratadoDAO.existsById(id)) {
            throw new RuntimeException("Contrato no encontrado con id: " + id);
        }
        contratadoDAO.deleteById(id);
    }
    private ContratadoDTO convertirA_DTO(Contratado entidad) {
        ContratadoDTO dto = new ContratadoDTO();
        dto.setId(entidad.getId());
        dto.setConArma(entidad.isConArma());

        if (entidad.getVigilante() != null) {
            dto.setIdVigilante(entidad.getVigilante().getId());
        }
        if (entidad.getSucursalBancaria() != null) {
            dto.setIdSucBancaria(entidad.getSucursalBancaria().getId());
        }

        if (entidad.getJornada() != null) {
            dto.setHorasTrabajadas(entidad.getJornada().getHorasTrabajadas());
            dto.setFechaInicioJornada(entidad.getJornada().getFechaInicio());
            dto.setFechaFinJornada(entidad.getJornada().getFechaFin());
        }

        return dto;
    }

    private Contratado convertirA_Entidad(ContratadoDTO dto, Usuario vigilante, SucursalBancaria sucursal) {
        Contratado entidad = new Contratado();

        entidad.setConArma(dto.isConArma());

        entidad.setVigilante(vigilante);
        entidad.setSucursalBancaria(sucursal);

        Jornada jornada = new Jornada();
        jornada.setHorasTrabajadas(dto.getHorasTrabajadas());
        jornada.setFechaInicio(dto.getFechaInicioJornada());
        jornada.setFechaFin(dto.getFechaFinJornada());
        
        entidad.setJornada(jornada);

        return entidad;
    }
}