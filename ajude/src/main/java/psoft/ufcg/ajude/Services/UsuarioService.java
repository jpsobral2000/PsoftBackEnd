package psoft.ufcg.ajude.Services;


import org.springframework.stereotype.Service;
import psoft.ufcg.ajude.Entities.Usuario;
import psoft.ufcg.ajude.Repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository<Usuario, String> usuarioDAO;

    UsuarioService(UsuarioRepository<Usuario, String> usuarioDAO){
        this.usuarioDAO = usuarioDAO;
    }

    //TODO Quando cadastrar é preciso enviar um email de boas vindas.

    public Usuario adicionaUsuario(Usuario usuario){
        return this.usuarioDAO.save(usuario);
    }

    public Optional<Usuario> getUsuario(String email){
        return this.usuarioDAO.findByEmail(email);
    }
}
