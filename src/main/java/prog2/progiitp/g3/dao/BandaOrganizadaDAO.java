package prog2.progiitp.g3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prog2.progiitp.g3.modelos.*; 
import java.util.Optional;

@Repository
public interface BandaOrganizadaDAO extends JpaRepository<BandaOrganizada, Integer> {
Optional<BandaOrganizada> findByNombreIgnoreCase(String nombre);
}
