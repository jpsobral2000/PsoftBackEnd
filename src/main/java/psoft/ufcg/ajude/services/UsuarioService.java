package psoft.ufcg.ajude.services;


import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.DTO.UsuarioDTO;
import psoft.ufcg.ajude.entities.Usuario;
import psoft.ufcg.ajude.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository<Usuario, String> usuarioRepository;

    public UsuarioService(UsuarioRepository<Usuario, String> usuarioRepository){

        this.usuarioRepository = usuarioRepository;
    }

    //TODO Quando cadastrar é preciso enviar um email de boas vindas.

    public UsuarioDTO adicionaUsuario(Usuario usuario){
        this.usuarioRepository.save(usuario);
        return transformaUsuarioEmDTO(usuario);

    }

    public UsuarioDTO transformaUsuarioEmDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPrimeiroNome(usuario.getPrimeiroNome());
        usuarioDTO.setSegundoNome(usuario.getSegundoNome());

        return usuarioDTO;
    }

    public Optional<Usuario> getUsuario(String email){
        return this.usuarioRepository.findById(email);
    }


    // ajeitar
    public boolean verificaEmailValido(String email) {
        boolean arroba = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                arroba = true;
            }
            if (email.charAt(i) == '@' && arroba) {
                return false;
            }
        }
        return arroba;
    }
}
