package com.dbc.assembleia.transportlayer.impl;

import com.dbc.assembleia.interectors.VotacaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import com.dbc.assembleia.transportlayer.data.response.VotacaoResultado;
import com.dbc.assembleia.transportlayer.data.response.VotoResponse;
import com.dbc.assembleia.transportlayer.mapper.VotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/votacao")
public class VotacaoControllerImpl {

    private final Logger LOG = LoggerFactory.getLogger(VotacaoControllerImpl.class);
    private final VotacaoUseCase votacaoUseCase;
    public VotacaoControllerImpl(VotacaoUseCase votacaoUseCase) {
        this.votacaoUseCase = votacaoUseCase;
    }

    @PostMapping
    public ResponseEntity<VotoResponse> registrarVoto(@RequestBody @Valid VotoRequest votoRequest) {
        LOG.info("Processando voto {}", votoRequest);
        var votoToPost = VotoMapper.INSTANCE.fromVotoRequest(votoRequest);
        var retorno = votacaoUseCase.computarVoto(votoToPost);

        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

    @GetMapping("/resultado/{idsessao}")
    public ResponseEntity<VotacaoResultado> getResultado(@PathVariable("idsessao") Integer idSessao) {

        var resultado = votacaoUseCase.processarResultado(idSessao);
        return ResponseEntity.ok(resultado);

    }

}
