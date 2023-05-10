package com.dbc.assembleia.transportlayer.data.response;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SessaoResponse implements Serializable {

    private Integer id;
    private PautaResponse pauta;
    private Integer duracao;
    private LocalDateTime dataHoraFim;

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

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    @Override
    public String toString() {
        return "SessaoResponse{" +
                "id=" + id +
                ", pauta=" + pauta +
                ", duracao=" + duracao +
                ", dataHoraFim=" + dataHoraFim +
                '}';
    }
}
