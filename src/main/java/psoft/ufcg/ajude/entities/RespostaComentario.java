package psoft.ufcg.ajude.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;


@ApiModel(value = "resposta do comentario")
@Entity
@SequenceGenerator(name="seq", initialValue=1)
public class RespostaComentario {

    @ApiModelProperty(value = "id da resposta do comentario.")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id
    private long id;

    @ApiModelProperty(value = "mensagem digitada como resposta do comentario.")
    private String mensagem;

    @ApiModelProperty(value = "email do dono da resposta do comentario.")
    private String emailDono;

    @ApiModelProperty(value = "horario que a respota do comentario foi feito.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horarioDoPost;

    @ApiModelProperty(value = "comentario que foi respondido.")
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

    public Date getHorarioDoPost() {
        return horarioDoPost;
    }

    public void setHorarioDoPost(Date horarioDoPost) {
        this.horarioDoPost = horarioDoPost;
    }

}

