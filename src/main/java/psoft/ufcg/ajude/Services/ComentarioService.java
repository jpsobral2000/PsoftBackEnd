package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Comentario;
import psoft.ufcg.ajude.Repositories.ComentarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {
    private ComentarioRepository<Comentario, Long> comentarioRepository;


    public boolean verificaValidade(Comentario comentario){
        return comentario.getMensagem() != null && comentario.getEmailDono() != null && comentario.getCampanha() != null;
    }

    public ComentarioDTO adicionaComentario(Comentario comentario){
        comentarioRepository.save(comentario);

        return new ComentarioDTO(comentario.getMensagem(), comentario.getEmailDono(), comentario.getHoraDeCriacao());
    }

    public List<ComentarioDTO> getComentarios(Campanha campanha){
        List<ComentarioDTO> comentarioDTOS = new ArrayList<>();
        List<Comentario> comentarios = this.comentarioRepository.findByCampanha(campanha);
        for(Comentario comentario: comentarios){
            comentarioDTOS.add(new ComentarioDTO(comentario.getMensagem(), comentario.getEmailDono(), comentario.getHoraDeCriacao()));
        }

        return comentarioDTOS;
    }
}
