package br.com.carloseduscc.resource_server.application.dto.pedido;

import br.com.carloseduscc.resource_server.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.UUID;

public record AbrirPedidoResponseDTO(
        UUID id,
        LocalDateTime dataPedido,
        StatusPedido status
) {
}
