package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Resposta de Item adicionado")
public record ItemAdicionadoResponseDTO(
        UUID idPedido,
        UUID idItem
) {
}
