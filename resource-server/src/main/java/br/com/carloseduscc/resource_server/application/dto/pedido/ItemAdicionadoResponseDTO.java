package br.com.carloseduscc.resource_server.application.dto.pedido;

import java.util.UUID;

public record ItemAdicionadoResponseDTO(
        UUID idPedido,
        UUID idItem
) {
}
