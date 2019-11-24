package psoft.ufcg.ajude.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "Doacao")
@Entity
public class Doacao {

    @ApiModelProperty(value = "id da doacao.")
    @GeneratedValue
    @Id
    private Long id;

    @ApiModelProperty(value = "usuario que fez a doacao.")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ApiModelProperty(value = "campanha na qual a doacao foi feita.")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCampanha")
    private Campanha campanha;

    @ApiModelProperty(value = "data da doacao.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDaDoacao;

    @ApiModelProperty(value = "valor a ser doado.")
    private Double valor;

    public Doacao(){}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public Date getDataDaDoacao() {
        return dataDaDoacao;
    }

    public void setDataDaDoacao(Date dataDaDoacao) {
        this.dataDaDoacao = dataDaDoacao;
    }
}
