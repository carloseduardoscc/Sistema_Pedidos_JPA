package br.com.carloseduscc.resource_server.application.dto.pedido;

import br.com.carloseduscc.resource_server.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.resource_server.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoDetalhadoDTO(
        UUID id,
        LocalDateTime dataHoraPedido,
        StatusPedido status,
        Integer totalItens,
        BigDecimal valorTotal,
        List<ItemResumoDTO> itens
) {
}
