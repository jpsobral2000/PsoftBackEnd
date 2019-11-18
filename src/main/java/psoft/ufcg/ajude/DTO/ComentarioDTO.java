package psoft.ufcg.ajude.DTO;

import java.util.Date;

public class ComentarioDTO {
    private long id;
    private String mensagem;
    private String email;
    private Date horaDeCriacao;

    public ComentarioDTO(Long id, String mensagem, String email, Date hdc){
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
