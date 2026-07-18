package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "Pedido")
public record PedidoDTO(
        UUID id,
        @Schema
        LocalDateTime dataHoraPedido,
        StatusPedido status
) {
}