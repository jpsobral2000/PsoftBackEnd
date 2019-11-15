package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Usuario;
import psoft.ufcg.ajude.Enum.StatusCampanha;
import psoft.ufcg.ajude.Repositories.CampanhaRepository;
import psoft.ufcg.ajude.Repositories.UsuarioRepository;

import java.text.Normalizer;
import java.util.*;

@Service
public class CampanhaService {

    private CampanhaRepository<Campanha, String> campanhaRepository;
    private UsuarioRepository<Usuario, String> usuarioRepository;


    public CampanhaService(CampanhaRepository<Campanha, String> campanhaRepository, UsuarioRepository<Usuario, String> usuarioRepository){
        this.usuarioRepository = usuarioRepository;
        this.campanhaRepository = campanhaRepository;

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

        return campanhaDTO;
    }

    public CampanhaDTO adicionaCampanha(Campanha campanha, String emailDono){
        campanha.setUrlCampanha(transformaURL(campanha.getNome()));
        Usuario dono = usuarioRepository.findByEmail(emailDono);

        campanha.setDono(dono);

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

}