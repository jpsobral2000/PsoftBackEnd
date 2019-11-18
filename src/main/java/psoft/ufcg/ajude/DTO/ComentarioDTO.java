package psoft.ufcg.ajude.DTO;

import java.util.Date;

public class ComentarioDTO {
    private String mensagem;
    private String email;
    private Date horaDeCriacao;

    public ComentarioDTO(String mensagem, String email, Date hdc){
        this.mensagem = mensagem;
        this.email = email;
        this.horaDeCriacao = hdc;
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
}
