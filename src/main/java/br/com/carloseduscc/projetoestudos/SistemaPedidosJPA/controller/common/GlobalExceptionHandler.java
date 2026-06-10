package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.controller.common;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.CampoErro;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.ErroResposta;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.NaoEncontradoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.OperacaoNaoPermitidaException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegistroDuplicadoException;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ArrayList<CampoErro> errosDeValidacao = e.getFieldErrors().stream()
                .map(fe -> new CampoErro(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toCollection(ArrayList::new));

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                errosDeValidacao
        );
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErroResposta handleRegraDeNegocioException(RegraDeNegocioException e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return new ErroResposta(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErroResposta handleNaoEncontradoException(NaoEncontradoException e){
        return new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                List.of()
        );
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErroResposta handleExcecaoNaoTratada(RuntimeException e){
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com a administração.",
                List.of(new CampoErro("Mensagem", e.getMessage()))
        );
    }
}
