package com.dbc.assembleia.transportlayer.data.request;

import javax.validation.constraints.NotNull;

public class SessaoRequest {

    @NotNull
    private Integer codigoPauta;
    private Integer duracao = 1;

    public Integer getCodigoPauta() {
        return codigoPauta;
    }

    public void setCodigoPauta(Integer codigoPauta) {
        this.codigoPauta = codigoPauta;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}
