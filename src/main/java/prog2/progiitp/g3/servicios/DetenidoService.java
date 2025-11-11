package prog2.progiitp.g3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prog2.progiitp.g3.dao.BandaOrganizadaDAO;
import prog2.progiitp.g3.dao.DetenidoDAO;
import prog2.progiitp.g3.dao.RoboDAO;
import prog2.progiitp.g3.dto.DetenidoDTO;
import prog2.progiitp.g3.modelos.BandaOrganizada;
import prog2.progiitp.g3.modelos.Detenido;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetenidoService {

    @Autowired
    private DetenidoDAO detenidoDAO;
    
    @Autowired
    private BandaOrganizadaDAO bandaOrganizadaDAO;
    
    @Autowired
    private RoboDAO roboDAO;
    @Transactional
    public DetenidoDTO crearDetenido(DetenidoDTO dto) {
        
        BandaOrganizada banda = null; 
        if (dto.getIdBO() > 0) { 
            banda = bandaOrganizadaDAO.findById(dto.getIdBO())
                    .orElseThrow(() -> new RuntimeException("La banda organizada con id " + dto.getIdBO() + " no existe."));
        }
        
        Detenido entidad = convertirA_Entidad(dto, banda); 
        Detenido entidadGuardada = detenidoDAO.save(entidad);
        
        return convertirA_DTO(entidadGuardada);
    }

 
    @Transactional(readOnly = true)
    public List<DetenidoDTO> obtenerTodos() {
        return detenidoDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public DetenidoDTO obtenerPorId(int id) {
        Detenido entidad = detenidoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Detenido no encontrado con id: " + id));
        return convertirA_DTO(entidad);
    }

    @Transactional
    public DetenidoDTO actualizarDetenido(int id, DetenidoDTO dto) {
        Detenido entidadExistente = detenidoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Detenido no encontrado con id: " + id));
        
        BandaOrganizada banda = null; 
        if (dto.getIdBO() > 0) {
            banda = bandaOrganizadaDAO.findById(dto.getIdBO())
                    .orElseThrow(() -> new RuntimeException("La banda organizada con id " + dto.getIdBO() + " no existe."));
        }

        entidadExistente.setNomApe(dto.getNomApe());
        entidadExistente.setBandaOrganizada(banda);
        
        Detenido entidadActualizada = detenidoDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarDetenido(int id) {
        if (!detenidoDAO.existsById(id)) {
            throw new RuntimeException("Detenido no encontrado con id: " + id);
        }
        
        if (roboDAO.existsByDetenidoId(id)) {
            throw new RuntimeException("No se puede borrar el detenido (ID: " + id + "), tiene robos asociados.");
        }
        
        detenidoDAO.deleteById(id);
    }

    private DetenidoDTO convertirA_DTO(Detenido entidad) {
        DetenidoDTO dto = new DetenidoDTO();
        dto.setId(entidad.getId());
        dto.setNomApe(entidad.getNomApe());

        if (entidad.getBandaOrganizada() != null) {
            dto.setIdBO(entidad.getBandaOrganizada().getId());
        }
        
        return dto;
    }

    private Detenido convertirA_Entidad(DetenidoDTO dto, BandaOrganizada banda) {
        Detenido entidad = new Detenido();
        entidad.setNomApe(dto.getNomApe());

        entidad.setBandaOrganizada(banda); 
        
        return entidad;
    }
}