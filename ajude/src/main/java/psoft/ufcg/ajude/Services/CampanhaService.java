package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Enum.StatusCampanha;
import psoft.ufcg.ajude.Repositories.CampanhaRepository;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CampanhaService {

    private CampanhaRepository<Campanha, String> campanhaDAO;


    public CampanhaService(CampanhaRepository<Campanha, String> campanhaDAO){
        this.campanhaDAO = campanhaDAO;

    }

    public Campanha adicionaCampanha(Campanha campanha){
        campanha.setUrlCampanha(transformaURL(campanha.getNome()));
        return campanhaDAO.save(campanha);
    }

    public Campanha encerraCampanha(String urlCampanha){
        Campanha campanha = this.getCampanha(urlCampanha).get();
        campanha.setStatus(StatusCampanha.ENCERRADA);


        return  campanhaDAO.save(campanha);
    }

    public boolean dataEhValida(Date data){
        Date thisTime = new Date();
        if(thisTime.before(data))
            return true;

        return false;
    }

    public Optional<Campanha> getCampanha(String nomeUrl) {
        return this.campanhaDAO.findById(nomeUrl);
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


    public String pesquisarNome (String substring) {
        String result = "";
        List<Campanha> campanhas = campanhaDAO.findAll();
        for (Campanha campanha : campanhas) {
            if (campanha.getNome().toLowerCase().contains(substring.toLowerCase()) && campanha.getStatus().equals(StatusCampanha.ATIVA)){
                result += campanha.getNome() + "\n";
            }
        }
        return result;
    }

}