package com.dbc.assembleia.transportlayer.data.response;

import com.dbc.assembleia.entity.enumerator.StatusEnum;

import java.io.Serializable;

public class SessaoResponse implements Serializable {

    private Integer id;
    private PautaResponse pauta;
    private StatusEnum status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PautaResponse getPauta() {
        return pauta;
    }

    public void setPauta(PautaResponse pauta) {
        this.pauta = pauta;
    }

    public StatusEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SessaoResponse{" +
                "id=" + id +
                ", pauta=" + pauta +
                ", status=" + status +
                '}';
    }
}
