package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;

public record PedidoAbertoEvent(Pedido pedido) {
}
