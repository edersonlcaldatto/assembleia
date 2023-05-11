package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.exception.PautaNaoLocalizadaException;
import com.dbc.assembleia.repositories.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class PautaUseCaseTest {

    PautaUseCase pautaUseCase;

    @MockBean
    private PautaRepository pautaRepository;
    Pauta pauta;
    @BeforeEach
    void setUp() {
        this.pautaUseCase = new PautaUseCase(pautaRepository);
        pauta = new Pauta("Pauta teste 1", "Datalhe da Pauta");
    }
    @Test
    void testCadastrar() {
        Pauta pautaToSave = pauta;
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaToSave);

        Pauta result = pautaUseCase.cadastrar(pautaToSave);

        assertThat(result).isNotNull();
        assertThat(result.getDetalhe()).isEqualTo(pautaToSave.getDetalhe());
        assertThat(result.getDescricao()).isEqualTo(pautaToSave.getDescricao());
    }

    @Test
    void testFind() {
        when(pautaRepository.findById(1)).thenReturn(Optional.of(pauta));

        Pauta result = pautaUseCase.find(1);

        assertThat(result).isNotNull();
        assertThat(result.getDetalhe()).isEqualTo(pauta.getDetalhe());
        assertThat(result.getDescricao()).isEqualTo(pauta.getDescricao());
    }

    @Test
    void testFind_PautaNaoLocalizadaException() {
        when(pautaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PautaNaoLocalizadaException.class, () -> pautaUseCase.find(1));
    }
}
