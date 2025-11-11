package prog2.progiitp.g3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prog2.progiitp.g3.dao.BandaOrganizadaDAO;
import prog2.progiitp.g3.dto.BandaOrganizadaDTO;
import prog2.progiitp.g3.modelos.BandaOrganizada;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BandaOrganizadaService {

    @Autowired
    private BandaOrganizadaDAO bandaOrganizadaDAO;


    @Transactional
    public BandaOrganizadaDTO crearBanda(BandaOrganizadaDTO dto) {
        Optional<BandaOrganizada> existente = bandaOrganizadaDAO.findByNombreIgnoreCase(dto.getNombre());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una banda con el nombre: " + dto.getNombre());
        }

        if (dto.getCantMiembros() <= 0) {
            throw new RuntimeException("La cantidad de miembros debe ser mayor a cero.");
        }
        BandaOrganizada entidad = convertirA_Entidad(dto);
        BandaOrganizada entidadGuardada = bandaOrganizadaDAO.save(entidad);
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<BandaOrganizadaDTO> obtenerTodas() {
        return bandaOrganizadaDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BandaOrganizadaDTO obtenerPorId(int id) {
        BandaOrganizada entidad = bandaOrganizadaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Banda no encontrada con id: " + id));
        return convertirA_DTO(entidad);
    }


    @Transactional
    public BandaOrganizadaDTO actualizarBanda(int id, BandaOrganizadaDTO dto) {

        BandaOrganizada entidadExistente = bandaOrganizadaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Banda no encontrada con id: " + id));
          Optional<BandaOrganizada> existenteConNuevoNombre = bandaOrganizadaDAO.findByNombreIgnoreCase(dto.getNombre());
        if (existenteConNuevoNombre.isPresent() && existenteConNuevoNombre.get().getId() != id) {
             throw new RuntimeException("El nombre " + dto.getNombre() + " ya está en uso por otra banda.");
        }

        entidadExistente.setNombre(dto.getNombre());
        entidadExistente.setCantMiembros(dto.getCantMiembros());
        
        BandaOrganizada entidadActualizada = bandaOrganizadaDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarBanda(int id) {
        if (!bandaOrganizadaDAO.existsById(id)) {
            throw new RuntimeException("Banda no encontrada con id: " + id);
        }
        bandaOrganizadaDAO.deleteById(id);
    }

    private BandaOrganizadaDTO convertirA_DTO(BandaOrganizada entidad) {
        BandaOrganizadaDTO dto = new BandaOrganizadaDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setCantMiembros(entidad.getCantMiembros());
        return dto;
    }

    private BandaOrganizada convertirA_Entidad(BandaOrganizadaDTO dto) {
        BandaOrganizada entidad = new BandaOrganizada();
        entidad.setNombre(dto.getNombre());
        entidad.setCantMiembros(dto.getCantMiembros());
        return entidad;
    }
}