package psoft.ufcg.ajude.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.ufcg.ajude.Entities.RespostaComentario;

import java.io.Serializable;

public interface RespostaComentarioRepository<T, ID extends Serializable> extends JpaRepository<RespostaComentario, Long> {



}
