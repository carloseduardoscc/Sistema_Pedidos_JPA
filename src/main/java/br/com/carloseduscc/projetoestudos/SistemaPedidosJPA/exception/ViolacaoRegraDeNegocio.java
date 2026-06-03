package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.exception;

public class ViolacaoRegraDeNegocio extends RuntimeException {
    public ViolacaoRegraDeNegocio(String s) {
        super(s);
    }
}
