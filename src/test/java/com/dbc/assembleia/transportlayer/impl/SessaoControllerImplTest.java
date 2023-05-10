package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.interectors.PautaUseCase;
import com.dbc.assembleia.interectors.SessaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.SessaoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

    private final String SESSAO_URL = "/v1/sessao";

    @Autowired
    MockMvc mvc;

    @InjectMocks
    SessaoUseCase sessaoUseCase;

    @Autowired
    PautaUseCase pautaUseCase;

    @Test
    @DisplayName("Deve cadastrar a sessão por meio da API")
    void ShouldCadastrarSessao() throws Exception {

        var pauta = new Pauta();
        pauta.setDetalhe("Teste 1");
        pauta.setDescricao("Teste 1222");
        var retorno = pautaUseCase.cadastrar(pauta);

        var sessaoRequest = new SessaoRequest();
        sessaoRequest.setCodigoPauta(retorno.getId());
        sessaoRequest.setDuracao(2);

        String json = new ObjectMapper().writeValueAsString(sessaoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(SESSAO_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult result =  mvc.perform(request)
                .andExpect( status().isCreated())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
    }
}