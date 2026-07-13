package br.com.carloseduscc.auth_server.application.dto.erro;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<CampoErro> erros) {

    public static ErroResposta respostaPadrao (String mensagem){
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResposta naoEncontrado (String mensagem){
        return new ErroResposta(HttpStatus.NOT_FOUND.value(), mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem){
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}
