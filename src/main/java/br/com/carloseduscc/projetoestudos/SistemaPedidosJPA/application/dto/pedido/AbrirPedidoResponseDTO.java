package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "Resposta de abertura de pedido")
public record AbrirPedidoResponseDTO(
        UUID id
) {
}
