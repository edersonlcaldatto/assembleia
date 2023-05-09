package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.entity.enumerator.ResultadoVotacaoEnum;
import com.dbc.assembleia.entity.enumerator.VotoEnum;
import com.dbc.assembleia.exception.VotoJaRealizadoException;
import com.dbc.assembleia.repositories.VotoRepository;
import com.dbc.assembleia.transportlayer.data.response.VotacaoResultado;
import com.dbc.assembleia.transportlayer.data.response.VotoResponse;
import com.dbc.assembleia.transportlayer.mapper.SessaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotocaoUseCase {

    private final VotoRepository votoRepository;
    private final SessaoUseCase sessaoUseCase;

    public VotocaoUseCase(VotoRepository votoRepository,
                          SessaoUseCase sessaoUseCase) {
        this.votoRepository = votoRepository;
        this.sessaoUseCase = sessaoUseCase;
    }

    public VotoResponse computarVoto(Voto votoToPost) {

        var sessao = sessaoUseCase.locate(votoToPost.getSessao().getId());

        var votoLocalizado = votoRepository.findByDocumentoAndSessao(votoToPost.getDocumento(), votoToPost.getSessao());
        if (votoLocalizado.isPresent()) {
            throw new VotoJaRealizadoException(String.format("Voto já realizado para a sessao %d com o documento %s",
                    votoToPost.getSessao().getId(), votoToPost.getDocumento()));
        }

        if (LocalDateTime.now().isAfter(sessao.getDataHoraFim())) {
            return VotoResponse.votoRecusado();
        }
        votoToPost.setHoraVoto(LocalDateTime.now());
        votoRepository.save(votoToPost);
        return VotoResponse.votoProcessado();
    }

    public VotacaoResultado processarResultado(Integer idSessao) {

        var sessao = sessaoUseCase.locate(idSessao);
        var votos = votoRepository.findAllByIdSessao(idSessao);

        var resultado = new VotacaoResultado();
        resultado.setSessao(SessaoMapper.INSTANCE.toSessaoResponse(sessao));
        resultado.setTotalVotos((long) votos.size());

        if (LocalDateTime.now().isBefore(sessao.getDataHoraFim())) {
            resultado.setResultado(ResultadoVotacaoEnum.EM_ANDAMENTO);
        } else {
            resultado.setTotalAprovado(votos
                    .stream()
                    .filter(voto -> voto.getVoto().equals(VotoEnum.SIM))
                    .count());
            resultado.setTotalReprovado(votos
                    .stream()
                    .filter(voto -> voto.getVoto().equals(VotoEnum.NAO))
                    .count());

            if (resultado.getTotalAprovado() > resultado.getTotalReprovado()) {
                resultado.setResultado(ResultadoVotacaoEnum.APROVADA);
            } else if (resultado.getTotalAprovado() < resultado.getTotalReprovado()) {
                resultado.setResultado(ResultadoVotacaoEnum.REPROVADA);
            } else {
                resultado.setResultado(ResultadoVotacaoEnum.EMPATE);
            }
        }

        return resultado;
    }
}
