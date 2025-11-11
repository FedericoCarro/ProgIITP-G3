package prog2.progiitp.g3.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prog2.progiitp.g3.modelos.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratadoDAO extends JpaRepository<Contratado, Integer> {
boolean existsBySucursalBancariaId(int sucursalId);
List<Contratado> findAllByVigilanteId(int vigilanteId);
boolean existsByVigilanteIdAndSucursalBancariaIdAndJornadaFechaInicio(int vigilanteId, int sucursalId, LocalDate fechaInicio);
}