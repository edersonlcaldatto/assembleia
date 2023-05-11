package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.interectors.PautaUseCase;
import com.dbc.assembleia.interectors.SessaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.SessaoRequest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SessaoControllerImplTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    SessaoUseCase sessaoUseCase;
    @Autowired
    PautaUseCase pautaUseCase;
    private Pauta pauta;

    @BeforeEach
    void setup() {
        pauta = new Pauta("Pauta teste", "Detalhe testes");
        pauta = pautaUseCase.cadastrar(pauta);
    }

    @Test
    @DisplayName("Deve Abrir uma nova sessão por meio da API")
    void ShouldAbrirSessao() throws Exception {

        var sessaoRequest = new SessaoRequest();
        sessaoRequest.setCodigoPauta(pauta.getId());

        String json = new ObjectMapper().writeValueAsString(sessaoRequest);

        String ABRIR_SESSAO_URL = "/v1/sessao";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ABRIR_SESSAO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult result =  mvc.perform(request)
                .andExpect( status().isCreated())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
    }

    @Test
    @DisplayName("Deve encerrar a sessão ")
    void ShouldCadastrarSessao() throws Exception {

        var sessao = new Sessao();
        sessao.setPauta(pauta);

        sessaoUseCase.abrirSessao(sessao);
        sessaoUseCase.encerrarSessao(sessao.getId());

        var sessaoRequest = new SessaoRequest();
        sessaoRequest.setCodigoPauta(pauta.getId());

        String json = new ObjectMapper().writeValueAsString(sessaoRequest);

        String FECHAR_SESSAO_URL = "/v1/sessao/encerrar/";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(FECHAR_SESSAO_URL.concat(sessao.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult result =  mvc.perform(request)
                .andExpect( status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
    }
}