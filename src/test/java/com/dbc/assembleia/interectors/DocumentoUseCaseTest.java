package com.dbc.assembleia.interectors;

import com.dbc.assembleia.datasource.invertexto.CpfValid;
import com.dbc.assembleia.datasource.invertexto.InvertextoClient;
import com.dbc.assembleia.exception.DocumentoInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class DocumentoUseCaseTest {

    @InjectMocks
    private DocumentoUseCase documentoUseCase;

    @Mock
    private InvertextoClient invertextoClient;

    @Test
    @DisplayName("Nao deve retornar Exception ao validar documento")
    void shouldNotThrowsExceptionWhenValidarDocumento() {

        var documento = "12345678900";
        var cpfValid = new CpfValid();
        cpfValid.setValid(true);

        Mockito.when(invertextoClient.validarCpf(Mockito.anyString(), documento)).thenReturn(cpfValid);

        Assertions.assertDoesNotThrow(() -> documentoUseCase.validarDocumento(documento));

        Mockito.verify(invertextoClient, Mockito.times(1)).validarCpf(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Deve retornar Exception ao validar documento")
    void shouldThrowsExceptionWhenValidarDocumento() {

        var cpfValid = new CpfValid();
        cpfValid.setValid(false);

        when(invertextoClient.validarCpf(anyString(),anyString())).thenReturn(cpfValid);

        Assertions.assertThrows(DocumentoInvalidoException.class,
                () ->  documentoUseCase.validarDocumento(anyString()));
    }


}