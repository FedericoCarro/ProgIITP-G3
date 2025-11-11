package prog2.progiitp.g3.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prog2.progiitp.g3.modelos.EntidadBancaria;
import java.util.Optional;

@Repository
public interface EntidadBancariaDAO extends JpaRepository<EntidadBancaria, Integer>{
    Optional<EntidadBancaria> findByNombreIgnoreCase(String nombre);
}