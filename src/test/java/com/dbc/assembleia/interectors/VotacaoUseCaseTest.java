package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.entity.enumerator.ResultadoVotacaoEnum;
import com.dbc.assembleia.entity.enumerator.StatusEnum;
import com.dbc.assembleia.entity.enumerator.VotoEnum;
import com.dbc.assembleia.exception.DocumentoInvalidoException;
import com.dbc.assembleia.exception.VotoJaRealizadoException;
import com.dbc.assembleia.interectors.queue.resultado.ResultadoProducer;
import com.dbc.assembleia.repositories.VotoRepository;
import com.dbc.assembleia.transportlayer.data.response.VotoStatusEnum;
import com.dbc.assembleia.transportlayer.mapper.SessaoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class VotacaoUseCaseTest {

    VotacaoUseCase votacaoUseCase;
    @MockBean
    VotoRepository votoRepository;
    @MockBean
    SessaoUseCase sessaoUseCase;
    @MockBean
    DocumentoUseCase documentoUseCase;
    @MockBean
    ResultadoProducer resultadoProducer;

    private Sessao sessao;
    private Voto voto1;
    private Voto voto2;
    private Voto voto3;

    @BeforeEach
    void setUp() {
        this.votacaoUseCase = new VotacaoUseCase(votoRepository,sessaoUseCase,documentoUseCase,resultadoProducer);
        Pauta pauta = new Pauta("Pauta teste", "Detalhe pauta");
        sessao = new Sessao(pauta, StatusEnum.ABERTA);

        voto1 = new Voto();
        voto1.setVoto(VotoEnum.SIM);
        voto1.setDocumento("123456781");
        voto1.setSessao(sessao);
        voto1.setHoraVoto(LocalDateTime.now().minusMinutes(10));

        voto2 = new Voto();
        voto2.setVoto(VotoEnum.SIM);
        voto2.setDocumento("123456782");
        voto2.setSessao(sessao);
        voto2.setHoraVoto(LocalDateTime.now().minusMinutes(5));

        voto3 = new Voto();
        voto3.setVoto(VotoEnum.NAO);
        voto3.setDocumento("123456783");
        voto3.setSessao(sessao);
        voto3.setHoraVoto(LocalDateTime.now().minusMinutes(1));
    }

    @Test
    @DisplayName("Deve computar o voto")
    void shouldComputarVoto() {

        doNothing().when(documentoUseCase).validarDocumento(anyString());

        when(sessaoUseCase.locate(voto1.getSessao().getId())).thenReturn(sessao);

        when(votoRepository.findByDocumentoAndSessao(anyString(), any(Sessao.class))).thenReturn(Optional.empty());

        when(votoRepository.save(any(Voto.class))).thenReturn(voto1);

        var result = votacaoUseCase.computarVoto(voto1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.status(), VotoStatusEnum.PROCESSAO);
    }

    @Test
    @DisplayName("Deve retonar voto recusado, voto fora do horário")
    void shouldReturnVotoRecusado() {

        sessao.setStatus(StatusEnum.ENCERRADA);

        doNothing().when(documentoUseCase).validarDocumento(anyString());

        when(sessaoUseCase.locate(voto1.getSessao().getId())).thenReturn(sessao);

        when(votoRepository.findByDocumentoAndSessao(anyString(), any(Sessao.class))).thenReturn(Optional.empty());

        when(votoRepository.save(any(Voto.class))).thenReturn(voto1);

        var result = votacaoUseCase.computarVoto(voto1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.status(), VotoStatusEnum.RECUSADO);
    }

    @Test
    @DisplayName("Deve retornar exception com documento inválido.")
    void shoulReturnVotoComDocumentoInvalido() {
        doThrow(DocumentoInvalidoException.class).when(documentoUseCase).validarDocumento(anyString());

        assertThrows(DocumentoInvalidoException.class, () -> votacaoUseCase.computarVoto(voto1));
        verifyNoInteractions(sessaoUseCase, votoRepository, resultadoProducer);
    }

    @Test
    void testComputarVotoComVotoJaRealizado() {

        doNothing().when(documentoUseCase).validarDocumento(anyString());
        when(sessaoUseCase.locate(sessao.getId())).thenReturn(sessao);

        when(votoRepository.findByDocumentoAndSessao(voto1.getDocumento(), sessao)).thenReturn(Optional.of(voto1));

        assertThrows(VotoJaRealizadoException.class, () -> votacaoUseCase.computarVoto(voto1));
        verify(documentoUseCase).validarDocumento(voto1.getDocumento());
        verify(sessaoUseCase).locate(sessao.getId());
        verify(votoRepository).findByDocumentoAndSessao(voto1.getDocumento(), sessao);
        verifyNoInteractions(resultadoProducer);
    }

    @Test
    @DisplayName("Deve retornar resultado em andamento")
    void shouldReturnResultadoEmAndamento() {

        var sessaoResponse = SessaoMapper.INSTANCE.toSessaoResponse(sessao);

        when(sessaoUseCase.locate(sessao.getId())).thenReturn(sessao);
        when(votoRepository.findAllBySessao(sessao)).thenReturn(Collections.emptyList());

        var resultado = votacaoUseCase.processarResultado(sessao.getId());

        assertEquals(sessaoResponse.status(), resultado.getSessao().status());
        assertNull(resultado.getTotalAprovado());
        assertNull(resultado.getTotalReprovado());
        assertEquals(0, resultado.getTotalVotos());
        assertEquals(ResultadoVotacaoEnum.EM_ANDAMENTO, resultado.getResultado());
    }

    @Test
    @DisplayName("Deve retornar resultado Aprovado")
    void shouldReturnResultadoAprovado() {

        sessao.setStatus(StatusEnum.ENCERRADA);

        List<Voto> votosList = List.of(voto1, voto2, voto3);

        when(sessaoUseCase.locate(sessao.getId())).thenReturn(sessao);

        when(votoRepository.findAllBySessao(sessao)).thenReturn(votosList);

        var resultado = votacaoUseCase.processarResultado(sessao.getId());

        assertEquals(ResultadoVotacaoEnum.APROVADA, resultado.getResultado());
        assertEquals(votosList.size(), resultado.getTotalVotos());
        verify(resultadoProducer, times(1)).sendResultado(resultado);
    }

}