package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto;

import java.util.UUID;

public record PedidoAdicionadoResponseDTO(
        UUID idPedido,
        UUID idItem
) {
}
