package psoft.ufcg.ajude.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import psoft.ufcg.ajude.DTO.CampanhaDTO;
import psoft.ufcg.ajude.entities.Campanha;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long> {
    List<Campanha> findByDonoEmail(String email);
    Optional<Campanha> findByUrlCampanha(String urlCampanha);

    @Query("SELECT C FROM Campanha AS C WHERE C.urlCampanha LIKE %?1%")
    List<Campanha> pesquisarSubstring(String substring);

//    @Query("SELECT CAMP.C FROM (SELECT C, C.meta - C.acumulado AS diferenca from Campanha as C ORDER BY diferenca) AS CAMP")
//    List<Campanha> pesquisarOrdenadosPorValorQueFalta();

}