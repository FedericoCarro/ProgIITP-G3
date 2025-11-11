package prog2.progiitp.g3.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prog2.progiitp.g3.modelos.*;
@Repository
public interface RoboDAO extends JpaRepository<Robo, Integer> {
boolean existsByDetenidoId(int detenidoId);
boolean existsBySucursalId(int sucursalId);
boolean existsByJuezClave(int juezClave);
}