package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
