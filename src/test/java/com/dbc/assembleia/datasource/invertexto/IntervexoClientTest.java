package com.dbc.assembleia.datasource.invertexto;

import com.dbc.assembleia.AssembleiaApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AssembleiaApplication.class)
@ActiveProfiles("test")
public class IntervexoClientTest {

    @Autowired
    private InvertextoClient invertextoClient;

    WireMockServer wireMockServer = new WireMockServer(8085);

    @Value("${invertexto.token}")
    private String token;

    @Test
    void testIntervexoClient() throws Exception{

        var responseObj = new CpfValid(true, "000.000.000-00");
        var responseBody = new ObjectMapper().writeValueAsString(responseObj);

        wireMockServer.start();
        configureFor(wireMockServer.port());

        stubFor(get(urlPathMatching("/v1/validator.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        var response = invertextoClient.validarCpf(token, "52680081021");

        assertNotNull(response);
        assertTrue(response.isValid());
        assertEquals("526.800.810-21", response.getFormatted());

        wireMockServer.stop();
    }

}
