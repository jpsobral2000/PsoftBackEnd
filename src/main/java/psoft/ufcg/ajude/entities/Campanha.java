package psoft.ufcg.ajude.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import psoft.ufcg.ajude.enums.StatusCampanha;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@ApiModel(value = "Campanha")
public class Campanha implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(value = "nome da campanha.")
    private String nome;

    @ApiModelProperty(value = "url de acesso da campanha.")
    private String urlCampanha;

    @ApiModelProperty(value = "descricao da campanha.")
    private String descricao;

    @ApiModelProperty(value = "data do prazo da campanha.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @ApiModelProperty(value = "status da campanha.")
    private StatusCampanha status;

    @ApiModelProperty(value = "meta, em dinheiro, da campanha.")
    private Double meta;

    @ApiModelProperty(value = "valor acumulado ate o momento pela campanha.")
    @JsonIgnore
    private Double acumulado;

    @ApiModelProperty(value = "usuario que criou a campanha.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idDono")
    private Usuario dono;

    @ApiModelProperty(value = "lista dos comentarios da campanha.")
    @OneToMany(mappedBy = "campanha", fetch = FetchType.EAGER)
    private List<Comentario> comentarios;

    @ApiModelProperty(value = "numero de likes da campanha.")
    @OneToMany(mappedBy = "campanha",fetch = FetchType.EAGER)
    private Set<LikeB> likes;

    @ApiModelProperty(value = "doacoes realizadas para a campanha.")
    @OneToMany(mappedBy = "campanha",fetch = FetchType.EAGER)
    private Set<Doacao> doacoes;

    public Campanha(){
    }

    public void setAcumulado(Double acumulado) {
        this.acumulado = acumulado;
    }

    public Double getAcumulado() {
        return acumulado;
    }

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(Set<Doacao> doacoes) {
        this.doacoes = doacoes;
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

    public Set<LikeB> getLikes() {
        return likes;
    }

    public void setLikes(Set<LikeB> likes) {
        this.likes = likes;
    }
}
