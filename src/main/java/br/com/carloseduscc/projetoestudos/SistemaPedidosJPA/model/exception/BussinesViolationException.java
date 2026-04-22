package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception;

public class BussinesViolationException extends RuntimeException {
    public BussinesViolationException(String s) {
        super(s);
    }
}
