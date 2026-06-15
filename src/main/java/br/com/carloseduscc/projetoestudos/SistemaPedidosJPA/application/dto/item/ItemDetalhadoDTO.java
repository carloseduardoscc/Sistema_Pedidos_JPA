package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDetalhadoDTO(
        UUID itemId,
        UUID pedidoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario
) {
}
