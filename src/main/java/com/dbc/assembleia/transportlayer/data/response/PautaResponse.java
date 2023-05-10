package com.dbc.assembleia.transportlayer.data.response;

import java.io.Serializable;

public class PautaResponse implements Serializable {
    private Integer id;
    private String descricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "PautaResponse{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
