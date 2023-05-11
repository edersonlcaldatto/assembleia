package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.SessaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.SessaoRequest;
import com.dbc.assembleia.transportlayer.data.response.SessaoResponse;
import com.dbc.assembleia.transportlayer.mapper.SessaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest sessaoRequest){
        LOG.info("Abrindo nova sessao {}", sessaoRequest);
        var sessaoToSave = SessaoMapper.INSTANCE.fromSesssaoRequest(sessaoRequest);
        var sessaoResponse =  SessaoMapper.INSTANCE.toSessaoResponse(sessaoUseCase.abrirSessao(sessaoToSave));

        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoResponse);
    }

    @PostMapping("/encerrar/{id}")
    public ResponseEntity<SessaoResponse> encerrarSessao(@PathVariable("id") Integer idSessao){
        LOG.info("Encerrando sessao {}", idSessao);

        var sessaoResponse =  SessaoMapper.INSTANCE.toSessaoResponse(sessaoUseCase.encerrarSessao(idSessao));

        return ResponseEntity.status(HttpStatus.OK).body(sessaoResponse);
    }




}
