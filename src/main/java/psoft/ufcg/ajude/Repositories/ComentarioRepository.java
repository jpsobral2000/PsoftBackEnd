package psoft.ufcg.ajude.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Comentario;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository<T, ID extends Serializable> extends JpaRepository<Comentario, Long> {

    Optional<Comentario> findById(Long id);
    List<Comentario> findByCampanha(Campanha campanha);
}
