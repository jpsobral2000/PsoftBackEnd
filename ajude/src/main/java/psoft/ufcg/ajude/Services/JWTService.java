package psoft.ufcg.ajude.Services;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Filter.TokenFilter;

import javax.servlet.ServletException;

@Service
public class JWTService {

    private  UsuarioService usuarioService;
    private final String TOKEN_KEY = "projetopsoft";

    public JWTService (UsuarioService usuarioService) {
        super();
        this.usuarioService = usuarioService;

    }

    public boolean existeUsuario (String authorizationHeader) throws ServletException {
        String usuario = getUsuarioToken(authorizationHeader);
        return usuarioService.getUsuario(usuario).isPresent();
    }

    private String getUsuarioToken(String authorizationHeader) throws  ServletException{
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            throw new ServletException("Token mal formado ou inexistente!");
        }

        String token = authorizationHeader.substring(TokenFilter.BEARER_INDEX);

        String usuario = null;

        try{
            //usuario = Jwts.parser().setSigningKey(TOKEN_KEY)
        }

        return "";
    }

}
