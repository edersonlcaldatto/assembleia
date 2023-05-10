package com.dbc.assembleia.interectors.queue.voto;

import com.dbc.assembleia.interectors.VotacaoUseCase;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import com.dbc.assembleia.transportlayer.mapper.VotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VotoConsumer {
    private final Logger log = LoggerFactory.getLogger(VotoConsumer.class);

    private final VotacaoUseCase votacaoUseCase;

    public VotoConsumer(VotacaoUseCase votacaoUseCase) {
        this.votacaoUseCase = votacaoUseCase;
    }

    /*
    * Topico escuta voto recebido e computa o mesmo.
    * Utilizei o mesmo usaCase aqui para não extender a entrega do projeto
    * mas cabe um refactory aqui
    * ex: está validando o documento novamente
    * */
    @KafkaListener(topics = "${service.voto.topic}", groupId = "service-voto")
    public void listen(VotoRequest votoRequest) {

        log.info("Resultado received on module service-voto {}", votoRequest );
        var voto = VotoMapper.INSTANCE.fromVotoRequest(votoRequest);

        try {
            votacaoUseCase.computarVoto(voto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erro ao registrar o voto erro {}", e.getMessage());
        }
    }
}
