package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.LikeB;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface LikeRepository<T, ID extends Serializable> extends JpaRepository<LikeB, Long> {
    Optional<LikeB> findByUsuarioEmailAndCampanha(String email, Campanha campanha);
}
