package psoft.ufcg.ajude.Controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Entities.Comentario;
import psoft.ufcg.ajude.Enum.StatusCampanha;
import psoft.ufcg.ajude.Services.CampanhaService;
import psoft.ufcg.ajude.Services.ComentarioService;
import psoft.ufcg.ajude.Services.JWTService;
import psoft.ufcg.ajude.Services.UsuarioService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
public class CampanhaController {

    private CampanhaService campanhaService;
    private JWTService jwtService;
    private ComentarioService comentarioService;

    public CampanhaController(CampanhaService campanhaService, JWTService jwtService, ComentarioService comentarioService){
        this.campanhaService = campanhaService;
        this.jwtService = jwtService;
        this.comentarioService = comentarioService;
    }

    @PostMapping("/campanha/cadastro")
    public ResponseEntity<CampanhaDTO> cadastraCampanha(@RequestBody Campanha campanha, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Campanha> checkCampanha = campanhaService.getCampanha(campanhaService.transformaURL(campanha.getNome()));


        if(!jwtService.existeUsuario(authorization)) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
        }

        if(checkCampanha.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.CONFLICT);
        }

        if(!campanhaService.dataEhValida(campanha.getDeadline()) || campanha.getDeadline() == null) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        campanha.setStatus(StatusCampanha.ATIVA);
        String emailDono = jwtService.getUsuarioToken(authorization);

        return new ResponseEntity<CampanhaDTO>(campanhaService.adicionaCampanha(campanha, emailDono), HttpStatus.CREATED);
    }

    @GetMapping("/campanha/{nome}")
    public ResponseEntity<CampanhaDTO> getCampanha(@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        if (!jwtService.existeUsuario(authorization)){
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if(campanha.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(campanhaService.transformaParaDTO(campanha.get()), HttpStatus.OK);
        }

        return  new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/campanha/pesquisar/{nome}")
    public ResponseEntity<List<CampanhaDTO>> pesquisaPorNome(@PathVariable String nome, @RequestParam(name = "estado", defaultValue = "true") Boolean estado, @RequestHeader(value = "Authorization") String authorizarion) throws ServletException {


        if (!jwtService.existeUsuario(authorizarion)) {
            return new ResponseEntity<List<CampanhaDTO>>(HttpStatus.UNAUTHORIZED);
        }

        List<CampanhaDTO> campanha = campanhaService.pesquisarNome(nome, estado);


        if(!campanha.isEmpty()) {
            return new ResponseEntity<List<CampanhaDTO>>(campanha, HttpStatus.OK);
        }


        return  new ResponseEntity<List<CampanhaDTO>>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("campanha/{nome}")
    public ResponseEntity<CampanhaDTO> editaCampanha(@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization, @RequestBody CampanhaDTO campanha) throws ServletException {

        if(!jwtService.existeUsuario(authorization))
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);


        Optional<Campanha> campanhaOptional = campanhaService.getCampanha(nome);

        if(!campanhaOptional.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);
        }

        else {
            if(!jwtService.usuarioTemPermissao(authorization, campanhaOptional.get().getDono().getEmail())) {
                return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
            }
        }


        return new ResponseEntity<CampanhaDTO>(campanhaService.modificaCampanha(campanha, nome), HttpStatus.ACCEPTED);
    }

    @PostMapping("campanha/comentario/{nome}")
    public ResponseEntity<ComentarioDTO> comentaCampanha(@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization, @RequestBody Comentario comentario) throws ServletException {

        if(!jwtService.existeUsuario(authorization))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.UNAUTHORIZED);

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if(!campanha.isPresent())
            return new ResponseEntity<ComentarioDTO>(HttpStatus.NOT_FOUND);

        comentario.setCampanha(campanha.get());
        comentario.setEmailDono(jwtService.getUsuarioToken(authorization));

        if(!comentarioService.verificaValidade(comentario))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.BAD_REQUEST);


        return new ResponseEntity<ComentarioDTO>(comentarioService.adicionaComentario(comentario), HttpStatus.CREATED);
    }

    @GetMapping("campanha/comentario/{nome}")
    public ResponseEntity<List<ComentarioDTO>> getComentarios(@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization) throws ServletException {

        if(!jwtService.existeUsuario(authorization))
            return new ResponseEntity<List<ComentarioDTO>>(HttpStatus.UNAUTHORIZED);

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if(!campanha.isPresent())
            return new ResponseEntity<List<ComentarioDTO>>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<ComentarioDTO>>(comentarioService.getComentarios(campanha.get()), HttpStatus.OK);
    }


}