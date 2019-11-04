package psoft.ufcg.ajude.Controllers;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Enum.StatusCampanha;
import psoft.ufcg.ajude.Services.CampanhaService;
import psoft.ufcg.ajude.Services.JWTService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
public class CampanhaController {

    private CampanhaService campanhaService;
    private JWTService jwtService;

    public CampanhaController(CampanhaService campanhaService, JWTService jwtService){
        this.campanhaService = campanhaService;
        this.jwtService = jwtService;
    }

    @PostMapping("/campanha/cadastro")
    public ResponseEntity<Campanha> cadastraCampanha(@RequestBody Campanha campanha, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Campanha> checkCampanha = campanhaService.getCampanha(campanhaService.transformaURL(campanha.getNome()));

        campanha.setStatus(StatusCampanha.ATIVA);

        if(!jwtService.existeUsuario(authorization))
            return new ResponseEntity<Campanha>(HttpStatus.NOT_FOUND);

        if(!jwtService.usuarioTemPermissao(authorization, campanha.getEmailDono()))
            return new ResponseEntity<Campanha>(HttpStatus.UNAUTHORIZED);

        if(checkCampanha.isPresent())
            return new ResponseEntity<Campanha>(HttpStatus.CONFLICT);

        if(!campanhaService.dataEhValida(campanha.getDeadline()) || campanha.getDeadline() == null || campanha.getEmailDono() == null)
            return new ResponseEntity<Campanha>(HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<Campanha>(campanhaService.adicionaCampanha(campanha), HttpStatus.CREATED);
    }

    @GetMapping("/campanha/{nome}")
    public ResponseEntity<Campanha> getCampanha(@PathVariable String nome){
        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if(campanha.isPresent())
            return new ResponseEntity<Campanha>(campanha.get(), HttpStatus.OK);

        return  new ResponseEntity<Campanha>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/campanha/pesquisar/{nome}")
    public ResponseEntity<List<Campanha>> pesquisaPorNome(@PathVariable String nome, @RequestParam(name = "estado", defaultValue = "true") Boolean estado, @RequestHeader(value = "Authorization") String authorizarion) throws ServletException {
        List<Campanha> campanha = campanhaService.pesquisarNome(nome, estado);


        if (!jwtService.existeUsuario(authorizarion))
            return new ResponseEntity<List<Campanha>>(HttpStatus.UNAUTHORIZED);



        if(!campanha.isEmpty()) {
            return new ResponseEntity<List<Campanha>>(campanha, HttpStatus.OK);
        }


        return  new ResponseEntity<List<Campanha>>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/campanha/encerrar")
    public ResponseEntity<Campanha> encerraCampanha(@RequestBody Campanha campanha, @RequestHeader(value = "Authorization") String authorizarion) throws ServletException {
        Optional<Campanha> optionalCampanha = campanhaService.getCampanha(campanha.getUrlCampanha());

        if(campanha.getEmailDono() == null || campanha.getUrlCampanha() == null)
            return new ResponseEntity<Campanha>(HttpStatus.BAD_REQUEST);

        if(!optionalCampanha.isPresent() || !jwtService.existeUsuario(authorizarion))
            return new ResponseEntity<Campanha>(HttpStatus.NOT_FOUND);

        if(!jwtService.usuarioTemPermissao(authorizarion, campanha.getEmailDono()))
            return new ResponseEntity<Campanha>(HttpStatus.UNAUTHORIZED);


        return new ResponseEntity<Campanha>(campanhaService.encerraCampanha(campanha.getUrlCampanha()) , HttpStatus.OK);
    }


}