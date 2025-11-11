package prog2.progiitp.g3.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prog2.progiitp.g3.dao.UsuarioDAO;
import prog2.progiitp.g3.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service // Marca esto como un servicio de Spring
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    @Transactional(readOnly = true) 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int dni;
        try {
            dni = Integer.parseInt(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("El DNI debe ser un número: " + username);
        }
        Usuario usuario = usuarioDAO.findByDni(dni)
                .orElseThrow(() -> 
                    new UsernameNotFoundException("No se encontró un usuario con el DNI: " + username)
                ); 
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (usuario.getPermiso() != null && !usuario.getPermiso().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(usuario.getPermiso()));
        } else {
            
            authorities.add(new SimpleGrantedAuthority("USUARIO_SIN_ROL")); 
        }

        return new User(
            String.valueOf(usuario.getDni()),
            usuario.getContrasenia(),
            authorities 
        );
    }
}