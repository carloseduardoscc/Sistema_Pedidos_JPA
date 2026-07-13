package br.com.carloseduscc.resource_server.model.events;

import br.com.carloseduscc.resource_server.model.Pedido;
import br.com.carloseduscc.resource_server.model.StatusPedido;

public record PedidoTeveStatusModificadoEvent(
        StatusPedido antigoStatus,
        Pedido pedido
) {
}
