package com.dbc.assembleia.interectors;

import com.dbc.assembleia.interectors.queue.voto.VotoProducer;
import com.dbc.assembleia.transportlayer.data.request.VotoRequest;
import com.dbc.assembleia.transportlayer.data.response.VotoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VotacaoByQueueUseCase {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DocumentoUseCase documentoUseCase;
    private final VotoProducer votoProducer;

    public VotacaoByQueueUseCase(DocumentoUseCase documentoUseCase,
                                 VotoProducer votoProducer) {
        this.documentoUseCase = documentoUseCase;
        this.votoProducer = votoProducer;
    }


    /*
        Abordagem utilizando fila para processar o voto
        Executa validacao de documento, caso positivo envia para fila.

        Existem muito pontos de melhoria aqui, mas a ideia foi pensando em volumetria
        e versionamento de API, para exemplificar

    * */

    public VotoResponse votoPorFila(VotoRequest votoToQueue) {

        documentoUseCase.validarDocumento(votoToQueue.getDocumento());

        log.info("Enviando voto para fila de processamento");
        votoProducer.sendVoto(votoToQueue);

        return VotoResponse.votoEmProcessamento();

    }

}
