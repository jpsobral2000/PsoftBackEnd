package psoft.ufcg.ajude.Entities;

import javax.persistence.*;
import java.util.Date;


@Entity
public class RespostaComentario {

    @GeneratedValue
    @Id
    private long id;
    private String mensagem;
    private String emailDono;

    @Temporal(TemporalType.TIMESTAMP)
    private Date horaDeCriacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idComentario")
    private Comentario comentario;


    public RespostaComentario(){}

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

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Date getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(Date horaDeCriacao) {
        this.horaDeCriacao = horaDeCriacao;
    }

}

