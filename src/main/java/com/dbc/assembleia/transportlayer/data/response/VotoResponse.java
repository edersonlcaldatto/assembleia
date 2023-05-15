package com.dbc.assembleia.transportlayer.data.response;

public record VotoResponse(VotoStatusEnum status,
                           String retorno) {

    public VotoResponse {
    }

    public static VotoResponse votoProcessado() {
        return new VotoResponse(VotoStatusEnum.PROCESSAO, "Voto processado com sucesso");
    }

    public static VotoResponse votoRecusado() {
        return new VotoResponse(VotoStatusEnum.RECUSADO, "Voto recusado, votação encerrada");
    }
    public static VotoResponse votoEmProcessamento() {
        return new VotoResponse(VotoStatusEnum.EM_FILA_PROCESSAMENTO, "Voto enviado para fila de processamento");
    }


}
