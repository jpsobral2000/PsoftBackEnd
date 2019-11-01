package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Repositories.CampanhaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CampanhaService {

    private CampanhaRepository<Campanha, String> campanhaDAO;


    public CampanhaService(CampanhaRepository<Campanha, String> campanhaDAO){
        this.campanhaDAO = campanhaDAO;

    }

    public Campanha adicionaCampanha(Campanha campanha){
        return campanhaDAO.save(campanha);
    }

    public Optional<Campanha> getCampanha(String nome){
        List<Campanha> campanhas = this.campanhaDAO.findAll();
        String nomeCampanha = "";

        for(Campanha campanha : campanhas){
            if(transformaURL(campanha.getNome()).equals(nome)){
                nomeCampanha = campanha.getNome();
            }
        }

        return campanhaDAO.findByNome(nomeCampanha);
    }


    private String transformaURL(String nome){
        String caracEspecial = "ÁÉÍÓÚáéíóúÂÊÎÔÛâêîôûÃÕÇçÃãÕõÀÈÌÒÙàèìòùÄËÏÖÜäëïöü";
        String caracNormal   = "AEIOUaeiouAEIOUaeiouAOCcAaOoAEIOUaeiouAEIOUaeiou";
        String newNome = nome;
        String[] pontuacoes = {"´", "`", ".", ",", "~", "^", ";", ":", "_", "{", "}", "(", ")", "\\", "/", "|", "[",
                "]", "!", "?", "@", "#", "$", "%", "¨", "&", "*", "-", "+", "=", "<" , ">"};

        for(String pontuacao: pontuacoes){
            newNome.replaceAll(pontuacao, " ");
        }

        for(int j = 0; j < caracEspecial.length(); j++){
            newNome.replaceAll(caracEspecial.substring(j, j+1), caracNormal.substring(j, j+1));
        }

        newNome = newNome.toLowerCase().trim().replaceAll(" ", "-");


        return newNome;


    }
}
