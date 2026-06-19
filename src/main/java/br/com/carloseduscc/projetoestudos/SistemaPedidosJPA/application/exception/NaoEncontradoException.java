package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String message) {
        super(message);
    }
}
