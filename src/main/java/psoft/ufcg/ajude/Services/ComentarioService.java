package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.DTO.RespostaComentarioDTO;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Comentario;
import psoft.ufcg.ajude.Entities.RespostaComentario;
import psoft.ufcg.ajude.Repositories.ComentarioRepository;
import psoft.ufcg.ajude.Repositories.RespostaComentarioRepository;

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
    private RespostaComentarioRepository<RespostaComentario, Long> respostaComentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository, RespostaComentarioRepository respostaComentarioRepository){
        this.respostaComentarioRepository = respostaComentarioRepository;
        this.comentarioRepository = comentarioRepository;
    }



    public boolean verificaValidade(Comentario comentario){
        return comentario.getMensagem() != null && comentario.getEmailDono() != null && comentario.getCampanha() != null;
    }

    public ComentarioDTO adicionaComentario(Comentario comentario){
        comentario.setHoraDeCriacao(Date.from(Instant.now()));
        comentarioRepository.save(comentario);

        return transformaComentarioDTO(comentario);
    }

    public List<ComentarioDTO> getComentarios(Campanha campanha){
        List<ComentarioDTO> comentarioDTOS = new ArrayList<>();
        List<Comentario> comentarios = this.comentarioRepository.findByCampanha(campanha);
        for(Comentario comentario: comentarios){
            comentarioDTOS.add(transformaComentarioDTO(comentario));
        }

        return comentarioDTOS;
    }

    public  Optional<Comentario> getCometario(Long id){
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario;
    }

    public Optional<RespostaComentario> getRespostaComentario(Long id){
        Optional<RespostaComentario> respostaComentario = respostaComentarioRepository.findById(id);
        return respostaComentario;
    }

    public RespostaComentarioDTO respondeComentario(long id, RespostaComentario resposta) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        List<RespostaComentario> respostas = comentario.get().getRespostas();
        resposta.setComentario(comentario.get());
        resposta.setHoraDeCriacao(Date.from(Instant.now()));
        respostaComentarioRepository.save(resposta);
        respostas.add(resposta);
        return new RespostaComentarioDTO(resposta.getId(), resposta.getMensagem(), resposta.getEmailDono(), resposta.getHoraDeCriacao());
    }

    private ComentarioDTO transformaComentarioDTO(Comentario comentario){
        List<RespostaComentarioDTO> respostaComentarioDTO = new ArrayList<>();

        if(comentario.getRespostas() == null)
            comentario.setRespostas(new ArrayList<>());

        if(!comentario.getRespostas().isEmpty()) {
            for (RespostaComentario respostaComentario : comentario.getRespostas()) {
                respostaComentarioDTO.add(transformaRespostaComentarioDTO(respostaComentario));
            }
        }

        return new ComentarioDTO(comentario.getId(), comentario.getMensagem(), comentario.getEmailDono(), comentario.getHoraDeCriacao(), respostaComentarioDTO);
    }

    private RespostaComentarioDTO transformaRespostaComentarioDTO(RespostaComentario respostaComentario){
        return new RespostaComentarioDTO(respostaComentario.getId(), respostaComentario.getMensagem(), respostaComentario.getEmailDono(), respostaComentario.getHoraDeCriacao());
    }

    public ComentarioDTO deletarComentario(Long id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        Optional<RespostaComentario> respostaComentario = respostaComentarioRepository.findById(id);

        if(comentario.isPresent()) {
            ComentarioDTO comentarioDTO = transformaComentarioDTO(comentario.get());

            if (!comentario.get().getRespostas().isEmpty()) {
                for (RespostaComentario respostaC : comentario.get().getRespostas()) {
                    respostaComentarioRepository.deleteById(respostaC.getId());
                }

                comentario.get().setRespostas(new ArrayList<RespostaComentario>());

            }

            comentarioRepository.deleteById(id);

            return comentarioDTO;
        }
        else{
            ComentarioDTO comentarioDTO = new ComentarioDTO(respostaComentario.get().getId(), respostaComentario.get().getMensagem(), respostaComentario.get().getEmailDono(), respostaComentario.get().getHoraDeCriacao(), null);

            respostaComentarioRepository.deleteById(id);

            return comentarioDTO;
        }

    }
}