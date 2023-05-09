package com.dbc.assembleia.transportlayer.data.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PautaRequest {

    @NotEmpty
    private String descricao;
    @NotEmpty
    private String detalhe;

    public String getDescricao() {
        return descricao;
    }

    public String getDetalhe() {
        return detalhe;
    }
}
