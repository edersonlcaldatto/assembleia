package com.dbc.assembleia.repositories;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.entity.enumerator.VotoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class VotoRepositoryTest {

    @Mock
    private VotoRepository votoRepository;

    Sessao sessao;

    @BeforeEach
    void setup() {
        sessao = new Sessao(new Pauta("Teste", "Teste"), 5);
    }

    @Test
    void shouldFindAllBySessao() {

        List<Voto> votos = new ArrayList<>();
        votos.add(votoMock());
        votos.add(votoMock());
        when(votoRepository.findAllBySessao(sessao)).thenReturn(votos);
        List<Voto> result = votoRepository.findAllBySessao(sessao);
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldFindByDocumentoAndSessao() {
        var voto = votoMock();
        when(votoRepository.findByDocumentoAndSessao("123456789", sessao)).thenReturn(Optional.of(voto));
        Optional<Voto> result = votoRepository.findByDocumentoAndSessao("123456789", sessao);
        assertThat(result).isPresent();
    }

    private Voto votoMock() {
        var voto = new Voto();
        voto.setSessao(sessao);
        voto.setHoraVoto(LocalDateTime.now());
        voto.setDocumento("123456789");
        voto.setVoto(VotoEnum.SIM);

        return voto;
    }

}