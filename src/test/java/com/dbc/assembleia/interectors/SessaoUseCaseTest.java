package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.exception.SessaoNaoLocalizadaException;
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

    Pauta pauta;

    @BeforeEach
    void setUp() {
        this.sessaoUseCase = new SessaoUseCase(sessaoRepository, pautaUseCase);
        pauta = new Pauta("Pauta teste 1 ", "Detalhes da Pauta");
    }

    @Test
    @DisplayName("Deve cadastrar uma nova sessao")
    void should_cadastrarSessao() {
        var sessaoToSave = new Sessao(pauta, 1);
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessaoToSave);
        var result = sessaoUseCase.cadastrar(sessaoToSave);

        assertThat(result).isNotNull();
        assertThat(sessaoToSave.getDuracao()).isEqualTo(result.getDuracao());
    }

    @Test
    @DisplayName("Deve localizar a sessao")
    void shouldLocateSessao() {
        var sessaoLocated = new Sessao(pauta, 1);
        when(sessaoRepository.findById(1)).thenReturn(Optional.of(sessaoLocated));

        var result = sessaoUseCase.locate(1);

        assertThat(result).isNotNull();
        assertThat(result.getPauta().getDescricao()).isEqualTo(sessaoLocated.getPauta().getDescricao());
        assertThat(result.getPauta().getDescricao()).isEqualTo(sessaoLocated.getPauta().getDescricao());
        assertThat(result.getDuracao()).isEqualTo(sessaoLocated.getDuracao());
    }

    @Test
    @DisplayName("Deve retornar exceção de sessao nao localizada")
    void sholudThrowsExceptionSessaoNaoLocalizadaException() {
        when(sessaoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(SessaoNaoLocalizadaException.class, () -> {
            sessaoUseCase.locate(1);
        });
    }


}