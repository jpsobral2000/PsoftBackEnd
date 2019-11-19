package psoft.ufcg.ajude.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    private String email;
    private String primeiroNome;
    private String segundoNome;
    private String numeroCartao;
    private String senha;

    @OneToMany(mappedBy = "dono",fetch = FetchType.EAGER)
    private Set<Campanha> campanhas;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private Set<LikeB> likesDados;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private Set<Doacao> doacoes;

    public Usuario () {

    }

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(Set<Doacao> doacoes) {
        this.doacoes = doacoes;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Campanha> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(Set<Campanha> campanhas) {
        this.campanhas = campanhas;
    }

    public Set<LikeB> getLikesDados() {
        return likesDados;
    }

    public void setLikesDados(Set<LikeB> likesDados) {
        this.likesDados = likesDados;
    }
}
