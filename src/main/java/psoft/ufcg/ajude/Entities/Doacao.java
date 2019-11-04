package psoft.ufcg.ajude.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class Doacao {

    private String emailDono;
    private Double valorDoado;

    public Doacao(){}

    public String getEmailDono() {
        return emailDono;
    }

    public void setEmailDono(String emailDono) {
        this.emailDono = emailDono;
    }

    public Double getValorDoado() {
        return valorDoado;
    }

    public void setValorDoado(Double valorDoado) {
        this.valorDoado = valorDoado;
    }
}
