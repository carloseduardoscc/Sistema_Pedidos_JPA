package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "Resposta detalhada de item")
public record ItemDetalhadoDTO(
        UUID itemId,
        @Schema(description = "Id do pedido relacionado ao item")
        UUID pedidoId,
        @Schema(example = "Mouse gamer")
        String nomeProduto,
        @Schema(example = "5")
        Integer quantidade,
        @Schema(example = "76.99")
        BigDecimal precoUnitario
) {
}
