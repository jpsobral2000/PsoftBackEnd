package psoft.ufcg.ajude.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoft.ufcg.ajude.Entities.Like;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface LikeRepository <T,ID extends Serializable> extends JpaRepository {
    List<Like> findByEmailUsuario (String email);

}
