package psoft.ufcg.ajude.entities;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "curtida")
@Entity
public class LikeB {

    @ApiModelProperty(value = "id da curtida.")
    @GeneratedValue
    @Id
    private Long id;

    @ApiModelProperty(value = "usuario que curtiu.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ApiModelProperty(value = "campanha na qual o usuario curtiu.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCampanha")
    private Campanha campanha;

    public LikeB(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
