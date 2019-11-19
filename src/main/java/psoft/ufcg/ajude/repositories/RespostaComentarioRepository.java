package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.ufcg.ajude.entities.RespostaComentario;

import java.io.Serializable;

public interface RespostaComentarioRepository<T, ID extends Serializable> extends JpaRepository<RespostaComentario, Long> {



}
