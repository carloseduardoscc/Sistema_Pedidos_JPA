package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
