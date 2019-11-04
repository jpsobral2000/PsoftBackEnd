package psoft.ufcg.ajude.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.ufcg.ajude.Entities.Campanha;

import java.io.Serializable;
import java.util.Optional;

public interface CampanhaRepository <T, ID extends Serializable> extends JpaRepository<Campanha, String> {
}