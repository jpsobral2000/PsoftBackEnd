package psoft.ufcg.ajude.services;


import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.DTO.DoacaoDTO;
import psoft.ufcg.ajude.DTO.PerfilDTO;
import psoft.ufcg.ajude.DTO.UsuarioDTO;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Doacao;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.repositories.CampanhaRepository;
import psoft.ufcg.ajude.repositories.DoacaoRepository;
import psoft.ufcg.ajude.repositories.UsuarioRepository;

import java.util.*;

@Service
public class UsuarioService {
    private UsuarioRepository<Usuario, String> usuarioRepository;
    private CampanhaRepository<Campanha, Long> campanhaRepository;
    private DoacaoRepository<Doacao, Long> doacaoRepository;


    public UsuarioService(UsuarioRepository<Usuario, String> usuarioRepository, CampanhaRepository campanhaRepository, DoacaoRepository doacaoRepository){

        this.usuarioRepository = usuarioRepository;
        this.doacaoRepository = doacaoRepository;
        this.campanhaRepository = campanhaRepository;
    }

    //TODO Quando cadastrar é preciso enviar um email de boas vindas.

    public UsuarioDTO adicionaUsuario(Usuario usuario){
        usuario.setDoacoes(new HashSet<Doacao>());
        this.usuarioRepository.save(usuario);
        return transformaUsuarioEmDTO(usuario);

    }

    public UsuarioDTO transformaUsuarioEmDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPrimeiroNome(usuario.getPrimeiroNome());
        usuarioDTO.setSegundoNome(usuario.getSegundoNome());

        return usuarioDTO;
    }

    public Optional<Usuario> getUsuario(String email){
        return this.usuarioRepository.findById(email);
    }


    public PerfilDTO exibirPerfilUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        UsuarioDTO usuarioDTO = transformaUsuarioEmDTO(usuario);
        List<Campanha> campanhas = campanhaRepository.findByDonoEmail(email);
        Optional<List<Doacao>> doacoes = doacaoRepository.findByUsuarioEmail(email);
        List<CampanhaDTO> campanhaDTOS = new ArrayList<CampanhaDTO>();
        List<DoacaoDTO> doacaoDTOS = new ArrayList<DoacaoDTO>();


        for(Campanha campanha: campanhas){
            campanhaDTOS.add(CampanhaService.transformaParaDTO(campanha));
        }

        if(doacoes.isPresent()){
            for(Doacao doacao : doacoes.get()){
                doacaoDTOS.add(new DoacaoDTO(email, doacao.getCampanha().getNome(),doacao.getValor(),doacao.getCampanha().getMeta() - doacao.getCampanha().getAcumulado() - doacao.getValor() , doacao.getDataDaDoacao()));
            }
        }

        return new PerfilDTO(usuarioDTO, campanhaDTOS, doacaoDTOS);
    }
}
