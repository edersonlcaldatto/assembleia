package com.dbc.assembleia.config;

import com.dbc.assembleia.exception.PautaNaoLocalizadaException;
import com.dbc.assembleia.exception.SessaoNaoLocalizadaException;
import com.dbc.assembleia.exception.VotoJaRealizadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExecptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler({VotoJaRealizadoException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorAdivice noResult(VotoJaRealizadoException e){
        return new ErrorAdivice("X_400", e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorAdivice noResult(HttpMessageNotReadableException e){
        return new ErrorAdivice("X_500", e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler({SessaoNaoLocalizadaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorAdivice noResult(SessaoNaoLocalizadaException e){
        return new ErrorAdivice("X_400", e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({PautaNaoLocalizadaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorAdivice noResult(PautaNaoLocalizadaException e){
        return new ErrorAdivice("X_400", e.getMessage());
    }

}
