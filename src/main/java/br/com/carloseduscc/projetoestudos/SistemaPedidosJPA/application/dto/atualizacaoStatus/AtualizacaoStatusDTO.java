package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.atualizacaoStatus;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Registro de quando e para qual status foi modificado")
public record AtualizacaoStatusDTO (
        @Schema(description = "Data e hora da atualização")
        LocalDateTime dataHora,
        @Schema(example = "PENDENTE", description = "Para qual status foi modificado")
        StatusPedido status
)
{}
