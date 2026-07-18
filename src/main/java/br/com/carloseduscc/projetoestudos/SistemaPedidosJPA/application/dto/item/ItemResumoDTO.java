package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "Resposta resumida de item")
public record ItemResumoDTO(
        UUID id,
        @Schema(example = "Mouse gamer")
        String nomeProduto,
        @Schema(example = "76.99")
        BigDecimal valor
) {
}
