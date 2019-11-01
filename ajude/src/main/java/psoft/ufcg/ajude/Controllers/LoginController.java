package psoft.ufcg.ajude.Controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import psoft.ufcg.ajude.Entities.Usuario;

import psoft.ufcg.ajude.Services.JWTService;
import psoft.ufcg.ajude.Services.UsuarioService;

import javax.servlet.ServletException;
import java.util.Optional;

@RequestMapping("/autorizacao")
public class LoginController {
    private JWTService jwtService;
    private UsuarioService usuarioService;

    public LoginController (JWTService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public LoginResponse autenticacao (@RequestBody Usuario usuario) throws ServletException {
        Optional<Usuario> usuarioAutenticando = usuarioService.getUsuario(usuario.getEmail());

        if (!usuarioAutenticando.isPresent()) {
            throw new ServletException("Usuario nao encontrado");
        }
        verificaSenha(usuario, usuarioAutenticando);

        String token = jwtService.geraToken(usuarioAutenticando.get().getEmail());

        return new LoginResponse(token);
    }

    private void verificaSenha(Usuario usuario, Optional<Usuario> usuarioAutenticando) throws ServletException{
        if (!usuarioAutenticando.get().getSenha().equals(usuario.getSenha())) {
            throw new ServletException("Senha incorreta");
        }
    }


    private class LoginResponse{
        public String token;

        public LoginResponse(String token) {
            this.token = token;

        }

    }


}

