package br.com.carloseduscc.auth_server.application.exception;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
