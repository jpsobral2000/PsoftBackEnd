package psoft.ufcg.ajude.controllers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psoft.ufcg.ajude.entities.Usuario;

import psoft.ufcg.ajude.services.JWTService;
import psoft.ufcg.ajude.services.UsuarioService;

import javax.servlet.ServletException;
import java.util.Optional;

@Api(value = "login api")
@RequestMapping("/api")
@RestController
public class LoginController {
    private JWTService jwtService;
    private UsuarioService usuarioService;

    public LoginController (JWTService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @ApiOperation(value = "loga no sistema da operacao")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autenticacao (@ApiParam(value = "usuario que deseja se logar") @RequestBody Usuario usuario) throws ServletException {
        Optional<Usuario> usuarioAutenticando = usuarioService.getUsuario(usuario.getEmail());

        if (!usuarioAutenticando.isPresent()) {
            throw new ServletException("Usuario nao encontrado");
        }
        verificaSenha(usuario, usuarioAutenticando);

        String token = jwtService.geraToken(usuarioAutenticando.get().getEmail());
        LoginResponse loginResponse = new LoginResponse(token);

        return new ResponseEntity<LoginResponse>(loginResponse , HttpStatus.OK);
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

        public String getToken() {
            return token;
        }
    }


}

