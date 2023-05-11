package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Sessao;
import com.dbc.assembleia.entity.enumerator.StatusEnum;
import com.dbc.assembleia.exception.SessaoNaoLocalizadaException;
import com.dbc.assembleia.interectors.queue.sessao.SessaoEncerradaProducer;
import com.dbc.assembleia.repositories.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoUseCase {

    private final SessaoRepository sessaoRepository;

    private final PautaUseCase pautaUseCase;
    private final SessaoEncerradaProducer sessaoEncerradaProducer;

    public SessaoUseCase(SessaoRepository sessaoRepository,
                         PautaUseCase pautaUseCase,
                         SessaoEncerradaProducer sessaoEncerradaProducer) {
        this.sessaoRepository = sessaoRepository;
        this.pautaUseCase = pautaUseCase;

        this.sessaoEncerradaProducer = sessaoEncerradaProducer;
    }

    public Sessao abrirSessao(Sessao sessaoToSave) {

        var horaGravacao = LocalDateTime.now();
        sessaoToSave.setPauta(pautaUseCase.find(sessaoToSave.getPauta().getId()));
        sessaoToSave.setDataHoraInicio(horaGravacao);
        sessaoToSave.setStatus(StatusEnum.ABERTA);

        return sessaoRepository.save(sessaoToSave);
    }

    public Sessao encerrarSessao(Integer idSessao) {

        var sessao = locate(idSessao);
        sessao.setStatus(StatusEnum.ENCERRADA);
        sessao.setDataHoraFim(LocalDateTime.now());
        var sessaoAlterada = sessaoRepository.save(sessao);

        sessaoEncerradaProducer.sessaoEncerrada(sessaoAlterada);

        return sessaoAlterada;
    }

    public Sessao locate(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new SessaoNaoLocalizadaException(String.format("Sessao de código %d não localizada", id)));
    }
}
