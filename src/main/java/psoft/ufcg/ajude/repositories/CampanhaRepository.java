package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.Campanha;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long> {
    List<Campanha> findByDonoEmail(String email);
    Optional<Campanha> findByUrlCampanha(String urlCampanha);
}