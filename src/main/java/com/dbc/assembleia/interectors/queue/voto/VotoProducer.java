package com.dbc.assembleia.interectors.queue.voto;

import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class VotoProducer {

    @Value("${service.voto.topic}")
    private String topicFilaVoto;

    private final Logger log = LoggerFactory.getLogger(VotoProducer.class);

    private final KafkaTemplate<String, Serializable> objectKafkaTemplate;

    public VotoProducer(KafkaTemplate<String, Serializable> objectKafkaTemplate) {
        this.objectKafkaTemplate = objectKafkaTemplate;
    }

    public void sendVoto(VotoRequest voto) {

        try {
            var record = objectKafkaTemplate.send(topicFilaVoto, voto).get().getRecordMetadata();
            log.info("Message send to topic {} Partition {} and offset {} ", topicFilaVoto, record.partition(), record.offset());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erro ao enviar mensagem para tópico {} erro: {}", topicFilaVoto, e.getMessage());
        }
    }

}
