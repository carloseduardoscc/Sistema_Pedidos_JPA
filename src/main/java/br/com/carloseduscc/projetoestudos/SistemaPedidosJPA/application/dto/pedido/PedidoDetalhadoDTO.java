package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoDetalhadoDTO(
        UUID id,
        LocalDateTime dataHoraPedido,
        StatusPedido status,
        Integer totalItens,
        BigDecimal valorTotal,
        List<ItemResumoDTO> itens
) {
}
