package psoft.ufcg.ajude.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.DTO.UsuarioDTO;
import psoft.ufcg.ajude.Entities.Usuario;
import psoft.ufcg.ajude.Services.EmailService;
import psoft.ufcg.ajude.Services.JWTService;
import psoft.ufcg.ajude.Services.UsuarioService;

import java.util.Optional;

@RestController
public class UsuarioController {
    private UsuarioService usuarioService;
    private EmailService emailService;

    public UsuarioController(UsuarioService usuarioService, EmailService emailService){
        this.emailService = emailService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String email){
        Optional<Usuario> usuario = this.usuarioService.getUsuario(email);

        if(usuario.isPresent())
            return new ResponseEntity<UsuarioDTO>(usuarioService.transformaUsuarioEmDTO(usuario.get()), HttpStatus.OK);



        return new ResponseEntity<UsuarioDTO>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/usuario/cadastro")
    public ResponseEntity<UsuarioDTO> cadastraUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> optionalUsuario = this.usuarioService.getUsuario(usuario.getEmail());

        if(optionalUsuario.isPresent())
            return new ResponseEntity<UsuarioDTO>(HttpStatus.CONFLICT);

        if (!usuarioService.verificaEmailValido(usuario.getEmail())){
            return new ResponseEntity<UsuarioDTO>(HttpStatus.FAILED_DEPENDENCY);

        }
        else
            emailService.sendEmail(usuario.getEmail());

        return new ResponseEntity<UsuarioDTO>(this.usuarioService.adicionaUsuario(usuario), HttpStatus.CREATED);
    }
}
