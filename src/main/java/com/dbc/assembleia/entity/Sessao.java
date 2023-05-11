package com.dbc.assembleia.entity;

import com.dbc.assembleia.entity.enumerator.StatusEnum;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "SESSAO")
public class Sessao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "PAUTA_ID")
    private Pauta pauta;
    @Column(name = "STATUS")
    private StatusEnum status;
    @Column(name = "DTHORA_INICIO")
    private LocalDateTime dataHoraInicio;
    @Column(name = "DTHORA_FIM")
    private LocalDateTime dataHoraFim;
    public Sessao(@Valid Pauta pauta,@Valid StatusEnum status) {
        this.pauta = Objects.requireNonNull(pauta);
        this.status = Objects.requireNonNull(status);
        this.dataHoraInicio = LocalDateTime.now();
    }

    public Sessao(Integer id) {
        this.id = id;
    }

    public Sessao() {

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
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
