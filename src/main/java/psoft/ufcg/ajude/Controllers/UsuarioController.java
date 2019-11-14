package psoft.ufcg.ajude.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Usuario> getUsuario(@PathVariable String email){
        Optional<Usuario> usuario = this.usuarioService.getUsuario(email);

        if(usuario.isPresent())
            return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);

        return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/usuario/cadastro")
    public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario usuario){
        Optional<Usuario> optionalUsuario = this.usuarioService.getUsuario(usuario.getEmail());

        if(optionalUsuario.isPresent())
            return new ResponseEntity<Usuario>(HttpStatus.CONFLICT);

        return new ResponseEntity<Usuario>(this.usuarioService.adicionaUsuario(usuario), HttpStatus.CREATED);
    }
}
