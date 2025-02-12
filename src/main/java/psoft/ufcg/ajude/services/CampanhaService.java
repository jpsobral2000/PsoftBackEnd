package psoft.ufcg.ajude.services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.DTO.DoacaoDTO;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Doacao;
import psoft.ufcg.ajude.entities.LikeB;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.enums.StatusCampanha;
import psoft.ufcg.ajude.repositories.CampanhaRepository;
import psoft.ufcg.ajude.repositories.DoacaoRepository;
import psoft.ufcg.ajude.repositories.LikeRepository;
import psoft.ufcg.ajude.repositories.UsuarioRepository;

import javax.servlet.ServletException;
import java.text.Normalizer;
import java.time.Instant;
import java.util.*;

@Service
public class CampanhaService {

    private CampanhaRepository<Campanha, String> campanhaRepository;
    private UsuarioRepository<Usuario, String> usuarioRepository;
    private LikeRepository<LikeB, Long> likeRepository;
    private DoacaoRepository<Doacao, Long> doacaoRepository;


    public CampanhaService(DoacaoRepository<Doacao, Long> doacaoRepository, CampanhaRepository<Campanha, String> campanhaRepository, UsuarioRepository<Usuario, String> usuarioRepository, LikeRepository<LikeB, Long> likeRepository){
        this.usuarioRepository = usuarioRepository;
        this.campanhaRepository = campanhaRepository;
        this.likeRepository = likeRepository;
        this.doacaoRepository = doacaoRepository;

    }

    public CampanhaDTO modificaCampanha(CampanhaDTO campanhaDTO, String nome) {
        Campanha campanha = campanhaRepository.findByUrlCampanha(nome).get();

        if(campanhaDTO.getDescricao() != null)
            campanha.setDescricao(campanhaDTO.getDescricao());

        if(campanhaDTO.getDeadline() != null)
            campanha.setDeadline(campanhaDTO.getDeadline());

        if(campanhaDTO.getStatus() != null)
            campanha.setStatus(campanhaDTO.getStatus());

        if(campanhaDTO.getMeta() != null)
            campanha.setMeta(campanhaDTO.getMeta());

        campanhaRepository.save(campanha);

        campanhaDTO = transformaParaDTO(campanha);

        return campanhaDTO;
    }

    public CampanhaDTO transformaParaDTO(Campanha campanha){
        CampanhaDTO campanhaDTO = new CampanhaDTO();

        campanhaDTO.setNome(campanha.getNome());
        campanhaDTO.setDescricao(campanha.getDescricao());
        campanhaDTO.setDeadline(campanha.getDeadline());
        campanhaDTO.setStatus(campanha.getStatus());
        campanhaDTO.setMeta(campanha.getMeta());
        campanhaDTO.getDono().setPrimeiroNome(campanha.getDono().getPrimeiroNome());
        campanhaDTO.getDono().setSegundoNome(campanha.getDono().getSegundoNome());
        campanhaDTO.getDono().setEmail(campanha.getDono().getEmail());
        campanhaDTO.setLikes((long) campanha.getLikes().size());

        return campanhaDTO;
    }

    public CampanhaDTO adicionaCampanha(Campanha campanha, String emailDono){
        campanha.setUrlCampanha(transformaURL(campanha.getNome()));
        Usuario dono = usuarioRepository.findByEmail(emailDono);
        campanha.setDono(dono);
        campanha.setLikes(new HashSet<LikeB>());
        campanha.setAcumulado(0.0);

        campanhaRepository.save(campanha);
        return transformaParaDTO(campanha);
    }

    public boolean dataEhValida(Date data){
        Date thisTime = new Date();
        if(thisTime.before(data))
            return true;

        return false;
    }

    public Optional<Campanha> getCampanha(String nomeUrl) {
        return this.campanhaRepository.findByUrlCampanha(nomeUrl);
    }

    public Optional<Campanha> getCampanha(long id){
        return  this.campanhaRepository.findById(id);
    }

    public String transformaURL(String nome){
        String newNome = nome;
        String[] pontuacoes = new String[]{"´", "`", ".", ",", "~", "^", ";", ":", "_", "{", "}", "(", ")", "\\", "/", "|", "[",
                "]", "!", "?", "@", "#", "$", "%", "¨", "&", "*", "-", "+", "=", "<" , ">"};

        for(String pontuacao: pontuacoes){
            if(newNome.contains(pontuacao))
                newNome = newNome.replace(pontuacao, " ");
        }

        newNome = Normalizer.normalize(newNome, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");


        String[] names = newNome.split(" ");

        newNome = "";
        for(int i = 0; i < names.length; i++){
            if(!names[i].isEmpty()){
                if(i == 0)
                    newNome += names[i];
                else
                    newNome += "-" + names[i];
            }
        }

        newNome = newNome.toLowerCase();

        return newNome;

    }

    public List<CampanhaDTO> pesquisarNome (String substring, Boolean estado) {
        substring = transformaURL(substring);

        List<CampanhaDTO> result = new ArrayList<>();
        List<Campanha> campanhas = campanhaRepository.findAll();
        for (Campanha campanha : campanhas) {

            if(estado) {
                if (transformaURL(campanha.getNome()).contains(substring.toLowerCase()) && campanha.getStatus().equals(StatusCampanha.ATIVA)) {
                    result.add(transformaParaDTO(campanha));
                }
            }
            else{
                if (transformaURL(campanha.getNome()).contains(substring.toLowerCase())) {
                    result.add(transformaParaDTO(campanha));
                }
            }
        }
        return result;
    }

    public CampanhaDTO curtirCampanha(Campanha campanha, String email) throws ServletException {

        if(likeRepository.findByUsuarioEmailAndCampanha(email, campanha).isPresent())
            throw new ServletException("Campanha já foi curtida pelo usuario!");

        LikeB like = new LikeB();
        like.setCampanha(campanha);
        like.setUsuario(usuarioRepository.findByEmail(email));
        likeRepository.save(like);

        return  transformaParaDTO(campanha);
    }

    public CampanhaDTO retirarCurtirCampanha(Campanha campanha, String email) throws ServletException {
        Optional<LikeB> likeB = likeRepository.findByUsuarioEmailAndCampanha(email, campanha);

        if(!likeB.isPresent())
            throw new ServletException("Campanha não foi curtida pelo usuario!");

        likeRepository.delete(likeB.get());

        return  transformaParaDTO(campanha);
    }

    public DoacaoDTO realizarDoacaoCampanha(Campanha campanha, String email, Doacao doacao) throws ServletException {

        if(campanha.getStatus().equals(StatusCampanha.CONCLUIDA) || campanha.getStatus().equals(StatusCampanha.ENCERRADA))
            throw new ServletException("Não é possível realizar doação para campanhas concluidas ou encerradas");

        doacao.setCampanha(campanha);
        Usuario usuario = usuarioRepository.findByEmail(email);
        doacao.setUsuario(usuario);
        doacao.setDate(Date.from(Instant.now()));
        doacaoRepository.save(doacao);
        campanha.setAcumulado(campanha.getAcumulado() + doacao.getValor());

        Double faltaParaCampanha = 0.0;

        if(campanha.getMeta() - campanha.getAcumulado() > 0){
            faltaParaCampanha = campanha.getMeta() - campanha.getAcumulado();
        }
        else{
            faltaParaCampanha = 0.0;
            campanha.setStatus(StatusCampanha.VENCIDA);
        }

        campanhaRepository.save(campanha);

        DoacaoDTO doacaoDTO = new DoacaoDTO(campanha.getNome(), usuario.getEmail(), doacao.getValor(), faltaParaCampanha, doacao.getDate());

        return doacaoDTO;

    }
}