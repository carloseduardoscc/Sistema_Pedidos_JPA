package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.exception.RegraDeNegocioException;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum StatusPedido {
    CANCELADO(List.of()),
    RECEBIDO(List.of()),
    ENVIADO(List.of(RECEBIDO)),
    PAGO(List.of(ENVIADO, CANCELADO)),
    PENDENTE(List.of(PAGO, CANCELADO));

    public final List<StatusPedido> proximosEstadosPossiveis;

    public void validarTransacao(StatusPedido novoStatus){
        if (!proximosEstadosPossiveis.contains(novoStatus)) lancarErro(novoStatus);
    }
    public void lancarErro(StatusPedido status){
        throw new RegraDeNegocioException("Não é possível tornar %s um pedido que está %s".formatted(status.toString().toLowerCase(), this.toString().toLowerCase()));
    }
}
