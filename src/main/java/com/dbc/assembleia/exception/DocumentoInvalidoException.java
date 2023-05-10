package com.dbc.assembleia.exception;

public class DocumentoInvalidoException extends RuntimeException {

    public DocumentoInvalidoException(String mensagem){
        super(mensagem);
    }

}
