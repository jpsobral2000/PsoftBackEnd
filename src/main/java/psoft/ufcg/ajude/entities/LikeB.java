package psoft.ufcg.ajude.entities;


import javax.persistence.*;

@Entity
public class LikeB {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

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
