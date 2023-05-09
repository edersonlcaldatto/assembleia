package com.dbc.assembleia.transportlayer.data.request;

public class VotoRequest {

    private Integer codigoSessao;
    private String documento;
    private VotoRequestEnum voto;

    public Integer getCodigoSessao() {
        return codigoSessao;
    }

    public void setCodigoSessao(Integer codigoSessao) {
        this.codigoSessao = codigoSessao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public VotoRequestEnum getVoto() {
        return voto;
    }

    public void setVoto(VotoRequestEnum voto) {
        this.voto = voto;
    }
}
