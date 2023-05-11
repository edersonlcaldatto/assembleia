package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.entity.enumerator.StatusEnum;
import com.dbc.assembleia.entity.enumerator.VotoEnum;
import com.dbc.assembleia.interectors.PautaUseCase;
import com.dbc.assembleia.interectors.SessaoUseCase;
import com.dbc.assembleia.interectors.VotacaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import com.dbc.assembleia.transportlayer.data.request.VotoRequestEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VotacaoControllerImplTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    VotacaoUseCase votacaoUseCase;

    @Autowired
    PautaUseCase pautaUseCase;

    @Autowired
    SessaoUseCase sessaoUseCase;

    Pauta pautaCriada;

    Sessao sessaoCriada;

    @BeforeEach
    void setup() {
        var pautaMock = new Pauta();
        pautaMock.setDetalhe("Teste 1");
        pautaMock.setDescricao("Teste 1222");

        pautaCriada = pautaUseCase.cadastrar(pautaMock);

        var sessaoMock = new Sessao();
        sessaoMock.setPauta(pautaCriada);
        sessaoMock.setDataHoraInicio(LocalDateTime.now());
        sessaoMock.setDataHoraFim(LocalDateTime.now().plusMinutes(10));
        sessaoMock.setStatus(StatusEnum.ABERTA);

        sessaoCriada = sessaoUseCase.abrirSessao(sessaoMock);
    }

    @Test
    @DisplayName("Deve registrar o voto por meio da API")
    void ShouldRegistrarVoto() throws Exception {

        var votoMock = new VotoRequest();
        votoMock.setVoto(VotoRequestEnum.SIM);
        votoMock.setDocumento("84171232023");
        votoMock.setCodigoSessao(sessaoCriada.getId());

        String json = new ObjectMapper().writeValueAsString(votoMock);

        String VOTACAO_URL = "/v1/votacao";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(VOTACAO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult result =  mvc.perform(request)
                .andExpect( status().isCreated())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar o resultado da votação em andamento")
    void shouldGetResultadoVotacao() throws Exception {

        var voto1 = gerarVoto(VotoEnum.SIM, "84171232023");
        var voto2 = gerarVoto(VotoEnum.SIM, "34177490008");
        var voto3 = gerarVoto(VotoEnum.NAO, "40198308000");
        var voto4 = gerarVoto(VotoEnum.SIM, "57563426051");

        var votos = List.of(voto1,voto2, voto3, voto4);

        votos.forEach(votoRequest -> votacaoUseCase.computarVoto(votoRequest));

        String RESULTADO_URL = "/v1/votacao/resultado";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RESULTADO_URL.concat("/"+sessaoCriada.getId()))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result =  mvc.perform(request)
                .andExpect( status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
    }

    private Voto gerarVoto(VotoEnum voto, String documento) {
        var votoMock = new Voto();
        votoMock.setVoto(voto);
        votoMock.setDocumento(documento);
        votoMock.setSessao(sessaoCriada);
        return votoMock;
    }


}