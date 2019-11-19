package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.Usuario;
import java.io.Serializable;

@Repository
public interface UsuarioRepository<T, ID extends  Serializable> extends JpaRepository <Usuario, String> {
    Usuario findByEmail(String email);

}
