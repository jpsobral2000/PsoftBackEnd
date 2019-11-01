package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Repositories.CampanhaRepository;

import java.text.Normalizer;
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
}
