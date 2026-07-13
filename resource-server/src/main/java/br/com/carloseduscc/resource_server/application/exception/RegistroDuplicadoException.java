package br.com.carloseduscc.resource_server.application.exception;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
