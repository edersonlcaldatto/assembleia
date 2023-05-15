package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.PautaUseCase;
import com.dbc.assembleia.transportlayer.data.request.PautaRequest;
import com.dbc.assembleia.transportlayer.data.response.PautaResponse;
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
class PautaControllerImplTest {

    private final String PAUTA_URL = "/v1/pauta";

    @Autowired
    MockMvc mvc;

    @InjectMocks
    PautaUseCase pautaUseCase;
    @Test
    @DisplayName("Deve cadastrar a Pauta por meio da API")
    void ShouldCadastrarPauta() throws Exception {

        final String descricao = "Teste de Pauta";
        final String detalhe = "Detalhe da Pauta";

        PautaRequest pautaRequest = new PautaRequest();
        pautaRequest.setDescricao(descricao);
        pautaRequest.setDetalhe(detalhe);

        String json = new ObjectMapper().writeValueAsString(pautaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PAUTA_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result =  mvc.perform(request)
                .andExpect( status().isCreated())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Assertions.assertFalse(responseJson.isEmpty());
        var pautaCriada = new ObjectMapper().readValue(responseJson, PautaResponse.class);
        Assertions.assertEquals(1, pautaCriada.id());
        Assertions.assertEquals(descricao, pautaCriada.descricao());
    }


}