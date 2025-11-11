package prog2.progiitp.g3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prog2.progiitp.g3.dao.EntidadBancariaDAO; 
import prog2.progiitp.g3.dto.EntidadBancariaDTO;
import prog2.progiitp.g3.modelos.EntidadBancaria;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntidadBancariaService {

    @Autowired
    private EntidadBancariaDAO entidadBancariaDAO; 
    @Transactional
    public EntidadBancariaDTO crearEntidad(EntidadBancariaDTO dto) {

        Optional<EntidadBancaria> existente = entidadBancariaDAO.findByNombreIgnoreCase(dto.getNombre());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una entidad con el nombre: " + dto.getNombre());
        }
        EntidadBancaria entidad = convertirA_Entidad(dto);
        EntidadBancaria entidadGuardada = entidadBancariaDAO.save(entidad);
        return convertirA_DTO(entidadGuardada);
    }

    @Transactional(readOnly = true)
    public List<EntidadBancariaDTO> obtenerTodas() {
        return entidadBancariaDAO.findAll().stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EntidadBancariaDTO obtenerPorId(int id) {
        EntidadBancaria entidad = entidadBancariaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada con id: " + id));
        return convertirA_DTO(entidad);
    }

    @Transactional
    public EntidadBancariaDTO actualizarEntidad(int id, EntidadBancariaDTO dto) {

        EntidadBancaria entidadExistente = entidadBancariaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada con id: " + id));
        
        Optional<EntidadBancaria> existenteConNuevoNombre = entidadBancariaDAO.findByNombreIgnoreCase(dto.getNombre());
        if (existenteConNuevoNombre.isPresent() && existenteConNuevoNombre.get().getId() != id) {
             throw new RuntimeException("El nombre " + dto.getNombre() + " ya está en uso por otra entidad.");
        }

        entidadExistente.setNombre(dto.getNombre());
        entidadExistente.setDomicilioCentral(dto.getDomicilioCentral());
        
        EntidadBancaria entidadActualizada = entidadBancariaDAO.save(entidadExistente);
        return convertirA_DTO(entidadActualizada);
    }

    @Transactional
    public void borrarEntidad(int id) {
        if (!entidadBancariaDAO.existsById(id)) {
            throw new RuntimeException("Entidad no encontrada con id: " + id);
        }

        entidadBancariaDAO.deleteById(id);
    }

    private EntidadBancariaDTO convertirA_DTO(EntidadBancaria entidad) {
        EntidadBancariaDTO dto = new EntidadBancariaDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setDomicilioCentral(entidad.getDomicilioCentral());
        return dto;
    }

    private EntidadBancaria convertirA_Entidad(EntidadBancariaDTO dto) {
        EntidadBancaria entidad = new EntidadBancaria();
        entidad.setNombre(dto.getNombre());
        entidad.setDomicilioCentral(dto.getDomicilioCentral());
        return entidad;
    }
}