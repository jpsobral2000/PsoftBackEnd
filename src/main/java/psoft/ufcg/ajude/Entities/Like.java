package psoft.ufcg.ajude.Entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Like {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Campanha campanha;

    @OneToOne(mappedBy = "like")
    Usuario usuario;

    public Like (Usuario usuario) {
        this.usuario = usuario;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }
}
