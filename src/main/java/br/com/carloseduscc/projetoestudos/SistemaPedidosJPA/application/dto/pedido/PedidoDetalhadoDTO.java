package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(name = "Resposta detalhada de pedido")
public record PedidoDetalhadoDTO(
        UUID id,
        @Schema(description = "Data e hora de abertura do pedido", example = "2026-07-18T18:55:29.334996")
        LocalDateTime dataHoraPedido,
        StatusPedido status,
        @Schema(example = "1")
        Integer totalItens,
        @Schema(example = "76.99")
        BigDecimal valorTotal,
        List<ItemResumoDTO> itens
) {
}
