package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
