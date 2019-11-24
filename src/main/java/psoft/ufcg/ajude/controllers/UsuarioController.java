package psoft.ufcg.ajude.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.DTO.UsuarioDTO;
import psoft.ufcg.ajude.DTO.PerfilDTO;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.services.EmailService;
import psoft.ufcg.ajude.services.JWTService;
import psoft.ufcg.ajude.services.UsuarioService;

import javax.servlet.ServletException;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:8000", maxAge = 3600)
@RequestMapping("/api")
@Api(value = "Campanha api")
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



    @ApiOperation(value = "cadastra um usuario")
    @PostMapping("/usuario/cadastro")
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@ApiParam(value = "usuario que deseja se cadastrar")@RequestBody Usuario usuario){
        Optional<Usuario> optionalUsuario = this.usuarioService.getUsuario(usuario.getEmail());

        if(optionalUsuario.isPresent())
            return new ResponseEntity<UsuarioDTO>(HttpStatus.CONFLICT);
        else
            emailService.enviaEmail(usuario.getEmail());

        return new ResponseEntity<UsuarioDTO>(this.usuarioService.adicionaUsuario(usuario), HttpStatus.CREATED);
    }

    @ApiOperation(value = "entra no perfil de um usuario")
    @GetMapping("/usuario/{email}")
    public ResponseEntity<PerfilDTO> getUsuario(@ApiParam(value = "email do usuario")@PathVariable String email, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Usuario> usuario = this.usuarioService.getUsuario(email);

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<PerfilDTO>(HttpStatus.UNAUTHORIZED);

        if (!usuario.isPresent()) {
            return new ResponseEntity<PerfilDTO>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<PerfilDTO>( usuarioService.exibirPerfilUsuario(email), HttpStatus.OK);

    }

    @ApiOperation(value = "pega o dono do token")
    @GetMapping("/usuario/propietario")
    public ResponseEntity<PerfilDTO> usuarioDonoDoToken(@RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Usuario> usuario = this.usuarioService.getUsuario(jwtService.getEmailPorToken(authorization));

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<PerfilDTO>(HttpStatus.UNAUTHORIZED);


        if(!usuario.isPresent())
            return new ResponseEntity<PerfilDTO>(HttpStatus.NOT_FOUND);

        System.out.println(jwtService.getEmailPorToken(authorization));
        return new ResponseEntity<PerfilDTO>(usuarioService.exibirPerfilUsuario(jwtService.getEmailPorToken(authorization)), HttpStatus.OK);
    }

}
