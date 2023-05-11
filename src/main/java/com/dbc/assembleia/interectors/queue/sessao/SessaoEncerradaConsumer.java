package com.dbc.assembleia.interectors.queue.sessao;

import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.enumerator.StatusEnum;
import com.dbc.assembleia.interectors.VotacaoUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SessaoEncerradaConsumer {
    private final Logger log = LoggerFactory.getLogger(SessaoEncerradaConsumer.class);

    private final VotacaoUseCase votacaoUseCase;

    public SessaoEncerradaConsumer(VotacaoUseCase votacaoUseCase) {
        this.votacaoUseCase = votacaoUseCase;
    }

    @KafkaListener(topics = "${service.sessao-encerrada.topic}", groupId = "service-voto")
    public void listen(Sessao sessao) {

        log.info("Resultado received on module service-voto {}", sessao );
        if (sessao.getStatus().equals(StatusEnum.ENCERRADA)) {
            votacaoUseCase.processarResultado(sessao.getId());
        }
    }
}
