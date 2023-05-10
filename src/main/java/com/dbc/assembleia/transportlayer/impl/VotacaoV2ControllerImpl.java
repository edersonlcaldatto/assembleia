package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.VotacaoByQueueUseCase;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import com.dbc.assembleia.transportlayer.data.response.VotoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/votacao")
public class VotacaoV2ControllerImpl {

    private final Logger LOG = LoggerFactory.getLogger(VotacaoV2ControllerImpl.class);
    private final VotacaoByQueueUseCase votacaoByQueueUseCase;

    public VotacaoV2ControllerImpl(VotacaoByQueueUseCase votacaoByQueueUseCase) {
        this.votacaoByQueueUseCase = votacaoByQueueUseCase;
    }

    @PostMapping
    public ResponseEntity<VotoResponse> registrarVoto(@RequestBody @Valid VotoRequest votoRequest) {

        LOG.info("Enviando voto para fila de processamento {}", votoRequest);
        var retorno = votacaoByQueueUseCase.votoPorFila(votoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

}
