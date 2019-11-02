package psoft.ufcg.ajude.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.Entities.Campanha;
import psoft.ufcg.ajude.Services.CampanhaService;

import java.util.Optional;


@RestController
public class CampanhaController {

    private CampanhaService campanhaService;

    public CampanhaController(CampanhaService campanhaService){
        this.campanhaService = campanhaService;
    }

    @PostMapping("/campanha/cadastro")
    public ResponseEntity<Campanha> cadastraCampanha(@RequestBody Campanha campanha){
        Optional<Campanha> checkCampanha = campanhaService.getCampanha(campanhaService.transformaURL(campanha.getNome()));

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

}