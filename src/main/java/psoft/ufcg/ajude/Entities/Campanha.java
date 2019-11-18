package psoft.ufcg.ajude.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import psoft.ufcg.ajude.Enum.StatusCampanha;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Campanha implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String nome;


    private String urlCampanha;

    private String descricao;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    private StatusCampanha status;
    private Double meta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDono")
    private Usuario dono;

    @OneToMany(mappedBy = "campanha", fetch = FetchType.EAGER)
    private List<Comentario> comentarios;



    public Campanha(){
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlCampanha() {
        return urlCampanha;
    }

    public void setUrlCampanha(String urlCampanha) {
        this.urlCampanha = urlCampanha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Double getMeta() {
        return meta;
    }

    public void setMeta(Double meta) {
        this.meta = meta;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {

        return id;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
