package psoft.ufcg.ajude.DTO;

import java.sql.Timestamp;

public class ComentarioDTO {
    private String mensagem;
    private String email;
    private Timestamp horaDeCriacao;

    public ComentarioDTO(String mensagem, String email, Timestamp hdc){
        this.mensagem = mensagem;
        this.email = email;
        this.horaDeCriacao = hdc;
    }

    public Timestamp getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(Timestamp horaDeCriacao) {
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
