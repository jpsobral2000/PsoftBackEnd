package psoft.ufcg.ajude.services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Comentario;
import psoft.ufcg.ajude.entities.RespostaComentario;
import psoft.ufcg.ajude.repositories.ComentarioRepository;
import psoft.ufcg.ajude.repositories.RespostaComentarioRepository;

import java.time.Instant;
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
        comentario.setHorarioDoPost(Date.from(Instant.now()));
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

    public ComentarioDTO respondeComentario(long id, RespostaComentario resposta) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        List<RespostaComentario> respostas = comentario.get().getRespostas();
        resposta.setComentario(comentario.get());
        resposta.setHorarioDoPost(Date.from(Instant.now()));
        respostaComentarioRepository.save(resposta);
        return new ComentarioDTO(resposta.getId(), resposta.getMensagem(), resposta.getEmailDono(), resposta.getHorarioDoPost(), null);
    }

    private ComentarioDTO transformaComentarioDTO(Comentario comentario){
        List<ComentarioDTO> respostaComentarioDTO = new ArrayList<>();

        if(comentario.getRespostas() == null)
            comentario.setRespostas(new ArrayList<>());

        if(!comentario.getRespostas().isEmpty()) {
            for (RespostaComentario respostaComentario : comentario.getRespostas()) {
                respostaComentarioDTO.add(new ComentarioDTO(respostaComentario.getId(), respostaComentario.getMensagem(), respostaComentario.getEmailDono(), respostaComentario.getHorarioDoPost(), null));
            }
        }

        return new ComentarioDTO(comentario.getId(), comentario.getMensagem(), comentario.getEmailDono(), comentario.getHorarioDoPost(), respostaComentarioDTO);
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
            ComentarioDTO comentarioDTO = new ComentarioDTO(respostaComentario.get().getId(), respostaComentario.get().getMensagem(), respostaComentario.get().getEmailDono(), respostaComentario.get().getHorarioDoPost(), null);

            respostaComentarioRepository.deleteById(id);

            return comentarioDTO;
        }

    }
}
