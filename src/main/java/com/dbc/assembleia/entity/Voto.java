package com.dbc.assembleia.entity;

import com.dbc.assembleia.entity.enumerator.VotoEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOTO")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ID_SESSAO", nullable = false)
    private Sessao sessao;
    @Column(name = "DOCUMENTO", length = 14, nullable = false)
    private String documento;
    @Enumerated(EnumType.STRING)
    @Column(name = "VOTO", nullable = false)
    private VotoEnum voto;

    @Column(name = "HORA_VOTO", nullable = false)
    private LocalDateTime horaVoto;

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public VotoEnum getVoto() {
        return voto;
    }

    public void setVoto(VotoEnum voto) {
        this.voto = voto;
    }

    public LocalDateTime getHoraVoto() {
        return horaVoto;
    }

    public void setHoraVoto(LocalDateTime horaVoto) {
        this.horaVoto = horaVoto;
    }
}
