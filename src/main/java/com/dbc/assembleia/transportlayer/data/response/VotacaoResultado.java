package com.dbc.assembleia.transportlayer.data.response;

import com.dbc.assembleia.entity.enumerator.ResultadoVotacaoEnum;

public class VotacaoResultado {

    private SessaoResponse sessao;
    private Long totalAprovado;
    private Long totalReprovado;
    private Long totalVotos;
    private ResultadoVotacaoEnum resultado;

    public SessaoResponse getSessao() {
        return sessao;
    }

    public void setSessao(SessaoResponse sessao) {
        this.sessao = sessao;
    }

    public Long getTotalAprovado() {
        return totalAprovado;
    }

    public void setTotalAprovado(Long totalAprovado) {
        this.totalAprovado = totalAprovado;
    }

    public Long getTotalReprovado() {
        return totalReprovado;
    }

    public void setTotalReprovado(Long totalReprovado) {
        this.totalReprovado = totalReprovado;
    }

    public Long getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Long totalVotos) {
        this.totalVotos = totalVotos;
    }

    public ResultadoVotacaoEnum getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoVotacaoEnum resultado) {
        this.resultado = resultado;
    }
}
