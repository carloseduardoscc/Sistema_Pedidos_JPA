package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.projections;

import java.math.BigDecimal;
import java.util.UUID;

public interface ItemPedidoDetalhadoProjection {
    UUID getItemId();
    UUID getPedidoId();
    String getNomeProduto();
    Integer getQuantidade();
    BigDecimal getPrecoUnitario();
}