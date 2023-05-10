package com.dbc.assembleia.interectors.queue.resultado;

import com.dbc.assembleia.transportlayer.data.response.VotacaoResultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ResultadoConsumer {
    private final Logger log = LoggerFactory.getLogger(ResultadoConsumer.class);

    @KafkaListener(topics = "${service.resultado.topic}", groupId = "service-comunicacao")
    public void listen(VotacaoResultado resultado) {

        log.info("Resultado received on module service-comunicacao {}", resultado );
    }


}
