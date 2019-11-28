package psoft.ufcg.ajude.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.DTO.ComentarioDTO;
import psoft.ufcg.ajude.DTO.DoacaoDTO;
import psoft.ufcg.ajude.entities.Campanha;
import psoft.ufcg.ajude.entities.Comentario;
import psoft.ufcg.ajude.entities.Doacao;
import psoft.ufcg.ajude.entities.RespostaComentario;
import psoft.ufcg.ajude.enums.StatusCampanha;
import psoft.ufcg.ajude.services.CampanhaService;
import psoft.ufcg.ajude.services.ComentarioService;
import psoft.ufcg.ajude.services.JWTService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@Api(value = "Campanha api")
@RequestMapping("/api")
@RestController
public class CampanhaController<authorization> {

    private CampanhaService campanhaService;
    private JWTService jwtService;
    private ComentarioService comentarioService;

    public CampanhaController(CampanhaService campanhaService, JWTService jwtService, ComentarioService comentarioService) {
        this.campanhaService = campanhaService;
        this.jwtService = jwtService;
        this.comentarioService = comentarioService;
    }

    @ApiOperation(value = "cria uma campanha")
    @PostMapping("/campanha/cadastro")
    public ResponseEntity<CampanhaDTO> cadastraCampanha(@ApiParam(value = "usuario que deseja se logar")@RequestBody Campanha campanha, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        Optional<Campanha> checkCampanha = campanhaService.getCampanha(campanhaService.transformaURL(campanha.getNome()));


        if (!jwtService.existeUsuario(authorization)) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
        }

        if (checkCampanha.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.CONFLICT);
        }

        if (!campanhaService.dataEhValida(campanha.getDeadline()) || campanha.getDeadline() == null) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(campanha.getMeta() == null)
            return new ResponseEntity<CampanhaDTO>(HttpStatus.BAD_REQUEST);

        campanha.setStatus(StatusCampanha.ATIVA);
        String emailDono = jwtService.getEmailPorToken(authorization);

        return new ResponseEntity<CampanhaDTO>(campanhaService.adicionaCampanha(campanha, emailDono), HttpStatus.CREATED);
    }

    @ApiOperation(value = "acessa a uma determinada campanha a partir do nome dela")
    @GetMapping("/campanha/{nome}")
    public ResponseEntity<CampanhaDTO> getCampanha(@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization) throws ServletException {
        if (!jwtService.existeUsuario(authorization)) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if (campanha.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(campanhaService.transformaParaDTO(campanha.get()), HttpStatus.OK);
        }

        return new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "pesquisa uma sampanha a partir de sua substring")
    @GetMapping("/campanha/pesquisar/{nome}")
    public ResponseEntity<List<CampanhaDTO>> pesquisaPorNome(@ApiParam(value = "substring do nome da campanha")@PathVariable String nome, @ApiParam(value = "status da campanha")@RequestParam(name = "estado", defaultValue = "true") Boolean estado) throws ServletException {

        List<CampanhaDTO> campanha = campanhaService.pesquisarNome(nome, estado);


        if (!campanha.isEmpty()) {
            return new ResponseEntity<List<CampanhaDTO>>(campanha, HttpStatus.OK);
        }


        return new ResponseEntity<List<CampanhaDTO>>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "modifica uma campanha")
    @PutMapping("campanha/{nome}")
    public ResponseEntity<CampanhaDTO> editaCampanha(@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization,@ApiParam(value = "campanha modificada") @RequestBody CampanhaDTO campanha) throws ServletException {

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);


        Optional<Campanha> campanhaOptional = campanhaService.getCampanha(nome);

        if (!campanhaOptional.isPresent()) {
            return new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);
        } else {
            if (!jwtService.usuarioTemPermissao(authorization, campanhaOptional.get().getDono().getEmail())) {
                return new ResponseEntity<CampanhaDTO>(HttpStatus.UNAUTHORIZED);
            }
        }


        return new ResponseEntity<CampanhaDTO>(campanhaService.modificaCampanha(campanha, nome), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "comenta em uma campanha na qual voce tem acesso")
    @PostMapping("campanha/comentario/{nome}")
    public ResponseEntity<ComentarioDTO> comentaCampanha(@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization, @ApiParam(value = "comentario feito")@RequestBody Comentario comentario) throws ServletException {

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.UNAUTHORIZED);

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);

        if (!campanha.isPresent())
            return new ResponseEntity<ComentarioDTO>(HttpStatus.NOT_FOUND);

        comentario.setCampanha(campanha.get());
        comentario.setEmailDono(jwtService.getEmailPorToken(authorization));

        if (!comentarioService.verificaValidade(comentario))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.BAD_REQUEST);


        return new ResponseEntity<ComentarioDTO>(comentarioService.adicionaComentario(comentario), HttpStatus.CREATED);
    }

    @ApiOperation(value = "exibi os comentarios de uma campanha")
    @GetMapping("campanha/comentario/{nome}")
    public ResponseEntity<List<ComentarioDTO>> getComentarios(@ApiParam(value = "nome da campnaha")@PathVariable String nome, @RequestHeader(value = "Authorization") String authorization) throws ServletException {

        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<List<ComentarioDTO>>(HttpStatus.UNAUTHORIZED);

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);


        if (!campanha.isPresent())
            return new ResponseEntity<List<ComentarioDTO>>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<List<ComentarioDTO>>(comentarioService.getComentarios(campanha.get()), HttpStatus.OK);
    }


    @ApiOperation(value = "responde um comentario de uma campanha")
    @PostMapping("campanha/comentario/resposta/{id}")
    public ResponseEntity<ComentarioDTO> responderComentario(@ApiParam(value = "id do comentario respondido")@PathVariable Long id, @RequestHeader(value = "Authorization") String authorization, @ApiParam(value = "resposta feita")@RequestBody RespostaComentario resposta) throws ServletException {
        if (!jwtService.existeUsuario(authorization))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.UNAUTHORIZED);

        Optional<Comentario> comentario = comentarioService.getCometario(id);
        resposta.setEmailDono(jwtService.getEmailPorToken(authorization));

        if (!comentario.isPresent())
            return new ResponseEntity<ComentarioDTO>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<ComentarioDTO>(comentarioService.respondeComentario(comentario.get().getId(), resposta), HttpStatus.CREATED);
    }

    @ApiOperation(value = "deleta um comentario que voce escreveu")
    @DeleteMapping("campanha/comentario/{id}")
    public ResponseEntity<ComentarioDTO> apagarComentario (@ApiParam(value = "id do comentario")@PathVariable Long id, @RequestHeader (value = "Authorization") String authorization) throws ServletException {

        Optional<Comentario> comentario = comentarioService.getCometario(id);
        Optional<RespostaComentario> respostaComentario = comentarioService.getRespostaComentario(id);

        if(!comentario.isPresent() && !respostaComentario.isPresent())
            return new ResponseEntity<ComentarioDTO>(HttpStatus.NOT_FOUND);

        if((!jwtService.existeUsuario(authorization) || (comentario.isPresent() && !jwtService.usuarioTemPermissao(authorization, comentario.get().getEmailDono()))) || (respostaComentario.isPresent() && !jwtService.usuarioTemPermissao(authorization, respostaComentario.get().getEmailDono())))
            return new ResponseEntity<ComentarioDTO>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<ComentarioDTO>(comentarioService.deletarComentario(id), HttpStatus.OK);

    }

    @ApiOperation(value = "curti uma campanha")
    @PostMapping("campanha/curtir/{nome}")
    public ResponseEntity<CampanhaDTO> colocarCurtidaCampanha (@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader (value = "Authorization")String authorization) throws ServletException {

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);
        if (!campanha.isPresent() || !jwtService.existeUsuario(authorization))
            return new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);

        String email = jwtService.getEmailPorToken(authorization);




        return new ResponseEntity<CampanhaDTO>(campanhaService.curtirCampanha(campanha.get(), email), HttpStatus.ACCEPTED);

    }

    @ApiOperation(value = "descurti uma campnaha ja curtida anteriormente por voce")
    @DeleteMapping("campanha/curtir/{nome}")
    public ResponseEntity<CampanhaDTO> retirarCurtidaCampanha (@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader (value = "Authorization")String authorization) throws ServletException {

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);
        if (!campanha.isPresent() || !jwtService.existeUsuario(authorization))
            return new ResponseEntity<CampanhaDTO>(HttpStatus.NOT_FOUND);

        String email = jwtService.getEmailPorToken(authorization);


        return new ResponseEntity<CampanhaDTO>(campanhaService.retirarCurtirCampanha(campanha.get(), email), HttpStatus.ACCEPTED);

    }

    @ApiOperation(value = "doa um valor para uma campanha")
    @PostMapping("campanha/doar/{nome}")
    public ResponseEntity<DoacaoDTO> realizarDoacao(@ApiParam(value = "nome da campanha")@PathVariable String nome, @RequestHeader (value = "Authorization")String authorization, @ApiParam(value = "doacao feita")@RequestBody Doacao doacao) throws ServletException {

        Optional<Campanha> campanha = campanhaService.getCampanha(nome);
        if (!campanha.isPresent() || !jwtService.existeUsuario(authorization))
            return new ResponseEntity<DoacaoDTO>(HttpStatus.NOT_FOUND);

        if (doacao.getValor() == null || doacao.getValor() <= 0)
            return new ResponseEntity<DoacaoDTO>(HttpStatus.BAD_REQUEST);

        String email = jwtService.getEmailPorToken(authorization);

        return new ResponseEntity<DoacaoDTO>(campanhaService.realizarDoacaoCampanha(campanha.get(), email, doacao),HttpStatus.CREATED);

    }

    @ApiOperation(value = "exibi as principais campanhas por meta, proximidade da data de vencimento, curtidas")
    @GetMapping("campanha/principais")
    public ResponseEntity<List<CampanhaDTO>> campanhasPrinciipais(@ApiParam(value = "forma de visualizacao desejada")@RequestParam (name = "visualizacao", defaultValue = "meta") String visualizacao){
        return new ResponseEntity<List<CampanhaDTO>>(campanhaService.buscarPrincipaisCampanhas(visualizacao), HttpStatus.OK);

    }



}

