package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.pedido;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.application.dto.item.ItemResumoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetalhadoDTO(
        LocalDateTime dataHoraPedido,
        Integer totalItens,
        BigDecimal valorTotal,
        List<ItemResumoDTO> itens
) {
}
