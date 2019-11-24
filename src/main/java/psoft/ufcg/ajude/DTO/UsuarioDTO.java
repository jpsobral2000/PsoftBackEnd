package psoft.ufcg.ajude.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "usuario")
public class UsuarioDTO {

    @ApiModelProperty(value = "email do usuario.")
    private String email;

    @ApiModelProperty(value = "primeiro nome do usuario.")
    private String primeiroNome;

    @ApiModelProperty(value = "sobrenome do usuario.")
    private String segundoNome;

    public UsuarioDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }
}
