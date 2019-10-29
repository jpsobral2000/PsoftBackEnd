package psoft.ufcg.ajude.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.ufcg.ajude.Entities.Usuario;

import java.util.Optional;
import java.io.Serializable;


public interface UsuarioRepository <T, ID extends  Serializable> extends JpaRepository <Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}
