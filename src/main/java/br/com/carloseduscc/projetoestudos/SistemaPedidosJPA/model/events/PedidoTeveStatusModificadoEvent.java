package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.events;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;

public record PedidoTeveStatusModificadoEvent(
        StatusPedido antigoStatus,
        Pedido pedido
) {
}
