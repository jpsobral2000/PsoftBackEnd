package psoft.ufcg.ajude.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class Comentario {

    @GeneratedValue
    @Id
    private long id;
    private String mensagem;
    private String emailDono;

    @Temporal(TemporalType.TIMESTAMP)
    private Date horaDeCriacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCampanha")
    private Campanha campanha;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Comentario> respostas;



    public Comentario(){}

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmailDono() {
        return emailDono;
    }

    public void setEmailDono(String emailDono) {
        this.emailDono = emailDono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Comentario> getRespostas() {
        return respostas;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setRespostas(List<Comentario> respostas) {
        this.respostas = respostas;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public Date getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(Timestamp horaDeCriacao) {
        this.horaDeCriacao = horaDeCriacao;
    }
}
