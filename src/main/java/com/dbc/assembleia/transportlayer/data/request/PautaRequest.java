package com.dbc.assembleia.transportlayer.data.request;

import javax.validation.constraints.NotEmpty;

public class PautaRequest {

    @NotEmpty
    private String descricao;
    @NotEmpty
    private String detalhe;

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getDescricao() {
        return descricao;
    }
    public String getDetalhe() {
        return detalhe;
    }
}
