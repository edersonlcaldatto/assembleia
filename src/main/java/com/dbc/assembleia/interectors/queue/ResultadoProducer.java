package com.dbc.assembleia.interectors.queue;

import com.dbc.assembleia.transportlayer.data.response.VotacaoResultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class ResultadoProducer {


    @Value("${service.resultado.topic}")
    private String topicName;

    private final Logger log = LoggerFactory.getLogger(ResultadoProducer.class);

    private final KafkaTemplate<String, Serializable> objectKafkaTemplate;

    public ResultadoProducer(KafkaTemplate<String, Serializable> objectKafkaTemplate) {
        this.objectKafkaTemplate = objectKafkaTemplate;
    }

    public void sendResultado(VotacaoResultado resultado) {

        try {
            var record = objectKafkaTemplate.send(topicName, resultado).get().getRecordMetadata();
            log.info("Message send to topic {} Partition {} and offset {} ", topicName, record.partition(), record.offset());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erro ao enviar mensagem para tópico {} erro: {}", topicName, e.getMessage());
        }
    }

}
