package psoft.ufcg.ajude.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.filter.TokenFilter;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {

    private  UsuarioService usuarioService;
    private final String TOKEN_KEY = "projetopsoft";

    public JWTService (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    public boolean existeUsuario (String authorizationHeader) throws ServletException {
        String usuario = getUsuarioToken(authorizationHeader);
        return usuarioService.getUsuario(usuario).isPresent();
    }

    public boolean usuarioTemPermissao (String authorizationHeader, String email) throws ServletException {
        String usuario = getUsuarioToken(authorizationHeader);
        Optional<Usuario> optionalUsuario = usuarioService.getUsuario(usuario);

        return optionalUsuario.isPresent() && optionalUsuario.get().getEmail().equals(email);
    }

    private String getUsuarioToken(String authorizationHeader) throws  ServletException{

        //Caso o authorizationHeader da requisição for null ou não começar com "Bearer " isso o torna inválido.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            throw new ServletException("Token mal formado ou inexistente!");
        }

        //Isso vai selecionar o JWT criptografado(O JWS) que está dentro do authorizationHeader.
        String signature = authorizationHeader.substring(TokenFilter.BEARER_INDEX);

        String usuario = null;

        try{
            //Isso vai desencriptografar a assinatura gerada no authorization header, transformando em JWT
            // e pegando o subject do JWT, onde se localiza a identificação do usuario.

            usuario = Jwts.parser().setSigningKey(this.TOKEN_KEY).parseClaimsJws(signature).getBody().getSubject();
        }catch (SignatureException e){
            throw new ServletException("Token nao existe ou já foi expirado!");
        }

        return usuario;
    }

    public String getEmailPorToken(String token) throws ServletException {
        return getUsuarioToken(token);
    }

    public String geraToken(String email) {
        return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() +  7 * 24 * 60 * 60 * 1000)).compact();
    }
}
