package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository;

import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.infra.repository.projections.ItemPedidoDetalhadoProjection;
import br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    @Query("""
    SELECT
        i.id as itemId,
        ped.id as pedidoId,
        prod.nome as nomeProduto,
        i.quantidade as quantidade,
        prod.precoUnitario as precoUnitario
    FROM ItemPedido i
    JOIN i.pedido ped
    JOIN i.produto prod
    WHERE i.id = :idItem
""")
    Optional<ItemPedidoDetalhadoProjection> buscarItemPedidoDetalhadoProjection(@Param("idItem") UUID id);

    @Transactional
    @Modifying
    @Query("""
    DELETE FROM ItemPedido i
    WHERE i.id = :id
    AND i.pedido.status = br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.StatusPedido.PENDENTE
""")
    int deleteByIdIfPedidoPendente(@Param("id") UUID id);

    boolean existsByProduto_Id(UUID produtoId);
}
