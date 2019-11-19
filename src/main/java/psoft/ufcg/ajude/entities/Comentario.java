package psoft.ufcg.ajude.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class Comentario {

    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id
    private long id;
    private String mensagem;
    private String emailDono;

    @Temporal(TemporalType.TIMESTAMP)
    private Date horaDeCriacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCampanha")
    private Campanha campanha;


    @OneToMany(mappedBy = "comentario", fetch = FetchType.EAGER)
    private List<RespostaComentario> respostas;


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

    public Campanha getCampanha() {
        return campanha;
    }

    public List<RespostaComentario> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<RespostaComentario> respostas) {
        this.respostas = respostas;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public Date getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(Date horaDeCriacao) {
        this.horaDeCriacao = horaDeCriacao;
    }

}
