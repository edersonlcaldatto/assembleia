package com.dbc.assembleia.repositories;

import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {

    @Query(value = "SELECT * FROM VOTO WHERE ID_SESSAO = ?1", nativeQuery = true)
    List<Voto> findAllByIdSessao(Integer idSessao);

    Optional<Voto> findByDocumentoAndSessao(String documento, Sessao sessao);

}
