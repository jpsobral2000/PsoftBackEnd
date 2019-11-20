package psoft.ufcg.ajude.DTO;


import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Doacao;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public class PerfilDTO {

    private UsuarioDTO usuario;
    private List<Campanha> campanhas;
    private Set<Doacao> doacoes;

    public PerfilDTO(UsuarioDTO usuario, List<Campanha> campanha, Set<Doacao> doacaos) {
        this.usuario = usuario;
        this.campanhas = campanha;
        this.doacoes = doacaos;

    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public List<Campanha> getCampanhas() {
        return campanhas;
    }

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public void setCampanhas(List<Campanha> campanhas) {
        this.campanhas = campanhas;
    }

    public void setDoacoes(Set<Doacao> doacoes) {
        this.doacoes = doacoes;
    }
}

