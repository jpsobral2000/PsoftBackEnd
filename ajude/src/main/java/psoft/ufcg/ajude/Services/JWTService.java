package psoft.ufcg.ajude.Services;

import org.springframework.stereotype.Service;

import javax.servlet.ServletException;

@Service
public class JWTService {

    private  UsuarioService usuarioService;
    private final String TOKEN_KEY = "teste";

    public JWTService (UsuarioService usuarioService) {
        super();
        this.usuarioService = usuarioService;

    }

    public boolean existeUsuario (String autorizaztionHeader) throws ServletException {
        String usuario = getAutorirazacao(autorizaztionHeader);
        return false;
    }

    private String getAutorirazacao(String autorizaztionHeader) throws  ServletException{
        return "";
    }

}
