package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
