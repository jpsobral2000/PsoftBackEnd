package psoft.ufcg.ajude.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "Comentario")
public class ComentarioDTO {

    @ApiModelProperty(value = "id do comentario.")
    private long id;

    @ApiModelProperty(value = "mensagem digitada como comentario.")
    private String mensagem;

    @ApiModelProperty(value = "email do dono do comentario.")
    private String email;

    @ApiModelProperty(value = "horario que o comentario foi feito.")
    private Date horarioDoPost;

    @ApiModelProperty(value = "lista das respostas do comentario.")
    private List<ComentarioDTO> respostaComentarios;

    public ComentarioDTO(Long id, String mensagem, String email, Date hdc, List<ComentarioDTO> respostaComentarios){
        this.id = id;
        this.mensagem = mensagem;
        this.email = email;
        this.horarioDoPost = hdc;
        this.respostaComentarios = respostaComentarios;
    }

    public void setRespostaComentarios(List<ComentarioDTO> respostaComentarios) {
        this.respostaComentarios = respostaComentarios;
    }

    public List<ComentarioDTO> getRespostaComentarios() {
        return respostaComentarios;
    }

    public Date getHoraDeCriacao() {
        return horarioDoPost;
    }

    public void setHoraDeCriacao(Date horaDeCriacao) {
        this.horarioDoPost = horaDeCriacao;
    }


    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
