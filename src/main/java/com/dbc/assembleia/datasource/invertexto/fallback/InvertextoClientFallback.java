package com.dbc.assembleia.datasource.invertexto.fallback;

import com.dbc.assembleia.datasource.invertexto.CpfValid;
import com.dbc.assembleia.datasource.invertexto.InvertextoClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvertextoClientFallback implements InvertextoClient {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Throwable throwable;
    public InvertextoClientFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public CpfValid validarCpf(String token, String documento) {
        log.error("Erro na chamada remota para API invertexto para validar documento ");
        log.error("EROR {} ", throwable.getCause());
        if (throwable instanceof FeignException f) {
            log.error("EROR {} ", f.request());
        }
        return new CpfValid(false);
    }

}
