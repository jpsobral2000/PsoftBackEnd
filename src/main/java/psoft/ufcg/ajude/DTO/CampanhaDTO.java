package psoft.ufcg.ajude.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import psoft.ufcg.ajude.enums.StatusCampanha;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@ApiModel(value = "Campanha")
public class CampanhaDTO {

    @ApiModelProperty(value = "nome da campanha.")
    private String nome;

    @ApiModelProperty(value = "descricao da campanha.")
    private String descricao;

    @ApiModelProperty(value = "data do prazo da campanha.")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ApiModelProperty(value = "valor acumulado ate o momento pela campanha.")
    private Double Acumulado;

    @ApiModelProperty(value = "status da campanha.")
    private StatusCampanha status;

    @ApiModelProperty(value = "meta, em dinheiro, da campanha.")
    private Double meta;

    @ApiModelProperty(value = "usuario que criou a campanha.")
    private UsuarioDTO dono;

    @ApiModelProperty(value = "numero de likes da campanha.")
    private Long likes;

    public CampanhaDTO(){
        this.dono = new UsuarioDTO();
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
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

    public Double getAcumulado() {
        return Acumulado;
    }

    public void setAcumulado(Double acumulado) {
        Acumulado = acumulado;
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
