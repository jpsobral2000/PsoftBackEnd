package psoft.ufcg.ajude.DTO;


import psoft.ufcg.ajude.entities.Doacao;

import java.util.List;


public class PerfilDTO {

    private UsuarioDTO usuario;
    private List<CampanhaDTO> campanhas;
    private List<Doacao> doacoes;

    public PerfilDTO(UsuarioDTO usuario, List<CampanhaDTO> campanha, List<Doacao> doacoes) {
        this.usuario = usuario;
        this.campanhas = campanha;
        this.doacoes = doacoes;
    }

    public List<Doacao> getDoacoes() {
        return doacoes;
    }

    public List<CampanhaDTO> getCampanhas() {
        return campanhas;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public void setDoacoes(List<Doacao> doacoes) {
        this.doacoes = doacoes;
    }

    public void setCampanhas(List<CampanhaDTO> campanhas) {
        this.campanhas = campanhas;
    }
}

