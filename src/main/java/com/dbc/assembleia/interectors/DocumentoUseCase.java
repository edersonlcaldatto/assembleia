package com.dbc.assembleia.interectors;

import com.dbc.assembleia.datasource.invertexto.InvertextoClient;
import com.dbc.assembleia.exception.DocumentoInvalidoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentoUseCase {

    @Value("${invertexto.token}")
    private String token;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final InvertextoClient invertextoClient;

    public DocumentoUseCase(InvertextoClient invertextoClient) {
        this.invertextoClient = invertextoClient;
    }

    /*
        Método responsavél por integrar com api de validacao de CPF invertexto
        Caso erro no processamento a classe de fallback irá tratar e retonar cpf inválido.

     */
    public void validarDocumento(String documento) {
        log.info("Validando documento do eleitor ");
        var retorno = invertextoClient.validarCpf(token,documento);

        if (!retorno.isValid()) {
            throw new DocumentoInvalidoException("Documento informado é inválido ");
        }
    }
}
