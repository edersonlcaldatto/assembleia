package com.dbc.assembleia.transportlayer.data.response;

public class VotoResponse {

    private VotoStatusEnum status;

    private String retorno;

    public VotoStatusEnum getStatus() {
        return status;
    }

    public void setStatus(VotoStatusEnum status) {
        this.status = status;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public static VotoResponse votoProcessado() {
        var votoResponse = new VotoResponse();
        votoResponse.setStatus(VotoStatusEnum.PROCESSAO);
        votoResponse.setRetorno("Voto processado com sucesso");
        return votoResponse;
    }

    public static VotoResponse votoRecusado() {
        var votoResponse = new VotoResponse();
        votoResponse.setStatus(VotoStatusEnum.RECUSADO);
        votoResponse.setRetorno("Voto recusado, votação encerrada");
        return votoResponse;
    }
}
