package com.dbc.assembleia.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "PAUTA")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;
    @Column(name = "DETALHE", length = 500, nullable = true)
    private String detalhe;

    public Pauta() {
    }

    public Pauta(@NotNull String descricao, @NotNull String detalhe) {
        this.descricao = Objects.requireNonNull(descricao);
        this.detalhe = Objects.requireNonNull(detalhe);
    }

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

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }
}
