package com.dbc.assembleia.datasource.invertexto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface InvertextoClient {
    @GetMapping("/v1/validator?token={token}&value={documento}&type=cpf")
    CpfValid validarCpf(@PathVariable("token") String token,
                        @PathVariable("documento") String documento);

}
