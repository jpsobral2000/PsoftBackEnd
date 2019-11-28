package psoft.ufcg.ajude.DTO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(value = "Perfil do usuario")
public class PerfilDTO {

    @ApiModelProperty(value = "usuario do perfil.")
    private UsuarioDTO usuario;

    @ApiModelProperty(value = "lista das campanhas criadas pelo usuario.")
    private List<CampanhaDTO> campanhas;

    @ApiModelProperty(value = "doacoes feitas pelo usuario.")
    private List<DoacaoDTO> doacoes;

    public PerfilDTO(UsuarioDTO usuario, List<CampanhaDTO> campanha, List<DoacaoDTO> doacoes) {
        this.usuario = usuario;
        this.campanhas = campanha;
        this.doacoes = doacoes;
    }

    public List<DoacaoDTO> getDoacoes() {
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

    public void setDoacoes(List<DoacaoDTO> doacoes) {
        this.doacoes = doacoes;
    }


    public void setCampanhas(List<CampanhaDTO> campanhas) {
        this.campanhas = campanhas;
    }
}

