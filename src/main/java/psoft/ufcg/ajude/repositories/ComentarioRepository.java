package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Comentario;

import java.io.Serializable;
import java.util.List;

@Repository
public interface ComentarioRepository<T, ID extends Serializable> extends JpaRepository<Comentario, Long> {

    List<Comentario> findByCampanha(Campanha campanha);
}
