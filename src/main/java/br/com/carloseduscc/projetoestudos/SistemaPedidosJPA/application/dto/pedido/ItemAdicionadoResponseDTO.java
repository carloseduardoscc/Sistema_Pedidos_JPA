package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import java.util.UUID;

public record ItemAdicionadoResponseDTO(
        UUID idPedido,
        UUID idItem
) {
}
