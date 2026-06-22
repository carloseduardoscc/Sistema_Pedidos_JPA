package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemResumoDTO;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetalhadoDTO(
        LocalDateTime dataHoraPedido,
        StatusPedido status,
        Integer totalItens,
        BigDecimal valorTotal,
        List<ItemResumoDTO> itens
) {
}
