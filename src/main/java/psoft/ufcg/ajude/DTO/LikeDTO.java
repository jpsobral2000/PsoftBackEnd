package psoft.ufcg.ajude.DTO;

import psoft.ufcg.ajude.Entities.Usuario;

public class LikeDTO {

    private Usuario usuario;

    public LikeDTO(Usuario usuario){
        this.usuario = usuario;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
