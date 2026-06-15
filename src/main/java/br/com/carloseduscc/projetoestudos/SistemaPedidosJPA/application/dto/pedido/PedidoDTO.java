package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoDTO(
        UUID id,
        LocalDateTime dataPedido,
        Integer totalItens,
        BigDecimal valorTotal,
        StatusPedido status
) {
}
