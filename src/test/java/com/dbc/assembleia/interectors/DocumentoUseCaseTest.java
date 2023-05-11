package com.dbc.assembleia.interectors;

import com.dbc.assembleia.datasource.invertexto.CpfValid;
import com.dbc.assembleia.datasource.invertexto.InvertextoClient;
import com.dbc.assembleia.exception.DocumentoInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DocumentoUseCaseTest {

    @InjectMocks
    private DocumentoUseCase documentoUseCase;
    @Mock
    private InvertextoClient client;

    @Value("${invertexto.token}")
    private String token;

    private final String cpf = "00000000000";

    @Test
    public void shouldCallClient() {

        var clientResponse = new CpfValid(true, "000.000.000-00");
        when(client.validarCpf(token, cpf)).thenReturn(clientResponse);

        documentoUseCase.validarDocumento(cpf);

        verify(client, times(1)).validarCpf(token, cpf);
    }

    @Test
    public void shouldCallClientAndThrowDocumentoInvalido() {

        var clientResponse = new CpfValid(false, "000.000.000-00");
        when(client.validarCpf(token, cpf)).thenReturn(clientResponse);

        var exception = assertThrows(DocumentoInvalidoException.class, () -> documentoUseCase.validarDocumento(cpf));

        verify(client, times(1)).validarCpf(token, cpf);
        assertNotNull(exception);
        assertEquals("Documento informado é inválido ", exception.getMessage());
    }

}
