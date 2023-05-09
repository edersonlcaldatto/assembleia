package com.dbc.assembleia.interectors;

import com.dbc.assembleia.entity.Pauta;
import com.dbc.assembleia.exception.PautaNaoLocalizadaException;
import com.dbc.assembleia.repositories.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class PautaUseCase {

    private final PautaRepository pautaRepository;

    public PautaUseCase(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta cadastrar(Pauta pautaToSave) {
        return pautaRepository.save(pautaToSave);
    }

    public Pauta find(Integer id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new PautaNaoLocalizadaException(String.format("Pauta código %d não localizada", id)));
    }
}
