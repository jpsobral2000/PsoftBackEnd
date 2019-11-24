package psoft.ufcg.ajude.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ApiModel(value = "Comentario")
@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class Comentario {

    @ApiModelProperty(value = "id do comentario.")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id
    private long id;

    @ApiModelProperty(value = "mensagem digitada como comentario.")
    private String mensagem;

    @ApiModelProperty(value = "email do dono do comentario.")
    private String emailDono;

    @ApiModelProperty(value = "horario que o comentario foi feito.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horarioDoPost;

    @ApiModelProperty(value = "campanha em que foi feita o comentario.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCampanha")
    private Campanha campanha;

    @ApiModelProperty(value = "lista das respostas do comentario.")
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

    public Date getHorarioDoPost() {
        return horarioDoPost;
    }

    public void setHorarioDoPost(Date horarioDoPost) {
        this.horarioDoPost = horarioDoPost;
    }

}
