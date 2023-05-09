package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.SessaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.SessaoRequest;
import com.dbc.assembleia.transportlayer.data.response.SessaoResponse;
import com.dbc.assembleia.transportlayer.mapper.SessaoMapper;
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
@RequestMapping("/v1/sessao")
public class SessaoControllerImpl {

    private final Logger LOG = LoggerFactory.getLogger(SessaoControllerImpl.class);

    private final SessaoUseCase sessaoUseCase;

    public SessaoControllerImpl(SessaoUseCase sessaoUseCase) {
        this.sessaoUseCase = sessaoUseCase;
    }

    @PostMapping
    public ResponseEntity<SessaoResponse> cadastraPauta(@RequestBody @Valid SessaoRequest sessaoRequest){
        LOG.info("Cadastrar nova sessao {}", sessaoRequest);
        var sessaoToSave = SessaoMapper.INSTANCE.fromSesssaoRequest(sessaoRequest);
        var sessaoResponse =  SessaoMapper.INSTANCE.toSessaoResponse(sessaoUseCase.cadastrar(sessaoToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoResponse);
    }


}
