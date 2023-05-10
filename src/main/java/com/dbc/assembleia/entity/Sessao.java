package com.dbc.assembleia.entity;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SESSAO")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "PAUTA_ID")
    private Pauta pauta;
    @Column(name = "DURACAO")
    private Integer duracao;
    @Column(name = "DTHORA_INICIO")
    private LocalDateTime dataHoraInicio;
    @Column(name = "DTHORA_FIM")
    private LocalDateTime dataHoraFim;

    @Deprecated
    public Sessao() {
    }

    public Sessao(@Valid Pauta pauta,@Valid Integer duracao) {
        this.pauta = Objects.requireNonNull(pauta);
        this.duracao = Objects.requireNonNull(duracao);
        this.dataHoraInicio = LocalDateTime.now();
        this.dataHoraFim = LocalDateTime.now().plusMinutes(duracao);
    }

    public Sessao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
