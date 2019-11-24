package psoft.ufcg.ajude.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "Doacao")
public class DoacaoDTO {
    @ApiModelProperty(value = "email do doador.")
    private String emailUsuario;

    @ApiModelProperty(value = "nome da campanha em que esta sendo doada.")
    private String nomeCampanha;

    @ApiModelProperty(value = "valor a ser doado.")
    private Double valor;

    @ApiModelProperty(value = "valor restante que falta para cumprir a meta.")
    private Double valorRestanteParaAMeta;

    @ApiModelProperty(value = "data da doacao.")
    private Date dataDaDoacao;

    public DoacaoDTO(String nomeCampanha, String emailUsuario, Double valor, Double valorRestanteParaAMeta, Date dataDaDoacao){
        this.emailUsuario = emailUsuario;
        this.nomeCampanha = nomeCampanha;
        this.valor = valor;
        this.valorRestanteParaAMeta = valorRestanteParaAMeta;
        this.dataDaDoacao = dataDaDoacao;
    }

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public Double getValorRestanteParaAMeta() {
        return valorRestanteParaAMeta;
    }

    public void setValorRestanteParaAMeta(Double valorRestanteParaAMeta) {
        this.valorRestanteParaAMeta = valorRestanteParaAMeta;
    }

    public Date getDataDaDoacao() {
        return dataDaDoacao;
    }

    public void setDataDaDoacao(Date dataDaDoacao) {
        this.dataDaDoacao = dataDaDoacao;
    }
}
