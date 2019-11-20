package psoft.ufcg.ajude.controllers;


import io.jsonwebtoken.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.DTO.UsuarioDTO;
import psoft.ufcg.ajude.DTO.PerfilDTO;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.services.EmailService;
import psoft.ufcg.ajude.services.JWTService;
import psoft.ufcg.ajude.services.UsuarioService;

import javax.servlet.ServletException;
import java.util.Optional;

@RestController
public class UsuarioController {
    private UsuarioService usuarioService;
    private EmailService emailService;
    private JWTService jwtService;


    public UsuarioController(UsuarioService usuarioService, EmailService emailService, JWTService jwtService){
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;

    }



    @PostMapping("/usuario/cadastro")
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> optionalUsuario = this.usuarioService.getUsuario(usuario.getEmail());

        if(optionalUsuario.isPresent())
            return new ResponseEntity<UsuarioDTO>(HttpStatus.CONFLICT);

        //verificacao nao funciona
        if (usuarioService.verificaEmailValido(usuario.getEmail())){
            return new ResponseEntity<UsuarioDTO>(HttpStatus.FAILED_DEPENDENCY);

        }
        else
            emailService.sendEmail(usuario.getEmail());

        return new ResponseEntity<UsuarioDTO>(this.usuarioService.adicionaUsuario(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<PerfilDTO> getUsuario(@PathVariable String email, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Usuario> usuario = this.usuarioService.getUsuario(email);

        if (!usuario.isPresent())
            return new ResponseEntity<PerfilDTO>(HttpStatus.NOT_FOUND);

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<PerfilDTO>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<PerfilDTO>( usuarioService.exibirPerfilUsuario(email), HttpStatus.OK);

    }

}
