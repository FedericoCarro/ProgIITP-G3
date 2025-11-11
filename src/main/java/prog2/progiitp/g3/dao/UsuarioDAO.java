package prog2.progiitp.g3.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prog2.progiitp.g3.modelos.Usuario;
import java.util.Optional;
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {
Optional<Usuario> findByDni(int dni);
boolean existsByDni(int dni);
}