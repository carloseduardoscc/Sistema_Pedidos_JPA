package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String message) {
        super(message);
    }
}
