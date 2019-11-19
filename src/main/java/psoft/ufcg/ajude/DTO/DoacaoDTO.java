package psoft.ufcg.ajude.DTO;

import java.util.Date;

public class DoacaoDTO {
    private String emailUsuario;

    private String nomeCampanha;

    private Double valor;

    private Double valorRestanteParaAMeta;

    private Date dataDeCriacao;

    public DoacaoDTO(String nomeCampanha, String emailUsuario, Double valor, Double valorRestanteParaAMeta, Date dataDeCriacao){
        this.emailUsuario = emailUsuario;
        this.nomeCampanha = nomeCampanha;
        this.valor = valor;
        this.valorRestanteParaAMeta = valorRestanteParaAMeta;
        this.dataDeCriacao = dataDeCriacao;
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

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
}
