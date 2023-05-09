package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.exception.SessaoNaoLocalizadaException;
import com.dbc.assembleia.repositories.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoUseCase {

    private final SessaoRepository sessaoRepository;

    private final PautaUseCase pautaUseCase;

    public SessaoUseCase(SessaoRepository sessaoRepository,
                         PautaUseCase pautaUseCase) {
        this.sessaoRepository = sessaoRepository;
        this.pautaUseCase = pautaUseCase;
    }

    public Sessao cadastrar(Sessao sessaoToSave) {

        var horaGravacao = LocalDateTime.now();
        sessaoToSave.setPauta(pautaUseCase.find(sessaoToSave.getPauta().getId()));
        sessaoToSave.setDataHoraInicio(horaGravacao);
        sessaoToSave.setDataHoraFim(horaGravacao.plusMinutes(sessaoToSave.getDuracao()));

        return sessaoRepository.save(sessaoToSave);
    }

    public Sessao locate(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new SessaoNaoLocalizadaException(String.format("Sessao de código %d não localizada", id)));
    }
}
