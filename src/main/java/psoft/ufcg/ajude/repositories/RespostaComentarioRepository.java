package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.entities.RespostaComentario;

import java.io.Serializable;

@Repository
public interface RespostaComentarioRepository<T, ID extends Serializable> extends JpaRepository<RespostaComentario, Long> {



}
