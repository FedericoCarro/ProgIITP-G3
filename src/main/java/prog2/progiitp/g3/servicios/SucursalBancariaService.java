package prog2.progiitp.g3.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog2.progiitp.g3.dao.ContratadoDAO;
import prog2.progiitp.g3.dao.EntidadBancariaDAO;
import prog2.progiitp.g3.dao.RoboDAO;
import prog2.progiitp.g3.dao.SucursalBancariaDAO;
import prog2.progiitp.g3.dto.SucursalBancariaDTO;
import prog2.progiitp.g3.modelos.EntidadBancaria;
import prog2.progiitp.g3.modelos.SucursalBancaria;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SucursalBancariaService {

    @Autowired
    private SucursalBancariaDAO sucursalBancariaDAO;
    @Autowired
    private EntidadBancariaDAO entidadBancariaDAO; 
    @Autowired
    private RoboDAO roboDAO; 
    @Autowired
    private ContratadoDAO contratadoDAO; 
    
    @Transactional
    public SucursalBancariaDTO crearSucursal(SucursalBancariaDTO dto) {
        EntidadBancaria entidadPadre = entidadBancariaDAO.findById(dto.getIdEntidadBancaria())
                .orElseThrow(() -> new RuntimeException("La entidad bancaria con id " + dto.getIdEntidadBancaria() + " no existe."));
        if (dto.getCantEmpleados() < 0) {
            throw new RuntimeException("La cantidad de empleados no puede ser negativa.");
        }

        SucursalBancaria entidad = convertirA_Entidad(dto, entidadPadre);
        SucursalBancaria entidadGuardada = sucursalBancariaDAO.save(entidad);
        
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<SucursalBancariaDTO> obtenerTodas() {
        return sucursalBancariaDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SucursalBancariaDTO obtenerPorId(int id) {
        SucursalBancaria entidad = sucursalBancariaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));
        return convertirA_DTO(entidad);
    }


    @Transactional
    public SucursalBancariaDTO actualizarSucursal(int id, SucursalBancariaDTO dto) {
        SucursalBancaria entidadExistente = sucursalBancariaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id: " + id));

        EntidadBancaria entidadPadre = entidadBancariaDAO.findById(dto.getIdEntidadBancaria())
                .orElseThrow(() -> new RuntimeException("La entidad bancaria con id " + dto.getIdEntidadBancaria() + " no existe."));

        if (dto.getCantEmpleados() < 0) {
            throw new RuntimeException("La cantidad de empleados no puede ser negativa.");
        }

        // Actualizar campos
        entidadExistente.setDomicilio(dto.getDomicilio());
        entidadExistente.setCantEmpleados(dto.getCantEmpleados());
        entidadExistente.setEntidadBancaria(entidadPadre);

        SucursalBancaria entidadActualizada = sucursalBancariaDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarSucursal(int id) {
        if (!sucursalBancariaDAO.existsById(id)) {
            throw new RuntimeException("Sucursal no encontrada con id: " + id);
        }

        if (roboDAO.existsBySucursalId(id)) {
            throw new RuntimeException("No se puede borrar la sucursal (ID: " + id + "), tiene robos asociados.");
        }
        
        if (contratadoDAO.existsBySucursalBancariaId(id)) {
            throw new RuntimeException("No se puede borrar la sucursal (ID: " + id + "), tiene contratos de vigilantes asociados.");
        }

        sucursalBancariaDAO.deleteById(id);
    }

    private SucursalBancariaDTO convertirA_DTO(SucursalBancaria entidad) {
        SucursalBancariaDTO dto = new SucursalBancariaDTO();
        dto.setId(entidad.getId());
        dto.setDomicilio(entidad.getDomicilio());
        dto.setCantEmpleados(entidad.getCantEmpleados());
        if (entidad.getEntidadBancaria() != null) {
            dto.setIdEntidadBancaria(entidad.getEntidadBancaria().getId());
        }
        
        return dto;
    }

    private SucursalBancaria convertirA_Entidad(SucursalBancariaDTO dto, EntidadBancaria entidadPadre) {
        SucursalBancaria entidad = new SucursalBancaria();
        entidad.setDomicilio(dto.getDomicilio());
        entidad.setCantEmpleados(dto.getCantEmpleados());
        entidad.setEntidadBancaria(entidadPadre);
        return entidad;
    }
}