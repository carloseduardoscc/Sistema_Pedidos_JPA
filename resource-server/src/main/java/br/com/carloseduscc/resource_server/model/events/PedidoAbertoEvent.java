package br.com.carloseduscc.resource_server.model.events;

import br.com.carloseduscc.resource_server.model.Pedido;

public record PedidoAbertoEvent(Pedido pedido) {
}
