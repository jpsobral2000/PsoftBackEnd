package psoft.ufcg.ajude.DTO;

import java.util.Date;
import java.util.List;

public class ComentarioDTO {
    private long id;
    private String mensagem;
    private String email;
    private Date horaDeCriacao;
    private List<ComentarioDTO> respostaComentarios;

    public ComentarioDTO(Long id, String mensagem, String email, Date hdc, List<ComentarioDTO> respostaComentarios){
        this.id = id;
        this.mensagem = mensagem;
        this.email = email;
        this.horaDeCriacao = hdc;
        this.respostaComentarios = respostaComentarios;
    }

    public void setRespostaComentarios(List<ComentarioDTO> respostaComentarios) {
        this.respostaComentarios = respostaComentarios;
    }

    public List<ComentarioDTO> getRespostaComentarios() {
        return respostaComentarios;
    }

    public Date getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(Date horaDeCriacao) {
        this.horaDeCriacao = horaDeCriacao;
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
