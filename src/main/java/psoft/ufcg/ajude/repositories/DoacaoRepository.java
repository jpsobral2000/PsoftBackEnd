package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Doacao;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DoacaoRepository<T, ID extends Serializable> extends JpaRepository<Doacao, Long> {
    Optional<Set<Doacao>> findByUsuarioEmail(String email);
    Optional<Set<Doacao>> findByCampanha(Campanha campanha);
    Optional<Set<Doacao>> findByUsuarioEmailAndCampanha(String email, Campanha campanha);
}
