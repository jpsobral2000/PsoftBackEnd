package psoft.ufcg.ajude.DTO;

import psoft.ufcg.ajude.Enum.StatusCampanha;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class CampanhaDTO {

    private String descricao;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    private StatusCampanha status;

    private Double meta;

    private String nome;

    private UsuarioDTO dono;

    public CampanhaDTO(){
        this.dono = new UsuarioDTO();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public Double getMeta() {
        return meta;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public StatusCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusCampanha status) {
        this.status = status;
    }

    public void setDono(UsuarioDTO dono) {
        this.dono = dono;
    }

    public UsuarioDTO getDono() {
        return dono;
    }
}
