package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Comentario;
import psoft.ufcg.ajude.Repositories.ComentarioRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    private ComentarioRepository<Comentario, Long> comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository){
        this.comentarioRepository = comentarioRepository;
    }



    public boolean verificaValidade(Comentario comentario){
        return comentario.getMensagem() != null && comentario.getEmailDono() != null && comentario.getCampanha() != null;
    }

    public ComentarioDTO adicionaComentario(Comentario comentario){
        comentario.setHoraDeCriacao(Date.from(Instant.now()));
        comentarioRepository.save(comentario);

        return new ComentarioDTO(comentario.getId(), comentario.getMensagem(), comentario.getEmailDono(), comentario.getHoraDeCriacao());
    }

    public List<ComentarioDTO> getComentarios(Campanha campanha){
        List<ComentarioDTO> comentarioDTOS = new ArrayList<>();
        List<Comentario> comentarios = this.comentarioRepository.findByCampanha(campanha);
        for(Comentario comentario: comentarios){
            comentarioDTOS.add(new ComentarioDTO(comentario.getId(), comentario.getMensagem(), comentario.getEmailDono(), comentario.getHoraDeCriacao()));
        }

        return comentarioDTOS;
    }

    public  Optional<Comentario> getCometario(Long id){
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario;


    }

    public ComentarioDTO respondeComentario(Long id, Comentario resposta) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        List<Comentario> respostas = comentario.get().getRespostas();
        respostas.add(resposta);
        return new ComentarioDTO(resposta.getId(), resposta.getMensagem(), resposta.getEmailDono())
    }
}
