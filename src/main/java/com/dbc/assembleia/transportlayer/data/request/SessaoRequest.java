package com.dbc.assembleia.transportlayer.data.request;

import javax.validation.constraints.NotNull;

public class SessaoRequest {

    @NotNull
    private Integer codigoPauta;

    public Integer getCodigoPauta() {
        return codigoPauta;
    }

    public void setCodigoPauta(Integer codigoPauta) {
        this.codigoPauta = codigoPauta;
    }

}
