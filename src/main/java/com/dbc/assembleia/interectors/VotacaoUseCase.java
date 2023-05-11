package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Voto;
import com.dbc.assembleia.entity.enumerator.ResultadoVotacaoEnum;
import com.dbc.assembleia.entity.enumerator.StatusEnum;
import com.dbc.assembleia.entity.enumerator.VotoEnum;
import com.dbc.assembleia.exception.VotoJaRealizadoException;
import com.dbc.assembleia.interectors.queue.resultado.ResultadoProducer;
import com.dbc.assembleia.repositories.VotoRepository;
import com.dbc.assembleia.transportlayer.data.response.VotacaoResultado;
import com.dbc.assembleia.transportlayer.data.response.VotoResponse;
import com.dbc.assembleia.transportlayer.mapper.SessaoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotacaoUseCase {

    private final VotoRepository votoRepository;
    private final SessaoUseCase sessaoUseCase;
    private final DocumentoUseCase documentoUseCase;
    private final ResultadoProducer resultadoProducer;

    public VotacaoUseCase(VotoRepository votoRepository,
                          SessaoUseCase sessaoUseCase,
                          DocumentoUseCase documentoUseCase,
                          ResultadoProducer resultadoProducer) {
        this.votoRepository = votoRepository;
        this.sessaoUseCase = sessaoUseCase;
        this.documentoUseCase = documentoUseCase;
        this.resultadoProducer = resultadoProducer;
    }

    public VotoResponse computarVoto(Voto votoToPost) {

        documentoUseCase.validarDocumento(votoToPost.getDocumento());

        var sessao = sessaoUseCase.locate(votoToPost.getSessao().getId());

        var votoLocalizado = votoRepository.findByDocumentoAndSessao(votoToPost.getDocumento(), votoToPost.getSessao());
        if (votoLocalizado.isPresent()) {
            throw new VotoJaRealizadoException(String.format("Voto já realizado para a sessao %d com o documento %s",
                    votoToPost.getSessao().getId(), votoToPost.getDocumento()));
        }

        if (sessao.getStatus().equals(StatusEnum.ENCERRADA)) {
            return VotoResponse.votoRecusado();
        }
        votoToPost.setHoraVoto(LocalDateTime.now());
        votoRepository.save(votoToPost);
        return VotoResponse.votoProcessado();
    }

    public VotacaoResultado processarResultado(Integer idSessao) {

        var sessao = sessaoUseCase.locate(idSessao);
        var votos = votoRepository.findAllBySessao(sessao);

        var resultado = new VotacaoResultado();
        resultado.setSessao(SessaoMapper.INSTANCE.toSessaoResponse(sessao));
        resultado.setTotalVotos((long) votos.size());

        if (sessao.getStatus().equals(StatusEnum.ABERTA) ) {
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
            resultadoProducer.sendResultado(resultado);
        }

        return resultado;
    }

}
