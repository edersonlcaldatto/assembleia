package com.dbc.assembleia.interectors.queue.sessao;

import com.dbc.assembleia.entity.Sessao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SessaoEncerradaProducer {

    @Value("${service.sessao-encerrada.topic}")
    private String topicSessaoEncerrada;

    private final Logger log = LoggerFactory.getLogger(SessaoEncerradaProducer.class);

    private final KafkaTemplate<String, Serializable> objectKafkaTemplate;

    public SessaoEncerradaProducer(KafkaTemplate<String, Serializable> objectKafkaTemplate) {
        this.objectKafkaTemplate = objectKafkaTemplate;
    }

    public void sessaoEncerrada(Sessao sessao) {

        try {
            var record = objectKafkaTemplate.send(topicSessaoEncerrada, sessao).get().getRecordMetadata();
            log.info("Message send to topic {} Partition {} and offset {} ", topicSessaoEncerrada, record.partition(), record.offset());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Erro ao enviar mensagem para tópico {} erro: {}", topicSessaoEncerrada, e.getMessage());
        }
    }

}
