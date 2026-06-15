package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.Pedido;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.repository.projections.ItemPedidoDetalhadoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    @Query("""
    select
        i.id as itemId,
        p.id as pedidoId,
        i.nomeProduto as nomeProduto,
        i.quantidade as quantidade,
        i.precoUnitario as precoUnitario
    from ItemPedido i
    join i.pedido p
    where i.id = :idItem
""")
    Optional<ItemPedidoDetalhadoProjection> buscarItemPedidoDetalhadoProjection(@Param("idItem") UUID id);
}
