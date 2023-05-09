package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.PautaUseCase;
import com.dbc.assembleia.transportlayer.data.request.PautaRequest;
import com.dbc.assembleia.transportlayer.data.response.PautaResponse;
import com.dbc.assembleia.transportlayer.mapper.PautaMapper;
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
@RequestMapping("/v1/pauta")
public class PautaControllerImpl {

    private final Logger LOG = LoggerFactory.getLogger(PautaControllerImpl.class);
    private final PautaUseCase pautaUseCase;

    public PautaControllerImpl(PautaUseCase pautaUseCase) {
        this.pautaUseCase = pautaUseCase;
    }

    @PostMapping
    public ResponseEntity<PautaResponse> cadastraPauta(@Valid @RequestBody PautaRequest pautaRequest) {
        LOG.info("Cadastro de nova pauta {}", pautaRequest);
        var pauta = PautaMapper.INSTANCE.fromPautaRequest(pautaRequest);

        var pautaResponse = PautaMapper.INSTANCE.toPautaResponse( pautaUseCase.cadastrar(pauta) );
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaResponse);
    }


}
