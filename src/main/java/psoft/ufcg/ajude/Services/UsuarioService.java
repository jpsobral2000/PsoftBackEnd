package psoft.ufcg.ajude.Services;


import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Entities.Usuario;
import psoft.ufcg.ajude.Repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository<Usuario, String> usuarioRepository;

    public UsuarioService(UsuarioRepository<Usuario, String> usuarioRepository){

        this.usuarioRepository = usuarioRepository;
    }

    //TODO Quando cadastrar é preciso enviar um email de boas vindas.

    public Usuario adicionaUsuario(Usuario usuario){
        this.usuarioRepository.save(usuario);
        return usuario;
    }

    public Optional<Usuario> getUsuario(String email){
        return this.usuarioRepository.findById(email);
    }
}
