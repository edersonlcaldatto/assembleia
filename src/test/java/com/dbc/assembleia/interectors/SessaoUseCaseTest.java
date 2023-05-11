package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.exception.SessaoNaoLocalizadaException;
import com.dbc.assembleia.interectors.queue.sessao.SessaoEncerradaProducer;
import com.dbc.assembleia.repositories.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class SessaoUseCaseTest {

    SessaoUseCase sessaoUseCase;
    @MockBean
    SessaoRepository sessaoRepository;
    @MockBean
    PautaUseCase pautaUseCase;
    @MockBean
    SessaoEncerradaProducer sessaoEncerradaProducer;
    private Pauta pauta;

    @BeforeEach
    void setUp() {
        this.sessaoUseCase = new SessaoUseCase(sessaoRepository, pautaUseCase, sessaoEncerradaProducer);
        pauta = new Pauta("Pauta teste 1 ", "Detalhes da Pauta");
    }

    @Test
    @DisplayName("Deve abrir uma nova sessao")
    void should_abrirSessao() {
        var sessaoToSave = new Sessao();
        sessaoToSave.setPauta(pauta);
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoToSave);
        var result = sessaoUseCase.abrirSessao(sessaoToSave);

        assertThat(result).isNotNull();
        assertThat(sessaoToSave.getStatus()).isEqualTo(result.getStatus());
    }

    @Test
    @DisplayName("Deve localizar a sessao")
    void shouldLocateSessao() {
        var sessaoLocated = new Sessao();
        sessaoLocated.setId(1);
        sessaoLocated.setPauta(pauta);
        when(sessaoRepository.findById(1)).thenReturn(Optional.of(sessaoLocated));

        var result = sessaoUseCase.locate(1);

        assertThat(result).isNotNull();
        assertThat(result.getPauta().getDescricao()).isEqualTo(sessaoLocated.getPauta().getDescricao());
        assertThat(result.getPauta().getDescricao()).isEqualTo(sessaoLocated.getPauta().getDescricao());
        assertThat(result.getStatus()).isEqualTo(sessaoLocated.getStatus());
    }

    @Test
    @DisplayName("Deve retornar exceção de sessao nao localizada")
    void sholudThrowsExceptionSessaoNaoLocalizadaException() {
        when(sessaoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(SessaoNaoLocalizadaException.class, () ->
            sessaoUseCase.locate(1)
        );
    }


}